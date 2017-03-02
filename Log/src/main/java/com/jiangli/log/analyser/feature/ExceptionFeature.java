package com.jiangli.log.analyser.feature;

/**
 * @author Jiangli
 * @date 2017/3/1 15:47
 */
public class ExceptionFeature implements Feature{
    @Override
    public boolean featureStarts(String line) {
        if (line.contains("Exception")) {
            String trimedString = line.trim();
            if(trimedString.startsWith("[") ){
                return false;
            }
            if(trimedString.startsWith("at") ){
                return false;
            }
            if(trimedString.startsWith("Caused by") ){
                return false;
            }
//            if (trimedString.startsWith("java") || trimedString.startsWith("com")|| trimedString.startsWith("org")) {
//                return true;
//            }
            return true;
        }
        return false;
    }

    @Override
    public boolean featureEnds(String line) {
        String trimedString = line.trim();
        //... 48 more
        if (trimedString.startsWith("...")) {
            return true;
        }
        if (trimedString.contains("Caused by")) {
            return false;
        }
        if (trimedString.startsWith("at")) {
            return false;
        }

        return true;
    }
}
