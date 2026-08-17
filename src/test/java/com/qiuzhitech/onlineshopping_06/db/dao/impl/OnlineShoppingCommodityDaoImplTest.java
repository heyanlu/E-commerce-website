package com.qiuzhitech.onlineshopping_06.db.dao.impl;

import com.qiuzhitech.onlineshopping_06.db.dao.OnlineShoppingCommodityDao;
import com.qiuzhitech.onlineshopping_06.db.po.OnlineShoppingCommodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OnlineShoppingCommodityDaoImplTest {

    @Resource
    private OnlineShoppingCommodityDao onlineShoppingCommodityDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertCommodity() {
        OnlineShoppingCommodity onlineShoppingCommodity = OnlineShoppingCommodity.builder()
                .commodityName(("TestCommodityName"))
                .commodityDesc("desc")
                .availableStock(111)
                .totalStock(111)
                .price(999)
                .lockStock(0)
                .creatorUserId(124L)
                .build();

        onlineShoppingCommodityDao.insertCommodity(onlineShoppingCommodity);

    }

    @Test
    void listCommodity() {
    }
}