package com.qiuzhitech.onlineshopping_06.service;

import com.qiuzhitech.onlineshopping_06.db.dao.OnlineShoppingCommodityDao;
import com.qiuzhitech.onlineshopping_06.db.po.OnlineShoppingCommodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SearchService {

    @Resource
    OnlineShoppingCommodityDao commodityDao;

    public List<OnlineShoppingCommodity> searchCommoditiesWithMysql(String keyWord) {
        return commodityDao.searchCommodityByKeyWord(keyWord);
    }
}
