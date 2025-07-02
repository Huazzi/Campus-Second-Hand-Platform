package com.ins1st.modules.shop.goods.service.impl;

import com.ins1st.modules.shop.goods.entity.Goods;
import com.ins1st.modules.shop.goods.mapper.GoodsMapper;
import com.ins1st.modules.shop.goods.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ins1st
 * @since 2020-02-21
 */
@Service("goodsService")
@Transactional
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
