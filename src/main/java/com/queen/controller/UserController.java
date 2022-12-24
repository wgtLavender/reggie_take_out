package com.queen.controller;


import com.queen.common.R;
import com.queen.domain.User;
import com.queen.service.UserService;
import com.queen.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @description 获取验证码
     *
     * @author
     * @date 2022/12/17 12:03
     * @param user
     * @param session
     * @return com.queen.common.R<java.lang.String>
     */

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 发送验证码请求获取验证码
        // 登录 校验验证码 通过 入库 不通过 返回失败

        Integer integer = ValidateCodeUtils.generateValidateCode(4);
        session.setAttribute("code", integer);
        session.setAttribute("phone", user.getPhone());
        log.info("验证码为：{}", integer);
        return R.success(String.valueOf(integer));
    }


    @PostMapping("/login")
    public R login(@RequestBody Map map, HttpSession session) {
        log.info("map:{}",map);
        String code1 = session.getAttribute("code").toString();
        String code = map.get("code").toString();
        if (!code.equals(code1)) {
            return R.error("验证失败，重新登录");
        }
        User user = new User();
        user.setPhone(map.get("phone").toString());
        boolean save = userService.save(user);

        return save?R.success("登录成功"):R.error("登录失败");
    }

}

