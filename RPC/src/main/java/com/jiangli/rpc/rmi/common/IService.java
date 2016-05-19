package com.jiangli.rpc.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/19 0019 15:17
 */
public interface IService extends Remote {
    String service(String content) throws RemoteException;
}
