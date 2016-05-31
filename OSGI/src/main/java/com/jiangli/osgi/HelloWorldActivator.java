package com.jiangli.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator
 {
     public void start(BundleContext bundleContext) throws Exception
     {
         System.out.println("Hello World Bundle started!");
     }
 
     public void stop(BundleContext bundleContext) throws Exception
     {
         System.out.println("Hello World Bundle stop!");
     }
 }