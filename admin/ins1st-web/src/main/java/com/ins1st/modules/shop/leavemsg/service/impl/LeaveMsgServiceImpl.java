package com.ins1st.modules.shop.leavemsg.service.impl;

import com.ins1st.modules.shop.leavemsg.entity.LeaveMsg;
import com.ins1st.modules.shop.leavemsg.mapper.LeaveMsgMapper;
import com.ins1st.modules.shop.leavemsg.service.ILeaveMsgService;
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
@Service("leaveMsgService")
@Transactional
public class LeaveMsgServiceImpl extends ServiceImpl<LeaveMsgMapper, LeaveMsg> implements ILeaveMsgService {

}
