package com.qiuzhitech.onlineshopping_06.component;

import com.qiuzhitech.onlineshopping_06.db.dao.OnlineShoppingCommodityDao;
import com.qiuzhitech.onlineshopping_06.db.po.OnlineShoppingCommodity;
//import com.qiuzhitech.onlineshopping_06.service.EsService;
import com.qiuzhitech.onlineshopping_06.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

// 一开机就执行，所以Component
@Component
@Slf4j
public class RedisPreHeatRunner implements ApplicationRunner {

    // Read
    @Resource
    OnlineShoppingCommodityDao onlineShoppingCommodityDao;

    // Write
    @Resource
    RedisService redisService;


//    @Resource
//    EsService esService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // read from Mysql
        // write to Redis
        List<OnlineShoppingCommodity> onlineShoppingCommodities = onlineShoppingCommodityDao.listCommodities();
        // for loop to write every record
        for (OnlineShoppingCommodity onlineShoppingCommodity : onlineShoppingCommodities) {
            String redisKey = "commodity:" + onlineShoppingCommodity.getCommodityDesc();
            redisService.setValue(redisKey, onlineShoppingCommodity.getAvailableStock().toString());
//            esService.addCommodity(onlineShoppingCommodity);
            log.info("preHeat Starting: " + onlineShoppingCommodity.getCommodityId());
        }
    }


}
