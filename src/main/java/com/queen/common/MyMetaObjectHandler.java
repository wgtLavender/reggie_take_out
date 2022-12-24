package com.queen.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.queen.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author
 * @date 2022/11/29/20:58
 */

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long id = BaseContext.getId();
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createUser", id);
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getId());
        System.out.println(metaObject.toString());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getId());

    }


}
