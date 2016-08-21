package support.base.job;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import support.base.action.ProductAction;
import support.base.job.runThread.ProductRunnable;
import support.base.service.ProductService;

/**
 * 执行product上线任务
 */
@Service
public class ProductTaskJob {
	@Autowired
	ProductService productService;
	Logger logger = Logger.getLogger(ProductTaskJob.class);
	private static ExecutorService workerPool = Executors.newFixedThreadPool(1);

	public void job() {
		logger.info("执行product任务" + new Date());
		workerPool.submit(new ProductRunnable(productService));
	}
}