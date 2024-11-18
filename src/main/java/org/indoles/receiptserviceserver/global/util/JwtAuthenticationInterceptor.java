package org.indoles.receiptserviceserver.global.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.BuyerOnly;
import org.indoles.receiptserviceserver.core.receipt.controller.interfaces.SellerOnly;
import org.indoles.receiptserviceserver.core.receipt.domain.enums.Role;
import org.indoles.receiptserviceserver.core.receipt.dto.request.SignInfoRequest;
import org.indoles.receiptserviceserver.global.exception.AuthorizationException;
import org.indoles.receiptserviceserver.global.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler || CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String token = extractToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            SignInfoRequest signInfoRequest = jwtTokenProvider.getSignInInfoFromToken(token);
            authenticationContext.setPrincipal(signInfoRequest); // 사용자 정보 설정

            authorize(handler, signInfoRequest);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing JWT token");
            return false;
        }

        return true;
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void authorize(Object handler, SignInfoRequest signInfoRequest) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (handlerMethod.hasMethodAnnotation(BuyerOnly.class) && !signInfoRequest.isType(Role.BUYER)) {
            throw new AuthorizationException("구매자만 요청할 수 있는 경로(API) 입니다.", ErrorCode.AU02);
        }

        if (handlerMethod.hasMethodAnnotation(SellerOnly.class) && !signInfoRequest.isType(Role.SELLER)) {
            throw new AuthorizationException("판매자만 요청할 수 있는 경로(API) 입니다.", ErrorCode.AU01);
        }
    }
}
