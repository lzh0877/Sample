# Sample
构建项目

依赖相关
1.retrofit2 + rxjava2 
2.butterknife
3.glide4
使用
  1.初始化Application
   public class MyApplication extends BaseApplication {

      @Override
      public void onCreate() {
          super.onCreate();
          //    接口地址            加密请求的appKey     加密请求的appSecret
          init(Constant.URL.BASE, Constant.APP_SECRET, Constant.APP_KEY);
          if (LeakCanary.isInAnalyzerProcess(this)) {
              return;
          }
          LeakCanary.install(this);
      }
  }
  
 2.创建Activity
  public class MainActivity extends BaseActivity {
  
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, TYPE.BKF);

        userService = new RetrofitManager().getDefaultClient(UserService.class);
        setToolbar("Hello World", true);// 设置Toolbar标题
        
        showToast("Hello World");       // 显示Toast

    }
    ...
  }
 3. 实例Retrofit接口
  UserService userService = new RetrofitManager().getDefaultClient(UserService.class);
 4.实例Dialog
  BaseDialog dialog = new BaseDialog(mContext, R.layout.layout_loading, R.style.Dialog_FullScreen);
 5.关于RxJava的DisposableSubscriber
  根据实际使用重载onError方法，示例参考CustomDisposable
