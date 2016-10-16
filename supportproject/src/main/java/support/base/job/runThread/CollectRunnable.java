package support.base.job.runThread;

import support.base.service.UserService;

public class CollectRunnable implements Runnable {
	private UserService userService;

	public CollectRunnable(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run() {
		//执行用户收藏商品信息
		userService.saveCollect();
		//删除所有product和topic的缓存
		userService.delCache();
		//更新商品和主题收藏量
		userService.updateCollectNum();
		//统计
		userService.statistics();
	}

}
