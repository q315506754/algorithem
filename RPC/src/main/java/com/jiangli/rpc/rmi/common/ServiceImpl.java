package com.jiangli.rpc.rmi.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceImpl extends UnicastRemoteObject implements IService {
    private static Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

  private String name; 

  public ServiceImpl(String name) throws RemoteException {
    this.name = name; 
  }

  @Override 
  public String service(String content) throws RemoteException{
      logger.debug("service invoked!!!!!!!!!!");
    return "server >> " + content; 
  } 
}