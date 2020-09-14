package com.drbsimon.apigateway.controller.filter;

import com.drbsimon.apigateway.security.CustomUserDetailsService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PreFilter extends ZuulFilter {
    private final CustomUserDetailsService customUserDetailsService;

    public PreFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String username = customUserDetailsService.findLoggedInUsername();
        Long userId = customUserDetailsService.findVisitorIdByUsername(username);
        if (userId != null) ctx.addZuulRequestHeader("userid", userId.toString());
        return null;
    }
}
