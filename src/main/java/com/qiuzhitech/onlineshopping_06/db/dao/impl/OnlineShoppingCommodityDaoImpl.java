package com.qiuzhitech.onlineshopping_06.db.dao.impl;

import com.qiuzhitech.onlineshopping_06.db.dao.OnlineShoppingCommodityDao;
import com.qiuzhitech.onlineshopping_06.db.mappers.OnlineShoppingCommodityMapper;
import com.qiuzhitech.onlineshopping_06.db.po.OnlineShoppingCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OnlineShoppingCommodityDaoImpl implements OnlineShoppingCommodityDao {

    @Resource
    OnlineShoppingCommodityMapper onlineShoppingCommodityMapper;

    @Override
    public int insertCommodity(OnlineShoppingCommodity onlineShoppingCommodity) {
        return onlineShoppingCommodityMapper.insert(onlineShoppingCommodity);
    }

    @Override
    public List<OnlineShoppingCommodity> listCommodities() {
        return onlineShoppingCommodityMapper.listcommodities();
    }

    @Override
    public List<OnlineShoppingCommodity> listCommoditiesByUserId(Long sellerId) {
        return onlineShoppingCommodityMapper.listcommoditiesByUserId(sellerId);
    }

    @Override
    public OnlineShoppingCommodity getCommodityDetail(Long commodityId) {
        return onlineShoppingCommodityMapper.selectByPrimaryKey(commodityId);
    }

    @Override
    public int updateCommodity(OnlineShoppingCommodity commodityDetail) {
        return onlineShoppingCommodityMapper.updateByPrimaryKeySelective(commodityDetail);
    }

    @Override
    public int deductStockWithCommodityId(Long commodityId) {
        return onlineShoppingCommodityMapper.deductStockWithCommodityId(commodityId);
    }

    @Override
    public int revertStockWithCommodityId(Long commodityId) {
        return onlineShoppingCommodityMapper.revertStockWithCommodityId(commodityId);
    }

    @Override
    public List<OnlineShoppingCommodity> searchCommodityByKeyWord(String keyWord) {
        String keywordLike = "%" + keyWord + "%";
        return onlineShoppingCommodityMapper.searchCommodityByKeyWord(keywordLike);
    }
}
