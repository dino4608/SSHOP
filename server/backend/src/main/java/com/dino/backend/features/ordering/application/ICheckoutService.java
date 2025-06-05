package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.EstimateCheckoutReq;
import com.dino.backend.features.ordering.application.model.CheckoutSnapshotRes;
import com.dino.backend.features.ordering.application.model.EstimateCheckoutRes;
import com.dino.backend.shared.api.model.CurrentUser;

public interface ICheckoutService {
    // QUERY //

    EstimateCheckoutRes estimateCheckout(EstimateCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

//    Object draftCheckout(Object request, CurrentUser currentUser);
//
//    Object confirmCheckout(Object request, CurrentUser currentUser);
}