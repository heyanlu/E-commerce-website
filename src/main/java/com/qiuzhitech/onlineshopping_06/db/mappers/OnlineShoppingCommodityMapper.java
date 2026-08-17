package com.qiuzhitech.onlineshopping_06.db.mappers;

import com.qiuzhitech.onlineshopping_06.db.po.OnlineShoppingCommodity;

import java.util.List;

public interface OnlineShoppingCommodityMapper {
    int deleteByPrimaryKey(Long commodityId);

    int insert(OnlineShoppingCommodity record);

    int insertSelective(OnlineShoppingCommodity record);

    OnlineShoppingCommodity selectByPrimaryKey(Long commodityId);

    List<OnlineShoppingCommodity> listcommodities();

    int updateByPrimaryKeySelective(OnlineShoppingCommodity record);

    int updateByPrimaryKey(OnlineShoppingCommodity record);

    List<OnlineShoppingCommodity> listcommoditiesByUserId(Long userId);

    int deductStockWithCommodityId(Long commodityId);

    int revertStockWithCommodityId(Long commodityId);

    List<OnlineShoppingCommodity> searchCommodityByKeyWord(String keyWord);
}