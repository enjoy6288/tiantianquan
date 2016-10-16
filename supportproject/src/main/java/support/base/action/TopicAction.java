package support.base.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import support.base.dao.mapper.SweetGoingtoMapper;
import support.base.dao.mapper.TopicMapper;
import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.po.SweetGoingto;
import support.base.pojo.po.Topic;
import support.base.pojo.vo.PageQuery;
import support.base.pojo.vo.ProductVo;
import support.base.pojo.vo.StatisticDataInfo;
import support.base.pojo.vo.TopicVo;
import support.base.process.context.Config;
import support.base.process.result.DataGridResultInfo;
import support.base.process.result.ResultUtil;
import support.base.process.result.SubmitResultInfo;
import support.base.service.ProductService;
import support.base.service.TopicService;
import support.base.util.CommonUtil;
import support.base.util.Constant;
import support.base.util.RedisUtil;
import support.base.util.SpringPropertyUtil;

@Controller
@RequestMapping("/topic")
public class TopicAction {
	@Autowired
	private TopicService topicService;
	@Autowired
	private ProductService productService;
	@Autowired
	private TopicMapper topicMapper;	
	@Autowired
	private SweetGoingtoMapper goingtoMapper;

	// 查询已经存在的排序值
	@RequestMapping("/querySortValue")
	public @ResponseBody
	JSONObject querySortValue(String shelvesTime) {
		List<Integer> sortValue = topicMapper.querySortValue(shelvesTime);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sortValue", sortValue);
		return jsonObject;
	}

	// 查询已经存在的排序值
	@RequestMapping("/sortValueExist")
	public @ResponseBody
	JSONObject sortValueExist(ProductVo vo) {
		int sortValueExist = topicMapper.sortValueExist(vo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sortValueExist", sortValueExist);
		return jsonObject;
	}

	// 添加专题页面
	@RequestMapping("/addTopicView")
	public String addTopicView(Model model) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		List<SweetGoingto> goingTo = goingtoMapper.queryGoingTo();
		model.addAttribute("goingTos", goingTo);
		model.addAttribute("categorys", categorys);
		return "/topic/addTopic";
	}

	// 保存主题
	@RequestMapping("/saveTopic")
	public @ResponseBody
	SubmitResultInfo saveTopic(TopicVo vo, MultipartFile out, MultipartFile inner) throws Exception {
		topicService.saveTopic(vo, out, inner);

		// 删除redis缓存
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> keys = jedis.keys("*topic*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			jedis.del(arrayKeys);
		}
		redisUtil.closeRedis();

		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 201, null));
	}

	// 修改主题页面
	@RequestMapping("/updateTopicView")
	public String updateTopicView(Model model, TopicVo vo) {
		// 获取类别信息
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		List<SweetGoingto> goingTo = goingtoMapper.queryGoingTo();
		model.addAttribute("goingTos", goingTo);
		// 获取商品信息
		List<Topic> topics = topicService.queryTopics(vo);
		if (topics != null && topics.size() > 0) {
			model.addAttribute("topic", topics.get(0));
		}
		return "/topic/editTopic";
	}

	// 修改主题
	@RequestMapping("/updateTopic")
	public @ResponseBody
	SubmitResultInfo updateTopic(TopicVo vo, MultipartFile out, MultipartFile inner) throws Exception {
		Topic topic = new Topic();
		CommonUtil.VoToPo(vo, topic);

		// 外部banner
		if (out != null) {
			String oldImg = vo.getOldOut();
			if (oldImg != null) {
				String imgPrefix = SpringPropertyUtil.getContextProperty(Constant.IMG_PREFIX);
				String partOldImg = oldImg.substring(imgPrefix.length());
				oldImg = SpringPropertyUtil.getContextProperty(Constant.FILE_PATH_PREFIX) + partOldImg;
				File file = new File(oldImg);
				if (file.exists()) {
					file.delete();
				}
			}
			topic.setBannerOutterimg(CommonUtil.upload(out, Constant.TOPIC));
		}

		// 内部banner
		if (inner != null) {
			String oldImg = vo.getOldInner();
			if (oldImg != null) {
				String imgPrefix = SpringPropertyUtil.getContextProperty(Constant.IMG_PREFIX);
				String partOldImg = oldImg.substring(imgPrefix.length());
				oldImg = SpringPropertyUtil.getContextProperty(Constant.FILE_PATH_PREFIX) + partOldImg;
				File file = new File(oldImg);
				if (file.exists()) {
					file.delete();
				}
			}
			topic.setBannerInnerimg(CommonUtil.upload(inner, Constant.TOPIC));
		}
		topic.setLinkUrl(vo.getGoingTo()+vo.getLinkUrl());
		topicService.updateTopic(topic);
		// 删除redis缓存
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		Set<String> keys = jedis.keys("*topic*");
		String[] arrayKeys = keys.toArray(new String[keys.size()]);
		if (arrayKeys.length > 0) {
			jedis.del(arrayKeys);
		}
		redisUtil.closeRedis();

		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 201, null));
	}

	// 查询专题页面
	@RequestMapping("/queryTopicView")
	public String queryTopicView(Model model, int status) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		model.addAttribute("status", status);
		return "/topic/topicList";
	}

	// 查询商品
	@RequestMapping("/queryTopics")
	public @ResponseBody
	DataGridResultInfo queryTopics(TopicVo vo, int page, int rows) {
		// 列表的总数
		int total = topicService.queryTopicsNum(vo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		vo.setPageQuery(pageQuery);// 设置分页参数

		// 分页查询列表
		List<Topic> list = topicService.queryTopics(vo);

		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}

	// 专题60天数据统计页面
	@RequestMapping("/topic60DaysView")
	public String topic60DaysView() {
		return "/topic/topic60Days";
	}

	// 专题60天数据统计
	@RequestMapping("/topic60Days")
	public @ResponseBody
	DataGridResultInfo topic60Days() {
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

	// 专题商品页面
	@RequestMapping("/topicProductView")
	public String topicProductView(Model model, String topicId) {
		model.addAttribute("topicId", topicId);
		return "/topic/topicProduct";
	}

	// 专题商品数据
	@RequestMapping("/topicProduct")
	public @ResponseBody
	DataGridResultInfo topicProduct(ProductVo vo, int page, int rows) {
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

	// 商品统计页面
	@RequestMapping("/statisticsView")
	public String statisticsView(Model model, TopicVo vo) {
		List<Category> categorys = (List<Category>) productService.queryCategorys(null);
		model.addAttribute("categorys", categorys);
		model.addAttribute("topic", vo);
		return "/topic/topicStatistics";
	}

	// 商品统计
	@RequestMapping("/statistics")
	public @ResponseBody
	DataGridResultInfo statistics(TopicVo vo, int page, int rows) {
		// 列表的总数
		int total = topicService.queryTopicsNum(vo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		vo.setPageQuery(pageQuery);// 设置分页参数

		// 分页查询列表
		List<Topic> list = topicService.queryTopics(vo);

		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}

}
