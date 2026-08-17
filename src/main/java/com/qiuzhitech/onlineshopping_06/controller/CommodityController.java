package com.qiuzhitech.onlineshopping_06.controller;

import com.qiuzhitech.onlineshopping_06.db.dao.OnlineShoppingCommodityDao;
import com.qiuzhitech.onlineshopping_06.db.po.OnlineShoppingCommodity;
import java.util.List;

import com.qiuzhitech.onlineshopping_06.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class CommodityController {

    @Resource
    OnlineShoppingCommodityDao commodityDao;

    @Resource
    SearchService searchService;

    //presents a page
    @RequestMapping("/addItem")
    public String addCommodity() {
        return "add_commodity";
    }

    //Post method
    @PostMapping("commodities")
    public String addCommodity(@RequestParam("commodityId") long commodityId,
                               @RequestParam("commodityName") String commodityName,
                               @RequestParam("commodityDesc") String commodityDesc,
                               @RequestParam("price") int price,
                               @RequestParam("availableStock") int availableStock,
                               @RequestParam("creatorUserId") long creatorUserId,
                               Map<String, Object> resultMap){
        //resultMap: holds the data that you want to display on the frontend

        OnlineShoppingCommodity commodity = OnlineShoppingCommodity.builder()
                .commodityId(commodityId)
                .commodityName(commodityName)
                .commodityDesc(commodityDesc)
                .price(price)
                .availableStock(availableStock)
                .creatorUserId(creatorUserId)
                .totalStock(availableStock)
                .lockStock(0)
                .build();

        //update database
        commodityDao.insertCommodity(commodity);

        // update view
        // Item should be the same as frontend. (frontend: Item.commodityId)
        resultMap.put("Item", commodity);

        // return a view named add_commodity
        return "add_commodity";

    }


    @GetMapping("searchAction")
    public String searchAction(@RequestParam("keyWord") String keyword,
                               Map<String, Object> resultMap){
        //resultMap: holds the data that you want to display on the frontend

        List<OnlineShoppingCommodity> onlineShoppingCommodities = searchService.searchCommoditiesWithMysql(keyword);
        //绑定前段

        resultMap.put("itemList", onlineShoppingCommodities);

        return "search_items";

    }


    @GetMapping({"/commodities","/"})
    public String listCommodities(Map<String, Object> resultMap) {
        List<OnlineShoppingCommodity> onlineShoppingCommodities = commodityDao.listCommodities();
        resultMap.put("itemList", onlineShoppingCommodities);
        return "list_items";
    }


    @GetMapping("/commodities/{sellerId}")
    public String listCommoditiesBySellerId(@PathVariable("sellerId") Long sellerId,
                                            Map<String, Object> resultMap) {
        List<OnlineShoppingCommodity> onlineShoppingCommodities = commodityDao.listCommoditiesByUserId(sellerId);
        resultMap.put("itemList", onlineShoppingCommodities);
        return "list_items";
    }

    @GetMapping({"/item/{commodityId}"})
    public String getCommodity(@PathVariable("commodityId") Long commodityId,
                               Map<String, Object> resultMap) {
        OnlineShoppingCommodity commodity = commodityDao.getCommodityDetail(commodityId);
        resultMap.put("commodity", commodity);
        return "item_detail";
    }


}
