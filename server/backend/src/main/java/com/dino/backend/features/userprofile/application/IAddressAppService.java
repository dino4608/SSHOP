package com.dino.backend.features.userprofile.application;

import com.dino.backend.features.userprofile.domain.Address;
import com.dino.backend.infrastructure.web.model.CurrentUser;

public interface IAddressAppService {
    // QUERY //

    Address getDefault(CurrentUser currentUser);
}
