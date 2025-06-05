package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.CheckoutPreviewResV2;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutReq;
import com.dino.backend.shared.api.model.CurrentUser;

public interface ICheckoutServiceV2 {
    // QUERY //

    CheckoutPreviewResV2 previewCheckout(PreviewCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

    Object checkoutOrder(CurrentUser currentUser);

    Object confirmCheckout(CurrentUser currentUser);
}