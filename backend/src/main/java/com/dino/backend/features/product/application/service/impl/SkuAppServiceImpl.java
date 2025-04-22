package com.dino.backend.features.product.application.service.impl;

import com.dino.backend.features.inventory.domain.entity.Sku;
import com.dino.backend.features.product.application.service.ISkuAppService;
import com.dino.backend.infrastructure.persistent.product.ISkuInfraRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@Slf4j
public class SkuAppServiceImpl implements ISkuAppService {
    ISkuInfraRepository skuDomainRepo;

    // SERVICES//
    @Override
    public void checkSkuCodeOrError(String skuCode) {
        this.skuDomainRepo.findBySkuCode(skuCode)
                .ifPresent(ignore -> {
                    throw new AppException(ErrorCode.SKU__CODE_UNIQUE);
                });

    }

    @Override
    public Sku findOrError(String skuId) {
        Sku skuPresent = this.skuDomainRepo.findById(skuId)
                .orElseThrow(() -> new AppException(ErrorCode.SKU__NOT_FOUND));

        return skuPresent;
    }
}
