package support.base.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import support.base.dao.mapper.ProductMapper;
import support.base.dao.mapper.StatisticsMapper;
import support.base.dao.mapper.SweetGoingtoMapper;
import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.po.SweetGoingto;
import support.base.pojo.po.SweetStatistics;
import support.base.pojo.vo.PageQuery;
import support.base.pojo.vo.ProductVo;
import support.base.pojo.vo.StatisticDataInfo;
import support.base.pojo.vo.SweetStatisticsVo;
import support.base.process.context.Config;
import support.base.process.result.DataGridResultInfo;
import support.base.process.result.ResultUtil;
import support.base.process.result.SubmitResultInfo;
import support.base.service.ProductService;
import support.base.util.CommonUtil;
import support.base.util.Constant;
import support.base.util.RedisUtil;
import support.base.util.SpringPropertyUtil;

@Controller
@RequestMapping("/product")
public class ProductAction {

	@Autowired
	private ProductService productService;
	Logger logger = Logger.getLogger(ProductAction.class);

	@Autowired
	private StatisticsMapper statisticsMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SweetGoingtoMapper goingtoMapper;

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
	@RequestMapping("/productStatistics")
	public @ResponseBody
	DataGridResultInfo productStatistics(ProductVo vo, int page, int rows) {
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

	// 商品统计
	@RequestMapping("/statistics")
	public @ResponseBody
	DataGridResultInfo statistics(SweetStatisticsVo vo) {
		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageQuery_end(7);
		vo.setPageQuery(pageQuery);// 设置分页参数

		List<SweetStatistics> statistics = statisticsMapper.queryStatistics(vo);
		long pvs = 0;
		long uvs = 0;
		for (SweetStatistics ss : statistics) {
			pvs += Long.parseLong(ss.getPv());
			uvs += Long.parseLong(ss.getUv());
		}
		for (int i = 0; i < statistics.size(); i++) {
			if (i == 0) {
				SweetStatistics sweetStatistics = statistics.get(i);
				sweetStatistics.setDesc("今日概览");
			}
			if (i == 1) {
				SweetStatistics sweetStatistics = statistics.get(i);
				sweetStatistics.setDesc("昨日概览");
			}
			if (i == 2) {
				SweetStatistics sweetStatistics = statistics.get(i);
				sweetStatistics.setDesc("周平均概览");
				pvs = pvs / 7;
				uvs = uvs / 7;
				sweetStatistics.setPv(pvs + "");
				sweetStatistics.setUv(uvs + "");
			}
		}
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setRows(statistics);
		return dataGridResultInfo;
	}

	// 添加商品页面
	@RequestMapping("/addProductView")
	public String addProductView(Model model, String topicId) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		List<SweetGoingto> goingTo = goingtoMapper.queryGoingTo();
		model.addAttribute("goingTos", goingTo);
		model.addAttribute("categorys", categorys);
		model.addAttribute("topicId", topicId);
		return "/product/addProduct";
	}

	// 查询已经存在的排序值
	@RequestMapping("/querySortValue")
	public @ResponseBody
	JSONObject querySortValue(String shelvesTime) {
		List<Integer> sortValue = productMapper.querySortValue(shelvesTime);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sortValue", sortValue);
		return jsonObject;

	}

	// 查询已经存在的排序值
	@RequestMapping("/sortValueExist")
	public @ResponseBody
	JSONObject sortValueExist(ProductVo vo) {
		int sortValueExist = productMapper.sortValueExist(vo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sortValueExist", sortValueExist);
		return jsonObject;

	}

	// 保存商品
	@RequestMapping("/saveProduct")
	public @ResponseBody
	SubmitResultInfo saveProduct(ProductVo vo, MultipartFile img) throws Exception {

		productService.saveProduct(vo, img);

		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> keys = jedis.keys("*product*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			jedis.del(arrayKeys);
		}
		redisUtil.closeRedis();

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
		List<SweetGoingto> goingTo = goingtoMapper.queryGoingTo();
		model.addAttribute("goingTos", goingTo);
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
			if (oldImg != null) {
				String imgPrefix = SpringPropertyUtil.getContextProperty(Constant.IMG_PREFIX);
				String partOldImg = oldImg.substring(imgPrefix.length());
				oldImg = SpringPropertyUtil.getContextProperty(Constant.FILE_PATH_PREFIX) + partOldImg;
				File file = new File(oldImg);
				if (file.exists()) {
					file.delete();
				}
			}
			product.setImg(CommonUtil.upload(img, Constant.PRODUCT));
		}
		product.setLinkUrl(vo.getGoingTo()+vo.getLinkUrl());
		productService.updateProduct(product);

		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> keys = jedis.keys("*product*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			jedis.del(arrayKeys);
		}
		redisUtil.closeRedis();

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
	DataGridResultInfo product60Days(SweetStatisticsVo vo) {
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageQuery_end(60);
		vo.setPageQuery(pageQuery);// 设置分页参数
		List<SweetStatistics> statistics = statisticsMapper.queryStatistics(vo);
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setRows(statistics);
		return dataGridResultInfo;
	}

}
