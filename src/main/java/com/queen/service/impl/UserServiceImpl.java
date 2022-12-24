package com.queen.service.impl;

import com.queen.domain.User;
import com.queen.dao.UserDao;
import com.queen.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
