package com.dynamic.service;

import com.dynamic.common.ServiceException;
import com.dynamic.mapper.ProductDao;
import com.dynamic.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jibingkun
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product select(long productId) {
        return productDao.select(productId);
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public Product update(long productId, Product newProduct) throws ServiceException {
        if (productDao.update(newProduct) <= 0) {
            throw new ServiceException("Update product:" + productId + "failed");
        }
        return newProduct;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(Product newProduct) {
        Integer num = productDao.insert(newProduct);
        if (num <= 0) {
            return false;
        }
        return true;
    }

    public List<Product> getAllProduct() {
        return productDao.getAllProduct();
    }
}
