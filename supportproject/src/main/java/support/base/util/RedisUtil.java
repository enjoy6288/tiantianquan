package support.base.util;

import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	public static Logger logger = Logger.getLogger(RedisUtil.class);
	// private static JedisPool pool;
	private static JedisPool pool;
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxTotal")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWaitMillis")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));

		// JedisShardInfo jedisShardInfo1 = new
		// JedisShardInfo(bundle.getString("redis1.ip"), Integer.valueOf(bundle
		// .getString("redis.port")));
		// JedisShardInfo jedisShardInfo2 = new
		// JedisShardInfo(bundle.getString("redis2.ip"), Integer.valueOf(bundle
		// .getString("redis.port")));
		// List<JedisShardInfo> shards = new LinkedList<JedisShardInfo>();
		// shards.add(jedisShardInfo1);
		// shards.add(jedisShardInfo2);
		// pool = new JedisPool(config, shards);

	}

	// private static Jedis getJedis() {
	// Jedis jedis = pool.getResource();
	// return jedis;
	// }
	private static Jedis getJedis() {
		Jedis jedis = pool.getResource();
		return jedis;
	}

	public static String get(String key) {
		Jedis jedis = null;
		String value = "";
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法get错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return value;
	}

	public static void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法set错误【"+key+"】"+"【"+value+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static Long scard(String key) {
		Jedis jedis = null;
		Long scard = null;
		try {
			jedis = getJedis();
			scard = jedis.scard(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法scard错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return scard;
	}

	public static void setKeyWithExpire(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法setex错误【"+key+"】"+"【"+value+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}


	public static Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> smembers = null;
		try {
			jedis = getJedis();
			smembers = jedis.smembers(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法smembers错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return smembers;
	}

	public static void sadd(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.sadd(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法sadd错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}
	public static void sadd(String key, String... value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.sadd(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法sadd错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static boolean sismember(String key, String value) {
		Jedis jedis = null;
		boolean isExist = false;
		try {
			jedis = getJedis();
			isExist = jedis.sismember(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法sismember错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return isExist;
	}

	public static void srem(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.srem(key, value);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法srem错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static Set<String> keys(String key) {
		Jedis jedis = null;
		Set<String> keys = null;
		try {
			jedis = getJedis();
			keys = jedis.keys(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法keys错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return keys;
	}

	public static void incr(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.incr(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法incr错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static Set<String> sunion(String[] keys) {
		Jedis jedis = null;
		Set<String> sunion = null;
		try {
			jedis = getJedis();
			sunion = jedis.sunion(keys);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法sunion错误【"+keys+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
		return sunion;
	}

	public static void del(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法del错误【"+key+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}

	public static void del(final String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(keys);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			logger.error("redis方法del错误【"+keys+"】" + e.getMessage());
		} finally {
			pool.returnResource(jedis);
		}
	}
}
