package cn.newbeedaly.easyexcel.order.service.impl;

import cn.newbeedaly.easyexcel.order.dao.mapper.OrderMapper;
import cn.newbeedaly.easyexcel.order.dao.po.Order;
import cn.newbeedaly.easyexcel.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author newbeedaly
 * @since 2021-08-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
