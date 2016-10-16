package support.base.dao.mapper;

import java.util.List;

import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.vo.ProductVo;

public interface ProductMapper {
	int saveProduct(Product record);

	Integer queryProductsNum(ProductVo vo);
	//查询已经存在的排序值
	List<Integer> querySortValue(String shelvesTime);
	//判断是否存在该值
	int sortValueExist(ProductVo vo);

	List<Product> queryProducts(ProductVo vo);

	void updateProduct(Product record);

	List<Category> queryCategorys(Long id);

	void updateProducts(List<Product> products);

	// 批量修改收藏量
	void changeProductsCollects(List<Product> products);
}