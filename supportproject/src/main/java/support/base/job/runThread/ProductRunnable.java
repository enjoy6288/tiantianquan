package support.base.job.runThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import support.base.pojo.po.Product;
import support.base.pojo.vo.ProductVo;
import support.base.service.ProductService;

public class ProductRunnable implements Runnable {
	private ProductService productService;

	public ProductRunnable(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public void run() {
		ProductVo vo = new ProductVo();
		vo.setScheduleTask("scheduleTask");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date now=new Date();
		vo.setShelvesTime(format.format(now));
		List<Product> products = productService.queryProducts(vo);
		for (Product product : products) {
			//已上线
			product.setStatus(1);
			product.setShelvesTime(now);
		}
		productService.updateProducts(products);
	}

}
