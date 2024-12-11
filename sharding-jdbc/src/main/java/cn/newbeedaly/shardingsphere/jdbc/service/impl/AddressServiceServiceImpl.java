package cn.newbeedaly.shardingsphere.jdbc.service.impl;

import cn.newbeedaly.shardingsphere.jdbc.dao.mapper.AddressMapper;
import cn.newbeedaly.shardingsphere.jdbc.dao.po.Address;
import cn.newbeedaly.shardingsphere.jdbc.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
}
