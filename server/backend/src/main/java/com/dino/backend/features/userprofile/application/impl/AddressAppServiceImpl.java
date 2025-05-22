package com.dino.backend.features.userprofile.application.impl;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.userprofile.application.IAddressAppService;
import com.dino.backend.features.userprofile.domain.Address;
import com.dino.backend.features.userprofile.domain.repository.IAddressDomainRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressAppServiceImpl implements IAddressAppService {

    IAddressDomainRepository addressDomainRepository;

    // QUERY //

    // getById //
    @Override
    public Address getDefault(CurrentUser currentUser) {
        Boolean isDefault = true;
        return this.addressDomainRepository.findByUserAndIsDefault(
                User.builder().id(currentUser.id()).build(),
                isDefault
        ).orElseThrow(() -> new AppException(ErrorCode.ADDRESS__NOT_FOUND));
    }
}
