package com.queen.service;

import com.queen.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Transactional
public interface UserService extends IService<User> {

}
