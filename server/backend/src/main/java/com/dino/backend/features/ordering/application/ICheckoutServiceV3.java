package com.dino.backend.features.ordering.application;

import com.dino.backend.features.ordering.application.model.PreviewCheckoutResV3;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutReq;
import com.dino.backend.shared.api.model.CurrentUser;

public interface ICheckoutServiceV3 {
    // QUERY //

    PreviewCheckoutResV3 previewCheckout(PreviewCheckoutReq request, CurrentUser currentUser);

    // COMMAND //

    Object checkoutOrder(CurrentUser currentUser);

    Object confirmCheckout(CurrentUser currentUser);
}