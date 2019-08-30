package com.jiangli.doc.redis;

import com.jiangli.doc.sql.helper.aries.Ariesutil;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

import java.lang.reflect.Method;

/**
 * @author Jiangli
 * @date 2019/8/22 15:01
 */
public class RedisDirectUtil {
    public static void main(String[] args) throws Exception {
       //new Ariesutil().getYanfaPool();
        System.out.println(Ariesutil.class);
        for (Method method : Ariesutil.class.getMethods()) {
            //System.out.println(method);
        }

        Jedis jedis = new Jedis("192.168.9.205", 6379);
        jedis.get("a");
        jedis.set("a","aaaa");
        jedis.hgetAll("aaa");
        Client client1 = jedis.getClient();

        //PubClient client = new PubClient("192.168.9.205", 6379);
        PubClient client = new PubClient(client1.getHost(), client1.getPort());
        System.out.println(client);

        client.sendCommand(Protocol.Command.GET, "aaa");
        System.out.println(client.getStatusCodeReply());

        //String cmd = "get   aaa";
        String cmd = "hgetall   aaa";
        cmd = cmd.replaceAll("\\s+"," ");
        String fCmd = cmd.split(" ")[0].toUpperCase();
        Protocol.Command command = Protocol.Command.valueOf(fCmd);
        String restCmd = cmd.substring(fCmd.length()).trim();
        //System.out.println(restCmd);
        client.sendCommand(command, restCmd);
        System.out.println(client.getStatusCodeReply());

        //System.out.println(client.getBulkReply());

        //client.set("aaa","bbbxxx");
        //client.get("aaa");
        //Method getStatusCodeReply = client.getClass().getDeclaredMethod("getStatusCodeReply");
        //getStatusCodeReply.setAccessible(true);
        //System.out.println(client.getBulkReply());
        //System.out.println(getStatusCodeReply.invoke(client));

        //client.
        //Ariesutil ariesutil = new Ariesutil();
        //System.out.println();
       //new Ariesutil.INSTANCE.getYanfaPool();
    }

}
