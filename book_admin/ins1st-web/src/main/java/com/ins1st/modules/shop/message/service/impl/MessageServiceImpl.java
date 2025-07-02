package com.ins1st.modules.shop.message.service.impl;

import com.ins1st.modules.shop.message.entity.Message;
import com.ins1st.modules.shop.message.mapper.MessageMapper;
import com.ins1st.modules.shop.message.service.IMessageService;
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
@Service("messageService")
@Transactional
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
