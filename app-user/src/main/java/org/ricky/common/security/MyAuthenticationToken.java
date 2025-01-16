package org.ricky.common.security;

import lombok.Getter;
import org.ricky.common.context.UserContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/9/26
 * @className MyAuthenticationToken
 * @desc token
 */
@Getter
public class MyAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 用户
     */
    private final UserContext userContext;

    /**
     * 过期时间
     */
    private final long expiration;

    public MyAuthenticationToken(UserContext userContext, long expiration) {
        super(List.of(new SimpleGrantedAuthority("ROLE_" + userContext.getRole().name())));
        requireNonNull(userContext, "UserPO must not be null.");
        this.userContext = userContext;
        this.expiration = expiration;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userContext;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
