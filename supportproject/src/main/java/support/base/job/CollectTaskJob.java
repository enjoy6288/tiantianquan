package support.base.job;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import support.base.job.runThread.CollectRunnable;
import support.base.service.UserService;

/**
 * 执行用户的收藏入库任务
 */
@Service
public class CollectTaskJob {
	@Autowired
	UserService userService;
	Logger logger = Logger.getLogger(CollectTaskJob.class);
	private static ExecutorService workerPool = Executors.newFixedThreadPool(1);

	public void job() {
		logger.info("执行收藏任务" + new Date());
		workerPool.submit(new CollectRunnable(userService));
	}
}