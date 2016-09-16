package support.base.util;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	private Jedis jedis;

	public Jedis getJedis() {
		String redis_server = SpringPropertyUtil.getContextProperty(Constant.REDIS_SERVER);
		jedis = new Jedis(redis_server, 6379);
		return jedis;
	}

	public void closeRedis() {
		jedis.close();
		jedis = null;
	}

	public static void main(String[] args) {
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		jedis.sadd("jedis:123:0", "aa", "bb", "cc");
		jedis.sadd("jedis:123:1", "dd", "ee", "ff");
		jedis.sadd("jedis:abc:0", "gg", "mm", "nn");
		jedis.sadd("jedis:abc:1", "qq", "ww", "x");
		Set<String> keys = jedis.keys("jedis:*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		Set<String> values = jedis.sunion(arrayKeys);
		System.out.println(values);
		// Long num = jedis.del(arrayKeys);
		// System.out.println(num);
	}

}
