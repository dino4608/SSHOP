package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.CheckoutPreviewRes;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutReq;
import com.dino.backend.shared.api.model.CurrentUser;

public interface ICheckoutService {
    // QUERY //

    CheckoutPreviewRes previewCheckout(PreviewCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

    Object checkoutOrder(CurrentUser currentUser);

    Object confirmCheckout(CurrentUser currentUser);
}