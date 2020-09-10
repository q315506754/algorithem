package com.jiangli.ftp;

import com.jiangli.ftp.custom.CustomListenerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.AbstractUserManager;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2020/8/10 19:09
 *
 *
telnet localhost 21
USER aaaa
PASS bbbb

 ftp://127.0.0.1

 */
@Slf4j
public class FtpServerDemo {
    public static void main(String[] args) throws Exception {
        FtpServerFactory serverFactory = new FtpServerFactory();
        serverFactory.setUserManager(new AbstractUserManager() {
            @Override
            public User getUserByName(String username) throws FtpException {
                if ("aaaa".equals(username)) {
                    BaseUser baseUser = new BaseUser();
                    baseUser.setName("aaaa");
                    baseUser.setPassword("bbbb");
                    String userHomePath = "C:\\a\\bb\\cc\\";
                    File file = new File(userHomePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    baseUser.setHomeDirectory(userHomePath);
                    ArrayList<Authority> authorities = new ArrayList<>();
                    authorities.add(new ConcurrentLoginPermission(9999,9999));
                    authorities.add(new WritePermission());
                    //authorities.add(new TransferRatePermission(999999,999999));
                    baseUser.setAuthorities(authorities);
                    log.warn("suc:{}",baseUser);
                    System.out.println(baseUser);
                    return baseUser;
                }
                return null;
            }

            @Override
            public String[] getAllUserNames() throws FtpException {
                throw new FtpException("getAllUserNames");
                //return new String[0];
            }

            @Override
            public void delete(String username) throws FtpException {
                throw new FtpException("delete");
            }

            @Override
            public void save(User user) throws FtpException {
                throw new FtpException("save");
            }

            @Override
            public boolean doesExist(String username) throws FtpException {
                throw new FtpException("doesExist");

                //return false;
            }

            @Override
            public User authenticate(Authentication authentication) throws AuthenticationFailedException {
                if (authentication instanceof UsernamePasswordAuthentication) {
                    UsernamePasswordAuthentication pwdAuth = (UsernamePasswordAuthentication) authentication;
                    String username = pwdAuth.getUsername();
                    try {
                        User userByName = getUserByName(username);
                        String password = pwdAuth.getPassword();
                        if (userByName.getPassword().equals(password)) {
                            return userByName;
                        }
                    } catch (FtpException e) {
                        e.printStackTrace();
                        throw new AuthenticationFailedException(e);
                    }
                }
                throw new AuthenticationFailedException("authenticate");
                //return null;
            }
        });

        //5、配置自定义用户事件
        Map<String, Ftplet> ftpLets = new HashMap();
        ftpLets.put("ftpService", new MyFtpPlet());
        serverFactory.setFtplets(ftpLets);

        // set the port of the listener

        //factory.setPort(2221);

// replace the default listener
        ListenerFactory factory = new CustomListenerFactory();
        //ListenerFactory factory = new ListenerFactory();
        factory.setPort(21);
        Listener listener = factory.createListener();

        //NioListener nioListener = (NioListener) listener;
        //Field acceptor = nioListener.getClass().getDeclaredField("acceptor");
        //acceptor.setAccessible(true);
        //SocketAcceptor socketAcceptor = (SocketAcceptor) acceptor.get(nioListener);
        //
        //List<IoFilterChain.Entry> all = socketAcceptor.getFilterChain().getAll();
        //for (IoFilterChain.Entry entry : all) {
        //    if (entry.getName().equals("codec")) {
        //        System.out.println(entry.getFilter());
        //    }
        //}

        serverFactory.addListener("default", listener);


        FtpServer server = serverFactory.createServer();
// start the server
        server.start();
    }
}
