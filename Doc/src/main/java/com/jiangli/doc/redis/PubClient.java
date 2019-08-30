package com.jiangli.doc.redis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol;

/**
 * @author Jiangli
 * @date 2019/8/22 15:19
 */
public class PubClient extends Client {
    public PubClient(String host) {
        super(host);
    }

    public PubClient(String host, int port) {
        super(host, port);
    }

    @Override
    public Connection sendCommand(Protocol.Command cmd, String... args) {
        return super.sendCommand(cmd, args);
    }

    @Override
    public Connection sendCommand(Protocol.Command cmd, byte[]... args) {
        return super.sendCommand(cmd, args);
    }

    @Override
    public Connection sendCommand(Protocol.Command cmd) {
        return super.sendCommand(cmd);
    }

    @Override
    public String getStatusCodeReply() {
        return super.getStatusCodeReply();
    }
}
