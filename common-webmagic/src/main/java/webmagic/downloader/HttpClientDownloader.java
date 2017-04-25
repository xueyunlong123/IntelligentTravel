package webmagic.downloader;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webmagic.Page;
import webmagic.Request;
import webmagic.Site;
import webmagic.Task;
import webmagic.proxy.Proxy;
import webmagic.selector.PlainText;
import webmagic.utils.HttpConstant;
import webmagic.utils.UrlUtils;
import webmagic.utils.WMCollections;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * The http downloader based on HttpClient.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class HttpClientDownloader extends AbstractDownloader {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();

    private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

    private CloseableHttpClient getHttpClient(Site site, Proxy proxy) {
        if (site == null) {
            return httpClientGenerator.getClient(null, null);
        }
        String domain = site.getDomain();
        CloseableHttpClient httpClient = httpClients.get(domain);
        if (httpClient == null) {
            synchronized (this) {
                httpClient = httpClients.get(domain);
                if (httpClient == null) {
                    httpClient = httpClientGenerator.getClient(site, null);
                    httpClients.put(domain, httpClient);
                }
            }
        }
        return httpClient;
    }

    @Override
    public Page download(Request request, Task task) {
        Site site = null;
        if (task != null) {
            site = task.getSite();
        }
        Set<Integer> acceptStatCode;
        String charset = null;
        Map<String, String> headers = null;
        if (site != null) {
            acceptStatCode = site.getAcceptStatCode();
            charset = site.getCharset();
            headers = site.getHeaders();
        } else {
            acceptStatCode = WMCollections.newHashSet(200);
        }
        logger.info("downloading page {}", request.getUrl());
        CloseableHttpResponse httpResponse = null;
        int statusCode=0;
        try {
            HttpHost proxyHost = null;
            Proxy proxy = null; //TODO
            if (site.getHttpProxyPool() != null && site.getHttpProxyPool().isEnable()) {
                proxy = site.getHttpProxyFromPool();
                proxyHost = proxy.getHttpHost();
            } else if(site.getHttpProxy()!= null){
                proxyHost = site.getHttpProxy();
            }
            
            HttpUriRequest httpUriRequest = getHttpUriRequest(request, site, headers);//���������˴���
            httpResponse = getHttpClient(site, proxy).execute(httpUriRequest);//getHttpClient�������˴�����֤
            statusCode = httpResponse.getStatusLine().getStatusCode();
            request.addExtra(Request.STATUS_CODE, statusCode);
            if (statusAccept(acceptStatCode, statusCode)) {
                Page page = handleResponse(request, charset, httpResponse, task);
                onSuccess(request);
                return page;
            } else {
                logger.warn("get page {} error, status code {} ",request.getUrl(),statusCode);
                return null;
            }
        } catch (IOException e) {
            logger.warn("download page {} error", request.getUrl(), e);
            if (site.getCycleRetryTimes() > 0) {
                return addToCycleRetry(request, site);
            }
            onError(request);
            return null;
        } finally {
        	request.addExtra(Request.STATUS_CODE, statusCode);
            if (site.getHttpProxyPool()!=null && site.getHttpProxyPool().isEnable()) {
                site.returnHttpProxyToPool((HttpHost) request.getExtra(Request.PROXY), (Integer) request
                        .getExtra(Request.STATUS_CODE));
            }
            try {
                if (httpResponse != null) {
                    //ensure the connection is released back to pool
                    EntityUtils.consume(httpResponse.getEntity());
                }
            } catch (IOException e) {
                logger.warn("close response fail", e);
            }
        }
    }

    public Page download(Request request, Site site) {
        return download(request, site, (request1, charset, httpResponse) -> {
            String content = getContent(charset, httpResponse);
            Page page = new Page();
            page.setRawText(content);
            page.setUrl(new PlainText(request1.getUrl()));
            page.setRequest(request1);
            page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            return page;
        });
    }
    public <T> T download(Request request, Site site, IHandleResponse<T> handle){
        Set<Integer> acceptStatCode;
        String charset = site.getCharset();
        Map<String, String> headers = site.getHeaders();
        acceptStatCode = site.getAcceptStatCode();
        logger.info("downloading page {}", request.getUrl());
        CloseableHttpResponse httpResponse = null;
        int statusCode=0;
        try {
            HttpUriRequest httpUriRequest = getHttpUriRequest(request, site, headers);
            httpResponse = getHttpClient(site,null).execute(httpUriRequest);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            request.addExtra(Request.STATUS_CODE, statusCode);
            if (statusAccept(acceptStatCode, statusCode)) {
                T page = handle.handleResponse(request, charset, httpResponse);
                onSuccess(request);
                return page;
            } else {
                logger.warn("");
                return null;
            }
        } catch (Exception e) {
            logger.warn("download page " + request.getUrl() + " error", e);
//            if (site.getCycleRetryTimes() > 0) {
//                return addToCycleRetry(request, site);
//            }
            onError(request);
            return null;
        } finally {
            request.addExtra(Request.STATUS_CODE, statusCode);
            try {
                if (httpResponse != null) {
                    //ensure the connection is released back to pool
                    EntityUtils.consume(httpResponse.getEntity());
                }
            } catch (IOException e) {
                logger.warn("close response fail", e);
            }
        }
    }
    @Override
    public void setThread(int thread) {
        httpClientGenerator.setPoolSize(thread);
    }

    protected boolean statusAccept(Set<Integer> acceptStatCode, int statusCode) {
        return acceptStatCode.contains(statusCode);
    }

    protected HttpUriRequest getHttpUriRequest(Request request, Site site, Map<String, String> headers) {
        RequestBuilder requestBuilder = selectRequestMethod(request).setUri(request.getUrl());
        if (headers != null) {
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(site.getTimeOut())
                .setSocketTimeout(site.getTimeOut())
                .setConnectTimeout(site.getTimeOut())
                .setCookieSpec(CookieSpecs.BEST_MATCH);
        if (site.getHttpProxy() !=null) {
			requestConfigBuilder.setProxy(site.getHttpProxy());
			request.addExtra(Request.PROXY, site.getHttpProxy());
		}
        requestBuilder.setConfig(requestConfigBuilder.build());
        if (StringUtils.isNotEmpty(site.getBody())){
            requestBuilder.setEntity(new StringEntity(site.getBody(),site.getCharset()));
        }
        return requestBuilder.build();
    }

    protected RequestBuilder selectRequestMethod(Request request) {
        String method = request.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            //default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            RequestBuilder requestBuilder = RequestBuilder.post();
            NameValuePair[] nameValuePair = (NameValuePair[]) request.getExtra("nameValuePair");
            if (nameValuePair != null && nameValuePair.length > 0) {
                requestBuilder.addParameters(nameValuePair);
            }
            return requestBuilder;
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return RequestBuilder.put();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
        String content = getContent(charset, httpResponse);
        Page page = new Page();
        page.setRawText(content);
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        return page;
    }

    protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
            if (htmlCharset != null) {
                return new String(contentBytes, htmlCharset);
            } else {
                logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()", Charset.defaultCharset());
                return new String(contentBytes);
            }
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }

    protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        String charset;
        // charset
        // 1、encoding in http header Content-Type
        String value = httpResponse.getEntity().getContentType().getValue();
        charset = UrlUtils.getCharset(value);
        if (StringUtils.isNotBlank(charset)) {
            logger.debug("Auto get charset: {}", charset);
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        // 2、charset in meta
        if (StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        logger.debug("Auto get charset: {}", charset);
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }

    public interface IHandleResponse<T>{
        T handleResponse(Request request, String charset, HttpResponse httpResponse) throws Exception;
    }
}
