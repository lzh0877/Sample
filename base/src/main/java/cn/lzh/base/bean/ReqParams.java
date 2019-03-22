package cn.lzh.base.bean;

import java.util.HashMap;
import java.util.Map;

import cn.lzh.base.util.Md5Util;
import cn.lzh.base.util.SharedPreferencesUtils;

/**
 * TODO 需要手动刷新请求头数据 {@link #refreshParamsMap}
 * Created by LZH On 2019/3/17
 */
public class ReqParams {

    public static final String LOGIN_DATA = "login";
    public static final String TOKEN = "token";
    private static ReqParams ourInstance = null;
    private Map<String, Object> parasMap = new HashMap<>();
    private String authorization = "";
    private String appKey = "";
    private String appSecret = "";
    private String token = "";

    public static ReqParams getInstance() {
        if (ourInstance == null) {
            synchronized (ReqParams.class) {
                if (ourInstance == null) {
                    ourInstance = new ReqParams();
                }
            }
        }
        return ourInstance;
    }

    private ReqParams() {
    }

    public Map<String, Object> refreshParamsMap() {
        // TODO refresh param
        long timestamp = System.currentTimeMillis() / 1000;
        token = SharedPreferencesUtils.getString(LOGIN_DATA, "token");
        parasMap.put("Authorization", "Bearer " + token);
        parasMap.put("appkey", appKey);
        parasMap.put("appsecret", Md5Util.md5(appSecret + timestamp));
        parasMap.put("timestamp", timestamp);
        return parasMap;
    }

    public void initReqMap(String appSecret, String appKey) {
        this.appSecret = appSecret;
        this.appKey = appKey;
    }

}
