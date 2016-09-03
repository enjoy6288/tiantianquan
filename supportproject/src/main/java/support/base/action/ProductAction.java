package support.base.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.vo.PageQuery;
import support.base.pojo.vo.ProductVo;
import support.base.pojo.vo.StatisticDataInfo;
import support.base.process.context.Config;
import support.base.process.result.DataGridResultInfo;
import support.base.process.result.ResultUtil;
import support.base.process.result.SubmitResultInfo;
import support.base.service.ProductService;
import support.base.util.CommonUtil;
import support.base.util.Constant;
import support.base.util.SpringPropertyUtil;

@Controller
@RequestMapping("/product")
public class ProductAction {

	@Autowired
	private ProductService productService;
	Logger logger = Logger.getLogger(ProductAction.class);

	// 商品详情
	@RequestMapping("/productDetail")
	public String productDetail(Model model, ProductVo vo) {
		List<Product> list = productService.queryProducts(vo);
		if (list != null && list.size() > 0) {
			model.addAttribute("product", list.get(0));
		}
		return "/product/productDetail";
	}

	// 商品统计
	@RequestMapping("/statisticsView")
	public String statisticsView(Model model, ProductVo vo) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		model.addAttribute("product", vo);
		return "/product/productStatistics";
	}

	// 商品统计
	@RequestMapping("/statistics")
	public @ResponseBody
	DataGridResultInfo statistics(ProductVo vo, int page, int rows) {
		// 列表的总数
		int total = productService.queryProductsNum(vo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		vo.setPageQuery(pageQuery);// 设置分页参数

		// 分页查询列表
		List<Product> list = productService.queryProducts(vo);
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}

	// 添加商品页面
	@RequestMapping("/addProductView")
	public String addProductView(Model model, String topicId) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		model.addAttribute("topicId", topicId);
		return "/product/addProduct";
	}

	// 保存商品
	@RequestMapping("/saveProduct")
	public @ResponseBody
	SubmitResultInfo saveProduct(ProductVo vo, MultipartFile img) throws Exception {

		productService.saveProduct(vo, img);

		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 201, null));
	}

	// 查询商品页面
	@RequestMapping("/queryProductView")
	public String queryProductView(Model model, ProductVo vo) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		model.addAttribute("product", vo);
		return "/product/productList";
	}

	// 查询商品
	@RequestMapping("/queryProducts")
	public @ResponseBody
	DataGridResultInfo queryProducts(ProductVo vo, int page, int rows) {
		// 列表的总数
		int total = productService.queryProductsNum(vo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		vo.setPageQuery(pageQuery);// 设置分页参数

		// 分页查询列表
		List<Product> list = productService.queryProducts(vo);
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}

	// 修改商品页面
	@RequestMapping("/updateProductView")
	public String updateProductView(Model model, ProductVo vo) {
		// 获取类别信息
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		// 获取商品信息
		List<Product> products = productService.queryProducts(vo);
		if (products != null && products.size() > 0) {
			model.addAttribute("product", products.get(0));
		}
		return "/product/editProduct";
	}

	// 修改商品
	@RequestMapping("/updateProduct")
	public @ResponseBody
	SubmitResultInfo updateProduct(ProductVo vo, MultipartFile img) throws Exception {
		Product product = new Product();
		CommonUtil.VoToPo(vo, product);
		if (img != null) {
			// 删除原来的文件
			String oldImg = vo.getOldImg();
			if(oldImg!=null){
				String imgPrefix = SpringPropertyUtil.getContextProperty(Constant.IMG_PREFIX);
			    String partOldImg = oldImg.substring(imgPrefix.length());
			    oldImg=SpringPropertyUtil.getContextProperty(Constant.FILE_PATH_PREFIX)+partOldImg;
				File file = new File(oldImg);
				if (file.exists()) {
					file.delete();
				}
			}
			product.setImg(CommonUtil.upload(img,Constant.PRODUCT));
		}
		productService.updateProduct(product);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 201, null));
	}

	// 商品60天数据统计页面
	@RequestMapping("/product60DaysView")
	public String product60DaysView() {
		return "/product/product60Days";
	}

	// 商品60天数据统计
	@RequestMapping("/product60Days")
	public @ResponseBody
	DataGridResultInfo product60Days() {
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		List dataInfo = new ArrayList<StatisticDataInfo>();
		StatisticDataInfo info = new StatisticDataInfo();
		info.setCTR(200l);
		info.setPV(300l);
		info.setUV(400l);
		info.setDate("2016-8-15");
		for (int i = 0; i < 10; i++) {
			dataInfo.add(info);
		}
		dataGridResultInfo.setRows(dataInfo);
		return dataGridResultInfo;
	}

}
