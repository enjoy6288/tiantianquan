package support.base.job.runThread;

import support.base.service.UserService;

public class CollectRunnable implements Runnable {
	private UserService userService;

	public CollectRunnable(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run() {
		//userService.saveCollect();
	}

}
