package support.base.util;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	private static Jedis jedis;
	public static Jedis getJedis(){
		String redis_server = SpringPropertyUtil.getContextProperty(Constant.REDIS_SERVER);
		jedis = new Jedis(redis_server, 6379);
		return jedis;
	}
	
	public static void closeRedis(){
		jedis.close();
		jedis=null;
	}
	
	public static void main(String[] args) {
		jedis = new Jedis("172.24.5.62", 6379);
		
//		jedis.zadd("topic:id",20160907D,"0b7f8eb2-6ce9-11e6-8baa-d00d00771857");
//		jedis.zadd("topic:id",20160908D,"1782d4c5-6c31-11e6-8baa-d00d00771857");
//		jedis.zadd("topic:id",20160908D,"52629aa0-6c34-11e6-8baa-d00d00771857");
//		jedis.zadd("topic:id",20160909D,"9d61b64e-6c42-11e6-8baa-d00d00771857");
		
		Set<String> zrangeByScore = jedis.zrangeByScore("topic:id", 20160907, 20160907);
		for(String mb: zrangeByScore){
			
			System.out.println(mb);
			
		}
		
		jedis.close();
	}
}
