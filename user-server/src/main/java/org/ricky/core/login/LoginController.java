package org.ricky.core.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.common.context.UserContext;
import org.ricky.common.security.IpJwtCookieUpdater;
import org.ricky.common.security.jwt.JwtCookieFactory;
import org.ricky.core.login.alter.LoginAlterationService;
import org.ricky.core.login.alter.dto.command.MobileOrEmailLoginCommand;
import org.ricky.core.login.alter.dto.command.VerificationCodeLoginCommand;
import org.ricky.core.login.alter.dto.response.JwtTokenResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/30
 * @className LoginController
 * @desc
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "登录相关接口")
public class LoginController {

    private final LoginAlterationService loginAlterationService;
    private final IpJwtCookieUpdater ipJwtCookieUpdater;
    private final JwtCookieFactory jwtCookieFactory;

    @PostMapping(value = "/login")
    @Operation(summary = "使用手机号或邮箱登录")
    public JwtTokenResponse loginWithMobileOrEmail(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   @RequestBody @Valid MobileOrEmailLoginCommand command) {
        String jwt = loginAlterationService.loginWithMobileOrEmail(command);
        response.addCookie(ipJwtCookieUpdater.updateCookie(jwtCookieFactory.newJwtCookie(jwt), request));
        return JwtTokenResponse.builder().token(jwt).build();
    }

    @Operation(summary = "使用验证码登录")
    @PostMapping(value = "/verification-code-login")
    public JwtTokenResponse loginWithVerificationCode(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      @RequestBody @Valid VerificationCodeLoginCommand command) {
        String jwt = loginAlterationService.loginWithVerificationCode(command);
        response.addCookie(ipJwtCookieUpdater.updateCookie(jwtCookieFactory.newJwtCookie(jwt), request));
        return JwtTokenResponse.builder().token(jwt).build();
    }

    @Operation(summary = "登出")
    @DeleteMapping(value = "/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       @AuthenticationPrincipal UserContext userContext) {
        response.addCookie(ipJwtCookieUpdater.updateCookie(jwtCookieFactory.logoutCookie(), request));
        if (userContext.isLoggedIn()) {
            log.info("User[{}] tried log out.", userContext.getUserId());
        }
    }

    @Operation(summary = "刷新token")
    @PutMapping(value = "/refresh-token")
    public JwtTokenResponse refreshToken(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @AuthenticationPrincipal UserContext userContext) {
        String jwt = loginAlterationService.refreshToken(userContext);
        response.addCookie(ipJwtCookieUpdater.updateCookie(jwtCookieFactory.newJwtCookie(jwt), request));
        return JwtTokenResponse.builder().token(jwt).build();
    }

}
