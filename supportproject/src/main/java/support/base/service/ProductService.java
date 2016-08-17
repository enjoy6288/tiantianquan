package support.base.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.vo.ProductVo;

public interface ProductService {
	Integer queryProductsNum(ProductVo vo);

	public List<Product> queryProducts(ProductVo vo);

	int saveProduct(ProductVo record,MultipartFile img) throws Exception;

	void updateProduct(Product record);

	List<Category> queryCategorys(Long id);
	
	void updateProducts(List<Product> products);
}
