package com.jiangli.common.core;

import com.jiangli.common.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.jiangli.common.core.ThreadCollector.ExecuteStatus.NOT_EXISTS;

/**
 * Created by Jiangli on 2017/4/20.
 */
public class ThreadCollector {
    private static Map<String, RunningStatistics> dataMap = new HashMap<>();
    private static Map<String, ExecuteStatus> statusMap = new HashMap<>();
    private static Map<String, CancelListener> cancelListenerMap = new HashMap<>();


    public static String generateQueryId(){
        String s = "query-" + System.currentTimeMillis() + "-" + RandomUtil.getRandomNum();
        setStatus(s,ExecuteStatus.PREPARED);
        return s;
    }

    public static<T> RunningStatistics start(String queryId){
        setStatus(queryId,ExecuteStatus.RUNNING);
        if (notNull(queryId)) {
            RunningStatistics<T> one = new RunningStatistics<>();
            dataMap.put(queryId,one);
            return one;
        }
        return new RunningStatistics();
    }
    public static void finish(String queryId){
        setStatus(queryId,ExecuteStatus.FINISHED);
    }
    public static QueryResult query(String queryId){
        ExecuteStatus executeStatus = null;
        if (notNull(queryId)) {
            executeStatus = statusMap.get(queryId);
        }

        QueryResult ret = new QueryResult();
        if (executeStatus == null) {
            ret.code=NOT_EXISTS.code;
        } else {
            ret.code=executeStatus.code;

//            if (ret.code==RUNNING.code) {
                ret.data = dataMap.get(queryId);
//            }
        }
        return ret;
    }

    public static void setStatus(String queryId,ExecuteStatus status){
        if (notNull(queryId)) {
            statusMap.put(queryId,status);
        }
    }

    public static void cancelExecute(String queryId){
        if (notNull(queryId)) {
            setStatus(queryId,ExecuteStatus.CANCELLED);

            if (cancelListenerMap.get(queryId)!=null) {
                cancelListenerMap.get(queryId).cancel();
                cancelListenerMap.remove(queryId);
            }
        }

        dataMap.remove(queryId);
    }

    public static void registerCancelListener(String queryId,CancelListener cancelListener){
        if (notNull(queryId)) {
            cancelListenerMap.put(queryId,cancelListener);
        }
    }

    private static boolean notNull(String queryId){
        return   (!StringUtils.isEmpty(queryId));
    }
    public interface CancelListener{
        void cancel();
    }

    public static class RunningStatistics<T>{
        private String percent="0%";
        private String restTime="未知";
        private long current=1;
        private long total=100;
        private T detail;

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getRestTime() {
            return restTime;
        }

        public void setRestTime(String restTime) {
            this.restTime = restTime;
        }

        public long getCurrent() {
            return current;
        }

        public void setCurrent(long current) {
            this.current = current;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public T getDetail() {
            return detail;
        }

        public void setDetail(T detail) {
            this.detail = detail;
        }

        @Override
        public String toString() {
            return "RunningStatistics{" +
                    "percent='" + percent + '\'' +
                    ", restTime='" + restTime + '\'' +
                    ", current=" + current +
                    ", total=" + total +
                    ", detail=" + detail +
                    '}';
        }
    }

    enum ExecuteStatus{
        NOT_EXISTS(-1),PREPARED(1),RUNNING(2),FINISHED(0),CANCELLED(3);

         int code;
        ExecuteStatus(int code) {
            this.code = code;
        }
    }
    public static class QueryResult{
        private int code;
        private RunningStatistics data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public RunningStatistics getData() {
            return data;
        }

        public void setData(RunningStatistics data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "QueryResult{" +
                    "code=" + code +
                    ", data=" + data +
                    '}';
        }
    }
}
