package support.base.dao.mapper;


import java.util.List;

import support.base.pojo.po.Category;
import support.base.pojo.po.Product;
import support.base.pojo.vo.ProductVo;

public interface ProductMapper {
    int saveProduct(Product record);
    
    Integer queryProductsNum(ProductVo vo);
    List<Product> queryProducts(ProductVo vo);
    
    void updateProduct(Product record);
    
    List<Category> queryCategorys(Long id);
    
    void updateProducts(List<Product> products);
}