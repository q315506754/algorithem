package com.jiangli.ftp.custom;

import org.apache.ftpserver.DataConnectionConfiguration;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.ipfilter.SessionFilter;
import org.apache.ftpserver.listener.nio.NioListener;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.firewall.Subnet;
import org.apache.mina.transport.socket.SocketAcceptor;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.List;

/**
 * @author Jiangli
 * @date 2020/8/11 11:29
 */
public class CustomNioListener extends NioListener {
    public CustomNioListener(String serverAddress, int port, boolean implicitSsl, SslConfiguration sslConfiguration, DataConnectionConfiguration dataConnectionConfig, int idleTimeout, List<InetAddress> blockedAddresses, List<Subnet> blockedSubnets) {
        super(serverAddress, port, implicitSsl, sslConfiguration, dataConnectionConfig, idleTimeout, blockedAddresses, blockedSubnets);
    }

    public CustomNioListener(String serverAddress, int port, boolean implicitSsl, SslConfiguration sslConfiguration, DataConnectionConfiguration dataConnectionConfig, int idleTimeout, SessionFilter sessionFilter) {
        super(serverAddress, port, implicitSsl, sslConfiguration, dataConnectionConfig, idleTimeout, sessionFilter);
    }

    @Override
    public synchronized void start(FtpServerContext context) {
        super.start(context);

        Field acceptor = null;
        try {
            acceptor = NioListener.class.getDeclaredField("acceptor");
            acceptor.setAccessible(true);

            SocketAcceptor socketAcceptor = (SocketAcceptor) acceptor.get(this);

            if (socketAcceptor != null) {
                List<IoFilterChain.Entry> all = socketAcceptor.getFilterChain().getAll();
                for (IoFilterChain.Entry entry : all) {
                    if (entry.getName().equals("codec")) {
                        //ProtocolCodecFilter filter = ((ProtocolCodecFilter) entry.getFilter());
                        //替换成我们自定义的解码器工厂
                        entry.replace(new ProtocolCodecFilter(new CustomFtpServerProtocolCodecFactory()));
                        //System.out.println(filter);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
