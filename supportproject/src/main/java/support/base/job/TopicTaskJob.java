package support.base.job;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import support.base.job.runThread.TopicRunnable;
import support.base.service.TopicService;

@Service
public class TopicTaskJob {
	@Autowired
	TopicService topicService;
	Logger logger = Logger.getLogger(TopicTaskJob.class);
	private static ExecutorService workerPool = Executors.newFixedThreadPool(1);

	public void job() {
		logger.info("执行topic任务" + new Date());
		workerPool.submit(new TopicRunnable(topicService));
	}
}