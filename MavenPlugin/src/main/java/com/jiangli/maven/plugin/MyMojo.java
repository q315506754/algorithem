package com.jiangli.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 *
 * @phase process-sources
 * @goal count
 */
public class MyMojo
        extends AbstractMojo {
    /**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;


    /**
     * @parameter expression="${project.basedir}"
     * @required
     * @readonly
     */
    private File basedir;
    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    private File sourcedir;
    /**
     * @parameter expression="${project.build.testSourceDirectory}"
     * @required
     * @readonly
     */
    private File testSourcedir;
    /**
     * @parameter expression="${project.resources}"
     * @required
     * @readonly
     */
    private List<File> resources;
    //private List<File> resources;
    /**
     * @parameter expression="${project.testResources}"
     * @required
     * @readonly
     */
    private List<File> testResources;
    //private List<File> testResources;
    /**
     * @parameter
     */
    private String[] includes;
    /**
     * @parameter
     */
    private String[] ratios;//TODO 定义为double[],从xml读取时提示java.lang.ClassCastException: [D cannot be cast to [Ljava.lang.Object;

    public void execute()
            throws MojoExecutionException {
        File f = outputDirectory;

        if (!f.exists()) {
            f.mkdirs();
        }

        File touch = new File(f, "touch.txt");

        FileWriter w = null;
        try {
            w = new FileWriter(touch);

            w.write("touch.txt");
        } catch (IOException e) {
            throw new MojoExecutionException("Error creating file " + touch, e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        getLog().info("overoveroveroveroverover..overoveroveroverover.");
        getLog().info("${project.build.directory}:"+String.valueOf(outputDirectory));
        getLog().info("${project.basedir}:"+String.valueOf(basedir));
        getLog().info("${project.build.sourceDirectory}:"+String.valueOf(sourcedir));
        getLog().info("${project.build.testSourceDirectory}:"+String.valueOf(testSourcedir));
        getLog().info("${project.resources}:"+String.valueOf(resources));
        getLog().info("${project.testResources}:"+String.valueOf(testResources));
        getLog().info("includes:"+ Arrays.toString(includes));
        getLog().info("ratios:"+ Arrays.toString(ratios));
    }
}
