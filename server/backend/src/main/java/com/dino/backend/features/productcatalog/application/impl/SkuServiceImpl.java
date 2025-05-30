package com.dino.backend.features.productcatalog.application.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dino.backend.features.productcatalog.application.ISkuService;
import com.dino.backend.features.productcatalog.domain.Sku;
import com.dino.backend.features.productcatalog.domain.repository.ISkuRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SkuServiceImpl implements ISkuService {

    ISkuRepository skuRepository;

    // DOMAIN //

    @Override
    public Sku getSku(Long skuId) {
        return this.findSku(skuId)
                .orElseThrow(() -> new AppException(ErrorCode.SKU__FIND_FAILED));
    }

    @Override
    public Optional<Sku> findSku(Long skuId) {
        return this.skuRepository.findById(skuId);
    }
}
