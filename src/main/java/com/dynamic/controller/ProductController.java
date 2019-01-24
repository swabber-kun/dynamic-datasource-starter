package com.dynamic.controller;

import com.dynamic.common.CommonResponse;
import com.dynamic.common.ResponseUtil;
import com.dynamic.common.ServiceException;
import com.dynamic.model.Product;
import com.dynamic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jibingkun
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 模拟动态配置
     */
    public static final boolean useNewConnection = false;

    /**
     * Get product by id
     *
     * curl -X GET http://localhost:8080/product/1
     *
     */
    @GetMapping("/{id}")
    public CommonResponse getProduct(@PathVariable("id") Long productId) {
        return ResponseUtil.generateResponse(productService.select(productId));
    }

    /**
     * Get all product
     */
    @GetMapping
    public CommonResponse getAllProduct() {
        return ResponseUtil.generateResponse(productService.getAllProduct());
    }

    /**
     * Update product by id
     */
    @PutMapping("/{id}")
    public CommonResponse updateProduct(@PathVariable("id") Long productId, @RequestBody Product newProduct) throws ServiceException {
        return ResponseUtil.generateResponse(productService.update(productId, newProduct));
    }

    /**
     * Save product
     */
    @PostMapping
    public CommonResponse addProduct(@RequestBody Product newProduct) {
        return ResponseUtil.generateResponse(productService.add(newProduct));
    }
}
