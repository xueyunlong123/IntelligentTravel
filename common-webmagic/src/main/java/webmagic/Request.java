package webmagic;

import com.xyl.common.enums.BaseIntEnum;
import lombok.*;
import webmagic.scheduler.PriorityScheduler;
import webmagic.utils.Experimental;
import webmagic.utils.HttpConstant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Object contains url to crawl.<br>
 * It contains some additional information.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {

    private static final long serialVersionUID = 2062192774891352043L;

    public static final String CYCLE_TRIED_TIMES = "_cycle_tried_times";
    public static final String STATUS_CODE = "statusCode";
    public static final String PROXY = "proxy";

    @Getter@Setter
    private String key;

    private String url;

    private String method;

    private Site site;

    @Getter@Setter
    private BaseIntEnum baseIntEnum;

    /**
     * Store additional information in extras.
     */
    private Map<String, Object> extras;

    /**
     * Priority of the request.<br>
     * The bigger will be processed earlier. <br>
     * @see PriorityScheduler
     */
    private long priority;


    public Request(String url) {
        this.url = url;
    }

    public long getPriority() {
        return priority;
    }

    /**
     * Set the priority of request for sorting.<br>
     * Need a scheduler supporting priority.<br>
     * @see PriorityScheduler
     *
     * @param priority priority
     * @return this
     */
    @Experimental
    public Request setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    public Object getExtra(String key) {
        if (extras == null) {
            return null;
        }
        return extras.get(key);
    }

    public Request addExtra(String key, Object value) {
        if (extras == null) {
            extras = new HashMap<String, Object>();
        }
        extras.put(key, value);
        return this;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (!url.equals(request.url)) return false;

        return true;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * The http method of the request. Get for default.
     * @return httpMethod
     * @see HttpConstant.Method
     * @since 0.5.0
     */
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", extras=" + extras +
                ", priority=" + priority +
                '}';
    }
}
