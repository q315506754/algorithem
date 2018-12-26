package com.jiangli.common.utils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 递归工具
 *
 * @author Jiangli
 * @date 2018/12/26 16:06
 */
public class RecurUtil {
    private static ThreadLocal<PathInfo> obj = new ThreadLocal<>();
    public static class PathInfo{
        public int layer;
        public Object absPath;
        public Object parentPath;
        public Object nodeName;
        public List<Object> paths=new LinkedList<>();

        @Override
        public String toString() {
            return "PathInfo{" +
                    "layer=" + layer +
                    ", absPath=" + absPath +
                    ", parentPath=" + parentPath +
                    ", nodeName=" + nodeName +
                    ", paths=" + paths +
                    '}';
        }
    }

    public static  PathInfo getCurPathInfo(){
        return obj.get();
    }

    public static <P,CHILD> void recursive(int layer, P param, Function<P,Iterable<CHILD>> queryChilder, BiConsumer<Integer,CHILD> consumer, BiFunction<P,CHILD,P> joiner) {
        Iterable<CHILD> apply = queryChilder.apply(param);
        if (apply != null) {
            for (CHILD child : apply) {
                P childParam = joiner.apply(param, child);


                int t = layer + 1;
                PathInfo pathInfo = new PathInfo();
                pathInfo.layer=t;
                pathInfo.parentPath=param;
                pathInfo.absPath=childParam;
                pathInfo.nodeName=child;
                PathInfo old = obj.get();

                List<Object> oldPaths;
                if (old == null) {
                    oldPaths = new LinkedList<>();
                    oldPaths.add(param);
                } else {
                    oldPaths = old.paths;
                }
                pathInfo.paths.addAll(oldPaths);
                pathInfo.paths.add(child);

                obj.set(pathInfo);

                //消费
                consumer.accept(t,child);

                //递归
                recursive(t,childParam,queryChilder,consumer,joiner);

            //    递归完子节点后 路径需要删除当前节点
                obj.get().paths = pathInfo.paths.subList(0, pathInfo.paths.size() - 1);
            }
        }
    }

    public static  void recursiveFile(String baseDir,  BiConsumer<Integer,File> consumer) {
        recursive(0
                ,baseDir
                ,s -> {
                    File file = new File(s);
                    if (!file.isDirectory()) {
                        return null;
                    }
                    return Arrays.asList(file.listFiles());
                }
                ,consumer
                ,(s, child) -> child.getAbsolutePath()
        );
    }

    public static  String addSlash(String base, String slash){
        String start = base.endsWith(slash)?base:(base+slash);
        return start;
    }
    public static  void recursivePath(String base, String slash, Function<String,Iterable<String>> queryChilder, BiConsumer<Integer,String> consumer) {
        String start = addSlash(base,slash);

        recursive(0
                ,start
                ,queryChilder
                ,consumer
                ,(s, child) -> addSlash(s,slash)+child
        );
    }

    public static String multiSplit(int x, String s) {
        StringBuilder sb = new StringBuilder();
        while (x-- > 0 ) {
            sb.append(s);
        }
        return sb.toString();
    }
}
