package com.dino.backend.infrastructure.web.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dino.backend.infrastructure.security.utils.SecurityUtils;
import com.dino.backend.shared.api.annotation.AuthUser;
import com.dino.backend.shared.api.model.CurrentUser;

@Component
public class AuthUserAnnotationImpl implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(AuthUser.class)
                && parameter.getParameterType().equals(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) throws Exception {

        return SecurityUtils.getCurrentUser();
    }
}
