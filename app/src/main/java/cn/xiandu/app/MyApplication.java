package cn.xiandu.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.weavey.loading.lib.LoadingLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import cn.xiandu.app.activity.BuildConfig;
import cn.xiandu.app.activity.R;
import okhttp3.OkHttpClient;


/**
 * Created by dell on 2016/11/30.
 */

public class MyApplication extends Application {
    public static final String LOGGER_TAG = "Xiandu";
    public static MyApplication application ;

    private static RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();

        application = this ;
        com.wanjian.sak.LayoutManager.init(this);
        initLogger();
        initOkhttp();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // 根据当前时间自动切换 亮色（light）/暗色（dark）主题
        initLoading();
//        initBugly();
        //LeakCanary 就会自动侦测 activity 的内存泄露

        // 会返回一个预定义的 RefWatcher ,同时也会启用一个 ActivityRefWatcher，用于自动监控调用 Activity.onDestroy() 之后泄露的 activity。
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(){
        return refWatcher ;
    }
    private void initBugly() {
        /**
         *
         * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
             输出详细的Bugly SDK的Log；
             每一条Crash都会被立即上报；
             自定义日志将会在Logcat中输出。
             建议在测试阶段建议设置成true，发布时设置为false。

            第四个参数 增加上报进程控制
                为了节省流量、内存等资源，建议初始化的时候对上报进程进行控制，只在主进程下上报数据

         */
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        //Bugly默认读取AndroidManifest.xml文件中VersionName、Package信息。若您有自己的版本或渠道设定需求，可通过该接口修改。
//        strategy.setAppChannel("myChannel"); //设置渠道
//        strategy.setAppVersion("1.1");
//        strategy.setAppReportDelay();//Bugly会在启动10s后联网同步数据。若您有特别需求，可以修改这个时间。
//        strategy.setAppPackageName();// 设置包名
//        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_APPID, false ,strategy);

//        Bugly.init(getApplicationContext(),  Constant.BUGLY_APPID, true);

//        如需增加自动检查时机可以使用
//        Beta.checkUpgrade(false,false);
    }

    private void initOkhttp() {
        // 支持自签名的https
        try {
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{getApplication().getAssets().open("srca.cer")}, null, null);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LoggerInterceptor("TAG"))
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    //其他配置
                    .build();
            OkHttpUtils.initClient(okHttpClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLogger() {
        Logger
                .init(LOGGER_TAG)                 // default PRETTYLOGGER or use just init()
//                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);       // default LogLevel.FULL ,Note: Use LogLevel.NONE for the release versions.
//                .methodOffset(2);          // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
    }

    private void initLoading(){
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.drawable.define_error)
                .setEmptyImage(R.drawable.define_empty)
                .setNoNetworkImage(R.drawable.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150,40);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    public static Application getApplication(){
        if (application == null){
            application = new MyApplication();
        }
        return application ;
    }
}
