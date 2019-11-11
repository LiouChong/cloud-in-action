package com.cloud.cloudzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/11 14:06
 */
@Component
public class ResponseFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return filterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        logger.info("添加correlation id 到返回值头部中, {}", filterUtils.getCorrelationId());
        // 获取原始HTTP请求中传入的关联id，并将它注入到响应中
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID,filterUtils.getCorrelationId());

        // 记录传出的请求URI, 这样就有了“书档”， 它将显示进入Zuu的用户请求的传入和传出条目
        logger.info("Completing outgoing request for {}." , ctx.getRequest().getRequestURI());

        return null;
    }
}
