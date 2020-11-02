package com.drbsimon.apigateway.controller.filter;

import com.drbsimon.apigateway.model.entity.Visitor;
import com.drbsimon.apigateway.security.service.ParseVisitorSecurityService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreFilter extends ZuulFilter {
    private final ParseVisitorSecurityService parseVisitorSecurityService;

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
        Visitor visitor = parseVisitorSecurityService.parseVisitorFromToken();
        String foundUserId = (visitor != null) ?  visitor.getId().toString() : null;
        ctx.addZuulRequestHeader("userid", foundUserId);
        return null;
    }
}
