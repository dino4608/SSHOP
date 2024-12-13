package com.dmon.sshop._application.service.shopping;

import com.dmon.sshop._domain.shopping.model.request.OrderCheckoutReq;
import com.dmon.sshop._domain.shopping.model.request.OrderPlaceReq;
import com.dmon.sshop._domain.shopping.model.response.OrderCheckoutRes;

public interface IOrderAppService {
    //CREATE//
    OrderCheckoutRes checkout(OrderCheckoutReq orderCheckoutReq);

    //UPDATE//
    Void place(OrderPlaceReq orderPlaceReq);
}
