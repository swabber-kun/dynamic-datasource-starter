package com.dynamic.mapper;

import com.dynamic.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jibingkun
 */
@Mapper
public interface ProductDao {

    /**
     * select ...
     */
    Product select(@Param("id") long id);

    /**
     * update ...
     */
    Integer update(Product product);

    /**
     * insert ...
     */
    Integer insert(Product product);

    /**
     * get all ...
     */
    List<Product> getAllProduct();
}
