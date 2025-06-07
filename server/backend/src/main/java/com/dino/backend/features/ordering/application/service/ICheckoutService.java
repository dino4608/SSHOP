package com.dino.backend.features.ordering.application.service;

import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.shared.api.model.CurrentUser;

public interface ICheckoutService {
    // QUERY //

    EstimateCheckoutRes estimateCheckout(EstimateCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

    InitCheckoutRes initCheckout(InitCheckoutReq request, CurrentUser currentUser);

    ConfirmCheckoutRes confirmCheckout(ConfirmCheckoutReq request, CurrentUser currentUser);
}