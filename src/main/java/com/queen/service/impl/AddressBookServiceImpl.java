package com.queen.service.impl;

import com.queen.domain.AddressBook;
import com.queen.dao.AddressBookDao;
import com.queen.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址管理 服务实现类
 * </p>
 *
 * @author wgt
 * @since 2022-11-26
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {

}
