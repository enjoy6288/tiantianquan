package support.base.job.runThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import redis.clients.jedis.Jedis;
import support.base.pojo.po.Topic;
import support.base.pojo.vo.TopicVo;
import support.base.service.TopicService;
import support.base.util.RedisUtil;

public class TopicRunnable implements Runnable {
	private TopicService topicService;

	public TopicRunnable(TopicService topicService) {
		this.topicService = topicService;
	}

	@Override
	public void run() {
		TopicVo vo=new TopicVo();
		vo.setScheduleTask("scheduleTask");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date now=new Date();
		vo.setShelvesTime(format.format(now));
		List<Topic> topics = topicService.queryTopics(vo);
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		for (Topic topic : topics) {
			//已上线
			topic.setStatus(1);
			topic.setShelvesTime(now);
		}
		topicService.updateTopics(topics);
	}

}
