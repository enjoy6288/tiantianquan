package support.base.util;


import redis.clients.jedis.Jedis;

public class RedisUtil {
	private Jedis jedis;
	public Jedis getJedis(){
		String redis_server = SpringPropertyUtil.getContextProperty(Constant.REDIS_SERVER);
		jedis = new Jedis(redis_server, 6379);
		return jedis;
	}
	
	public void closeRedis(){
		jedis.close();
		jedis=null;
	}
	
}
