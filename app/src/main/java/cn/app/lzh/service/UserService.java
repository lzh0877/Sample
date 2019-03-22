package cn.app.lzh.service;

import java.util.Map;

import cn.app.lzh.bean.LoginResp;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static cn.app.lzh.sys.Constant.URL.LOGIN;
import static cn.app.lzh.sys.Constant.URL.USER_INFO;

/**
 * Created by LZH On 2019/3/19
 */
public interface UserService {

    @POST(LOGIN)
    Flowable<LoginResp> login(@Body Map<String, Object> map);

    @GET(USER_INFO)
    Flowable<Map<String, Object>> queryUserInfo();

}
