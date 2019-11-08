package com.cloud.cloudzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Author: Liuchong
 * Description: 所有Zuul过滤器必须扩展ZuulFilter类，并覆盖4个方法
 * date: 2019/11/7 17:38
 */
@Component
public class TrackingFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger loger = LoggerFactory.getLogger(TrackingFilter.class);

    /**
     * 过滤器中常用的方法都封装在FilterUtil中
     */
    @Autowired
    FilterUtils filterUtils;

    /**
     * filterType告诉Zuul，当前过滤器是前置过滤器还是路由过滤器还是后置过滤器
      */
    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    /**
     * filterOrder方法返回一个整数值，指示不同类型的过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /**
     * shouldFilter方法返回布尔值来只是改过滤器是否要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent() {
        if (filterUtils.getCorrelationId() != null) {
            return true;
        }

        return false;
    }

    /**
     * 该辅助方法实际上检查tmx-correlation-id是否存在，并且可以生成关联ID的GUID值
     * @return
     */
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * run方法使每次服务通过过滤器时实行的代码，run方法检查tmx-correlation-id是否存在，
     * 如果不存在，则生成一个关联值，并且设置HTTP首部tmx-correlation-id
     * @return
     */
    @Override
    public Object run() {
        if (isCorrelationIdPresent()) {
            loger.info("tmx-correlation-id 在Zuul过滤器中被发现: {}.", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            loger.info("tms-correlationId-id 在Zuul过滤器中被创建: {}.", filterUtils.getCorrelationId());
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        loger.info("当前请求uri为： {}.", ctx.getRequest().getRequestURI());

        return null;
    }


}
