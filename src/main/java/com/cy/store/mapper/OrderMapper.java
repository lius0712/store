package com.cy.store.mapper;

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;

public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order 订单数据
     * @return
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项的数据
     * @param orderItem
     * @return
     */
    Integer insertOrderItem(OrderItem orderItem);
}
