package com.dino.backend.features.ordering.application.service;

import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.application.utils.Deleted;

import java.time.Duration;

public interface ICheckoutService {
    // QUERY //

    EstimateCheckoutRes estimateCheckout(EstimateCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

    InitCheckoutRes startCheckout(InitCheckoutReq request, CurrentUser currentUser);

    ConfirmCheckoutRes confirmCheckout(ConfirmCheckoutReq request, CurrentUser currentUser);

    Deleted cancelCheckout(Duration ttl);
}