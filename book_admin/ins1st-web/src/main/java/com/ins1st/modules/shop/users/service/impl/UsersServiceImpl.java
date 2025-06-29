package com.ins1st.modules.shop.users.service.impl;

import com.ins1st.modules.shop.users.entity.Users;
import com.ins1st.modules.shop.users.mapper.UsersMapper;
import com.ins1st.modules.shop.users.service.IUsersService;
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
@Service("usersService")
@Transactional
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
