package com.zj.dubbo.serivce;

import com.zj.dubbo.entity.Stock;

public interface StockService {

    //查询库存
    public Stock getStock(Long skuId);

}
