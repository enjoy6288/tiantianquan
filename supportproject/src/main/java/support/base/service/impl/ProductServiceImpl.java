package support.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import support.base.dao.mapper.ProductMapper;
import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.vo.ProductVo;
import support.base.process.context.Config;
import support.base.process.result.ResultUtil;
import support.base.service.ProductService;
import support.base.util.CommonUse;
import support.base.util.CommonUtil;
import support.base.util.Constant;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	public Integer queryProductsNum(ProductVo vo) {
		// TODO Auto-generated method stub
		return productMapper.queryProductsNum(vo);
	}

	@Override
	public List<Product> queryProducts(ProductVo vo) {
		// TODO Auto-generated method stub
		return productMapper.queryProducts(vo);
	}

	@Override
	public int saveProduct(ProductVo vo, MultipartFile img) throws Exception {
		Product product = new Product();
		String title = vo.getTitle();
		String productDesc = vo.getProductDesc();
		String priceNow = vo.getPriceNow();
		String priceOld = vo.getPriceOld();
		String shelvesTime = vo.getShelvesTime();
		String sortValue = vo.getSortValue();
		boolean flag = StringUtils.isEmpty(priceOld) || StringUtils.isEmpty(title) || StringUtils.isEmpty(productDesc)
				|| img == null || StringUtils.isEmpty(priceNow) || StringUtils.isEmpty(shelvesTime)
				|| StringUtils.isEmpty(sortValue);
		if (flag) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 100, null));
		}
		// 说明是专题商品
		if (!StringUtils.isEmpty(vo.getTopicId())) {
			product.setType((byte) 1);
		}
		product.setImg(CommonUtil.upload(img, Constant.PRODUCT));
		CommonUtil.VoToPo(vo, product);
		product.setLinkUrl(vo.getGoingTo()+vo.getLinkUrl());
		product.setId(CommonUtil.generateId());
		return productMapper.saveProduct(product);
	}

	@Override
	public void updateProduct(Product record) {
		productMapper.updateProduct(record);
	}

	@Override
	public List<Category> queryCategorys(Long id) {
		List<Category> categorys = null;
		if (id == null) {
			categorys = (List<Category>) (CommonUse.getCache("categorys") == null ? null : CommonUse
					.getCache("categorys"));
			if (categorys == null) {
				categorys = productMapper.queryCategorys(id);
				CommonUse.addCache("categorys", categorys);
			}
		}
		return categorys;
	}

	@Override
	public void updateProducts(List<Product> products) {
		productMapper.updateProducts(products);
	}

}
