package com.cloud.cloudzuul.filter;

import com.cloud.cloudzuul.config.ZuulConfig;
import com.cloud.cloudzuul.model.AbTestingRoute;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Liuchong
 * Description:
 * date: 2019/11/11 16:32
 */
//@Component
public class SpecialRoutesFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(SpecialRoutesFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZuulConfig zuulConfig;

    @Override
    public String filterType() {
        return filterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private ProxyRequestHelper helper = new ProxyRequestHelper();

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        // 执行对SpecialRoutes服务的调用，已确定该服务ID是否有路有记录
        AbTestingRoute abTestingRoute = getAbRoutingInfo(filterUtils.getServiceId());

        // useSpecialRoute()方法将会接收路径的权重，生成一个随机数，并确定是否将请求转发到替代服务
        if (abTestingRoute != null && useSpecialRoute(abTestingRoute)) {
            // 如果有路由记录，则将完整的URL（包含路径）构建到由specialroutes服务指定给的服务位置
            String route = buildRouteString(ctx.getRequest().getRequestURI(), abTestingRoute.getEndpoint(), ctx.get("serviceId").toString());
            // forwardToSpecialRoute()方法完成转发到其他服务的工作
            forwardToSpecialRoute(route);
        }

        return null;
    }

    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        List<Header> list = new ArrayList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }
        return list.toArray(new BasicHeader[0]);
    }

    private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName) {
        // 获的
        int index = oldEndpoint.indexOf(serviceName);
        String strippedRoute = "";
        if (index == -1) {
            // 这里需要对serviceName进行判断然后选择各自service的Zuul别名，这里以licensingService为例
            String licensingServiceZuul = zuulConfig.getLicensingServiceZuul().split("/")[1];
            index = oldEndpoint.indexOf(licensingServiceZuul);
            if (index != -1) {
                strippedRoute = oldEndpoint.substring(index + licensingServiceZuul.length());
            } else {
                return "";
            }
        } else {
            strippedRoute = oldEndpoint.substring(index + serviceName.length());
        }
        System.out.println("Target route: " + String.format("%s/%s", newEndpoint, strippedRoute));
        return String.format("%s%s", newEndpoint, strippedRoute);
    }

    private String getVerb(HttpServletRequest request) {
        String sMethod = request.getMethod();
        return sMethod.toUpperCase();
    }

    private HttpHost getHttpHost(URL host) {
        HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(),
                host.getProtocol());
        return httpHost;
    }

    private AbTestingRoute getAbRoutingInfo(String serviceName) {
        ResponseEntity<AbTestingRoute> restExchange = null;

        try {
            restExchange = restTemplate.exchange(
                    // ribbon使用服务名称来调用服务
                    "http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET,
                    null,
                    AbTestingRoute.class,
                    serviceName);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("无法找到url错误，404");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restExchange.getBody();
    }

    private boolean useSpecialRoute(AbTestingRoute testingRoute) {
        /*Random random = new Random();
        if ("N".equals(testingRoute.getActive())) {
            return false;
        }

        int value = random.nextInt((10 -1) + 1) + 1;

        if (testingRoute.getWeight() < value) {
        return true;
        }

        return false;*/
        return true;
    }

    private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Header header : headers) {
            String name = header.getName();
            if (!map.containsKey(name)) {
                map.put(name, new ArrayList<String>());
            }
            map.get(name).add(header.getValue());
        }
        return map;
    }

    private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        } catch (IOException ex) {
            // no requestBody is ok.
        }
        return requestEntity;
    }

    private void forwardToSpecialRoute(String route) {
        if (route.isEmpty()) {
            return;
        }
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
        MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
        String verb = getVerb(request);
        InputStream requestEntity = getRequestBody(request);
        if (request.getContentLength() < 0) {
            context.setChunkedRequestBody();
        }

        this.helper.addIgnoredHeaders();
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();
            response = forward(httpClient, verb, route, request, headers,
                    params, requestEntity);
            setResponse(response, context);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {

            }
        }
    }

    private void setResponse(HttpResponse httpResponse, RequestContext context) throws IOException {
        context.setResponseBody(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
    }


    private HttpResponse forward(HttpClient httpclient, String verb, String uri,
                                 HttpServletRequest request, MultiValueMap<String, String> headers,
                                 MultiValueMap<String, String> params, InputStream requestEntity)
            throws Exception {
        Map<String, Object> info = this.helper.debug(verb, uri, headers, params, requestEntity);
        URL host = new URL(uri);
        HttpHost httpHost = getHttpHost(host);

        HttpRequest httpRequest;
        int contentLength = request.getContentLength();
        InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
                request.getContentType() != null
                        ? ContentType.create(request.getContentType()) : null);
        switch (verb.toUpperCase()) {
            case "POST":
                HttpPost httpPost = new HttpPost(uri);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(uri);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(uri);
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(verb, uri);

        }
        try {
            httpRequest.setHeaders(convertHeaders(headers));
            HttpResponse zuulResponse = forwardRequest(httpclient, httpHost, httpRequest);

            return zuulResponse;
        } finally {

        }
    }

    private HttpResponse forwardRequest(HttpClient httpclient, HttpHost httpHost,
                                        HttpRequest httpRequest) throws IOException {
        return httpclient.execute(httpHost, httpRequest);
    }
}
