package com.dmon.sshop._domain.shopping.service;

import com.dmon.sshop._domain.shopping.model.entity.Order;
import com.dmon.sshop._domain.shopping.model.request.OrderCheckoutReq;
import com.dmon.sshop._domain.shopping.model.request.OrderPlaceReq;
import com.dmon.sshop._domain.shopping.model.response.OrderCheckoutRes;

/**
 * key features:
 * - checkout an order
 * - place an order
 */
public interface IOrderDomainService {

    //CREATE//
    OrderCheckoutRes checkout(OrderCheckoutReq orderCheckoutReq, String userId);

    //UPDATE//
    Void place(OrderPlaceReq orderPlaceReq);

    //HELPER//
    Order findOrError(String orderId);
}
