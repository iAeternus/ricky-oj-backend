package org.ricky.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.io.IOException;
import java.util.UUID;

import static org.ricky.common.context.UserContext.ANONYMOUS_USER;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/10/14
 * @className JwtAnonymousAuthenticationFilter
 * @desc 处理匿名认证
 */
public class JwtAnonymousAuthenticationFilter extends AnonymousAuthenticationFilter {

    private static final String KEY = UUID.randomUUID().toString();

    public JwtAnonymousAuthenticationFilter() {
        super(KEY);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(KEY, ANONYMOUS_USER, createAuthorityList("ROLE_ANONYMOUS"));
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);
        }
        chain.doFilter(req, res);
    }
}
