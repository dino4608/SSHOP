package com.dino.backend.features.product.application.service.impl;

import com.dino.backend.features.product.application.mapper.ICategoryMapper;
import com.dino.backend.features.product.application.model.projection.CategoryProj;
import com.dino.backend.features.product.application.model.request.CategoryReq;
import com.dino.backend.features.product.application.service.ICategoryAppService;
import com.dino.backend.features.product.domain.entity.Category;
import com.dino.backend.infrastructure.persistent.product.ICategoryInfraRepository;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.model.PageRes;
import com.dino.backend.shared.utils.AppUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryAppServiceImpl implements ICategoryAppService {
    ICategoryInfraRepository cateInfraRepo;

    ICategoryMapper cateMapper;

    //CREATE//
    @Override
    public Category create(CategoryReq.Create payload) {
        if (this.cateInfraRepo.findFirstByName(payload.getName()).isPresent())
            throw new AppException(ErrorCode.CATEGORY__NAME_UNIQUE);

        Category cateResult = this.cateInfraRepo.save(this.cateMapper.toEntity(payload));
        return cateResult;
    }

    //UPDATE//
    @Override
    public Category update(CategoryReq.Update cateDto, String cateId) {
        if (this.cateInfraRepo.findFirstByNameAndIdNot(cateDto.getName(), cateId).isPresent())
            throw new AppException(ErrorCode.CATEGORY__NAME_UNIQUE);

        Category cateRequested = this.cateMapper.toEntity(cateDto);

        Category cateUpdated = this.findOrError(cateId);

        AppUtils.updateNonNull(cateUpdated, cateRequested);

        Category cateResult = this.cateInfraRepo.save(cateUpdated);
        return cateResult;
    }

    //LIST//
    @Override
    public PageRes<CategoryProj> findAll(Pageable pageable) {
        Page<CategoryProj> catePage = this.cateInfraRepo.findAllProjectedBy(pageable);
        return AppUtils.toPageRes(catePage);
    }

    @Override
    public List<CategoryProj> findTree() {
        Sort sort = Sort.by(Sort.Direction.ASC, "position");
        List<CategoryProj> cateList = this.cateInfraRepo.findAllProjectedBy(sort);
        return cateList;
    }

    //FIND//
    @Override
    public Category find(String cateId) {
        Category catePresent = this.findOrError(cateId);
        return catePresent;
    }

    //DELETE//
    @Override
    public Void delete(String cateId) {
        this.cateInfraRepo.deleteById(cateId);
        return null;
    }

    //SERVICE//
    @Override
    public Category findOrError(String cateId) {
        Category cate = this.cateInfraRepo.findById(cateId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY__NOT_FOUND));
        return cate;
    }

}
