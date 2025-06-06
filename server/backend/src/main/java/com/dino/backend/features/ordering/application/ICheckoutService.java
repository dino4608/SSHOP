package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.*;
import com.dino.backend.shared.api.model.CurrentUser;
import jakarta.validation.Valid;

public interface ICheckoutService {
    // QUERY //

    EstimateCheckoutRes estimateCheckout(EstimateCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

    InitCheckoutRes initCheckout(InitCheckoutReq request, CurrentUser currentUser);

    ConfirmCheckoutRes confirmCheckout(ConfirmCheckoutReq request, CurrentUser currentUser);
}