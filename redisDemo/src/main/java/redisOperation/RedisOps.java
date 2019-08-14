package redisOperation;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

import static java.lang.System.*;

public class RedisOps {
    Jedis jedis;
    @Before
    public void setup (){
        jedis = new Jedis("localhost");
    }

    /**
     * redis存储初级的字符串
     */
    @Test
    public void testBasicString() {
        //添加数据
        jedis.set("name", "jane");
        out.println(jedis.get("name"));

        //修改数据
        // 1: 追加
        jedis.append("name"," lee");
        out.println(jedis.get("name"));
        // 2: 覆写
        jedis.set("name", " Lee Jane");
        out.println(jedis.get("name"));

        //删除key对应的记录
        jedis.del("name");
        out.println(jedis.get("name"));

        /**
         * mset相当于
         * jedis.set("name","meepo");
         * jedis.set("dota","poofu");
         */
        jedis.mset("name","meepo","dota","poofu");
        out.println(jedis.mget("name","dota"));
    }
    /**
     * redis 操作map
     */
    @Test
    public void testMap() {
        Map<String, String> user = new HashMap<>();
        user.put("name","meepo");
        user.put("pwd","password");
        user.put("gender", "female");
        jedis.hmset("user", user);
        List<String> name = jedis.hmget("user", "pwd");
        out.println(name);

        /*
          删除键值
         */
        jedis.hdel("user", "pwd");
        out.println(jedis.hget("user", "pwd")); //null
        out.println(jedis.hlen("user")); // 2
        out.println(jedis.exists("user"));// yes
        out.println(jedis.hexists("user","pwd")); // no
        out.println(jedis.hkeys("user"));// name gender
        out.println(jedis.hvals("user"));// meepo female

        //便利键值
        jedis.hkeys("user").forEach(out::println); // name gender
    }

    /**
     * 操作list
     */
    @Test
    public void testList() {
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework", "spring-boot");
        jedis.lpush("java framework", "spring-cloud");

        out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * 操作set
     */
    @Test
    public void testSet() {
        jedis.sadd("sname","meepo");
        jedis.sadd("sname","poofu");
        jedis.sadd("sname","noname");
        jedis.sadd("sname","dota");

        //移除
        jedis.srem("sname","noname");

        out.println(jedis.smembers("sname")); // meepo poofu dota
        out.println(jedis.sismember("sname", "noname") + " : " + jedis.sismember("sname", "dota")); // false : true
        out.println(jedis.srandmember("sname")); // 返回一个随机成员
        out.println(jedis.scard("sname")); // 3 返回元素个数

    }

    @Test
    public void test() throws InterruptedException {
        //向key中传入通配符
        out.println(jedis.keys("*")); // get all keys
        out.println(jedis.keys("*name")); // get keys ending with name
        out.println(jedis.del("snaddd")); // delete key snaddd  success : 1  failure (or does not exist): 0
        jedis.setex("timekey", 10, "min"); // set life time to key , unit: s
        Thread.sleep(5000);
        out.println(jedis.ttl("timekey")); // return 5 s
        jedis.setex("timekey", 100, "min");
        out.println(jedis.exists("timekey")); // check if the key exists  here yes and return true
        jedis.rename("timekey", "key"); // rename the key
        out.println(jedis.get("timekey")); // now that timekey is renamed as key , here comes out null
        out.println(jedis.get("key")); // the key is the old timekey ,so its value is min

        //jedis 排序
        jedis.del("a");
        jedis.lpush("a", "100");
        jedis.lpush("a", "0");
        jedis.lpush("a", "120");
        jedis.lpush("a", "100");
        jedis.lpush("a", "90");
        out.println(jedis.lrange("a", 0, -1));
        out.println(jedis.sort("a"));
        out.println(jedis.lrange("a", 0, -1));



    }
}
