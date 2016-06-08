package com.jiangli.jni.app.richman9.smallgame;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.common.utils.PathUtil;
import com.jiangli.jni.common.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/8 0008 16:16
 */
public abstract class AbsractAnalyser implements  Analyser{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected int analysedSize = 0;
    protected final String projectPath;
    protected final String capturePath;
    protected final String analysePath;

    public AbsractAnalyser(String projectPath) {
        this.projectPath = projectPath;

        capturePath = PathUtil.buildPath(projectPath,"captured");
        analysePath = PathUtil.buildPath(projectPath,"anylyse");

    }

    @Override
    public int analyse() {
        List<String> capturePaths = FileUtil.getFilePathFromDirPath(capturePath);

        for (String capturePathOne : capturePaths) {
            anylyseEachPath(capturePathOne);
        }
        analysedSize = capturePaths.size();
        return analysedSize;
    }

    protected abstract void anylyseEachPath(String capturePathOne);

}
