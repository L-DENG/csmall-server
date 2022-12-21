package com.li.csmall.product.webapi.app;

import com.li.csmall.product.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CachePreLoad implements ApplicationRunner {

    @Autowired
    private ICategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("CachePreLoad.run()");
        categoryService.preloadCache();
    }
}
