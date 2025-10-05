package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.sky.service.impl
 * 用户：admin
 * 日期：2025-10-04
 * 项目名称：sky-take-out
 */

@Service
@Slf4j
public class OrderServerImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 用户下单
     * @param orderSubmitDTO 提交订单的dto
     * @return 返回vo
     */
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO orderSubmitDTO){

        //1.异常处理(地址为空，购物车为空)
        AddressBook addressBook = addressBookMapper.getById(orderSubmitDTO.getAddressBookId());
        //检查地址簿是否为空
        if(addressBook == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //检查购物车是否为空
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> carts = shoppingCartMapper.list(shoppingCart);
        if(carts == null || carts.isEmpty()){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2.向订单表插入1条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO,orders);
        orders.setOrderTime(LocalDateTime.now());   //下单时间
        orders.setPayStatus(Orders.UN_PAID);    //支付状态
        orders.setStatus(Orders.PENDING_PAYMENT);   //订单状态
        orders.setNumber(String.valueOf(System.currentTimeMillis()));   //使用时间戳作为订单号
        orders.setPhone(addressBook.getPhone());    //用户电话
        orders.setConsignee(addressBook.getConsignee());    //收货人
        orders.setUserId(userId);   //用户id
        orderMapper.insert(orders);

        //3.向订单明细表插入n条信息
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart cart : carts) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetails);

        //4.清空购物车
        shoppingCartMapper.cleanByUserId(userId);

        //5.返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();

        return orderSubmitVO;
    }

}
