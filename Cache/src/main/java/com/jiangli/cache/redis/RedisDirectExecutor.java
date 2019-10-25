package com.jiangli.cache.redis;

import redis.clients.jedis.*;
import redis.clients.util.RedisInputStream;
import redis.clients.util.SafeEncoder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2019/10/22 13:16
 */
public class RedisDirectExecutor {
    private static Map<String, String> hostMap = new LinkedHashMap<>();

    static {
        hostMap.put("common", "redis://192.168.9.170:19000");
        hostMap.put("message", "redis://192.168.9.170:19000");
        hostMap.put("video", "redis://192.168.9.170:19000");
    }

    public static void main(String[] args) {
        RedisDirectExecutor executor = new RedisDirectExecutor();
        System.out.println(executor.doExecuteCmd("common","set a test"));
        System.out.println(executor.doExecuteCmd("common","get a "));
    }

    public Client getClient(String hostShortName) {
        String host = hostMap.get(hostShortName);
        if (host != null) {
            //redis.clients.jedis.JedisPoolConfig
            JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), URI.create(host));
            //JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), host);
            Jedis jedis = jedisPool.getResource();

            //if (host.contains("@") && host.contains("//")) {
            //    String unamepassword = host.substring(host.indexOf("//") + 2, host.lastIndexOf("@"));
            //    String pwd = unamepassword.split(":")[1];
            //    jedis.auth(pwd);
            //}

            Client client1 = jedis.getClient();
            //PubClient client = new PubClient("192.168.9.205", 6379);
            //PubClient client = new PubClient(client1.getHost(), client1.getPort());
            return client1;
        }
        return null;
    }

    public String doExecuteCmd(String hostShortName, String cmd) {
        String res = "null";
        Client client = getClient(hostShortName);
        if (client == null) {
            throw new IllegalArgumentException("不支持的host:" + hostShortName);
        }
        cmd = cmd.trim();
        cmd = cmd.replaceAll("\\s+", " ");
        String fCmd = cmd.split(" ")[0].toUpperCase();
        String restCmd = cmd.substring(fCmd.length()).trim();

        //特殊命令
        Protocol.Command command = Protocol.Command.valueOf(fCmd);
        //client.sendCommand(command, restCmd.split(" "));

        try {
            Method sendCommand = Connection.class.getDeclaredMethod("sendCommand", Protocol.Command.class, String[].class);
            sendCommand.setAccessible(true);
            sendCommand.invoke(client, command, restCmd.split(" "));

            String redisRs = tryGetStringResult(client);
            res = String.valueOf(redisRs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static Object tryInvokeFunc(Class cls, Object instance, String method) {
        try {
            Method inputStream = cls.getDeclaredMethod(method);
            inputStream.setAccessible(true);
            return inputStream.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object tryGetField(Class cls, Object instance, String fieldName) {
        try {
            Field inputStream = cls.getDeclaredField(fieldName);
            inputStream.setAccessible(true);
            return inputStream.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String tryGetStringResult(Client client) {
        String redisRs = null;

        try {
            //先要推送
            //client.flush();
            tryInvokeFunc(Connection.class, client, "flush");

            //RedisInputStream
            //RedisInputStream inputStream = client.getRedisInputStream();
            RedisInputStream inputStream = (RedisInputStream) tryGetField(Connection.class, client, "inputStream");

            Object read = Protocol.read(inputStream);

            if (read instanceof Number) {
                return String.valueOf(read);
            }

            if (read instanceof byte[]) {
                return SafeEncoder.encode((byte[]) read);
            }

            if (read instanceof List) {
                return BuilderFactory.STRING_LIST.build(read).toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //try {
        //    if (redisRs == null) {
        //        redisRs = client.getStatusCodeReply();
        //    }
        //} catch (Exception e) {
        //}
        //
        //try {
        //    if (redisRs == null) {
        //        redisRs =  client.getMultiBulkReply().toString();
        //    }
        //} catch (Exception e) {
        //}
        //
        //try {
        //    if (redisRs == null) {
        //        redisRs =  BuilderFactory.STRING_SET
        //                .build(client.getBinaryMultiBulkReply()).toString();
        //    }
        //} catch (Exception e) {
        //}
        //
        //try {
        //    if (redisRs == null) {
        //        redisRs =  BuilderFactory.STRING_MAP
        //                .build(client.getBinaryMultiBulkReply()).toString();
        //    }
        //} catch (Exception e) {
        //}
        //
        //try {
        //    if (redisRs == null) {
        //        redisRs =  client.getIntegerReply().toString();
        //    }
        //} catch (Exception e) {
        //}

        return redisRs;
    }

}
