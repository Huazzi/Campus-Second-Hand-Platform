package com.ins1st.modules.shop.feedback.service.impl;

import com.ins1st.modules.shop.feedback.entity.Feedback;
import com.ins1st.modules.shop.feedback.mapper.FeedbackMapper;
import com.ins1st.modules.shop.feedback.service.IFeedbackService;
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
@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
