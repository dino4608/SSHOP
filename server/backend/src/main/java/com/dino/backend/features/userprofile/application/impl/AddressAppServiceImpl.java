package com.dino.backend.features.userprofile.application.impl;

import org.springframework.stereotype.Service;

import com.dino.backend.features.userprofile.application.IAddressAppService;
import com.dino.backend.features.userprofile.domain.Address;
import com.dino.backend.features.userprofile.domain.repository.IAddressDomainRepository;
import com.dino.backend.shared.api.model.CurrentUser;
import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressAppServiceImpl implements IAddressAppService {

    IAddressDomainRepository addressDomainRepository;

    // QUERY //

    // getDefault //
    @Override
    public Address getDefault(CurrentUser currentUser) {
        Boolean isDefault = true;
        return this.addressDomainRepository.findByUserIdAndIsDefault(currentUser.id(), isDefault)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS__NOT_FOUND));
    }
}
