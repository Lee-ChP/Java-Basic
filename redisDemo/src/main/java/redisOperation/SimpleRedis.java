package redisOperation;

import redis.clients.jedis.Jedis;

public class SimpleRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo","tball");
        String value = jedis.get("foo");
        System.out.println(value);
    }
}
