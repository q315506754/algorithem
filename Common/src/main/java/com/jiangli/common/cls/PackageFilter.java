package com.jiangli.common.cls;

/**
 * @author Jiangli
 * @date 2017/7/5 9:48
 */
public interface PackageFilter {
    boolean filter(String baseDirPackage, String relativePackage, String currentPackage, String fileName);
}
