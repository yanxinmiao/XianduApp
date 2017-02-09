package cn.xiandu.app.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.badoo.mobile.util.WeakHandler;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiandu.app.utils.CommonTool;
import cn.xiandu.app.utils.Constant;
import cn.xiandu.app.utils.SharedPreferenceUtils;
import hugo.weaving.DebugLog;
import okhttp3.Call;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.activity_welcome)
    RelativeLayout activityWelcome;
    private WeakHandler weakHandler;

    private int[] images = new int[]{R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3, R.drawable.welcome4};

    private PackageManager mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 不显示系统的标题栏，保证windowBackground和界面activity_main的大小一样，显示在屏幕不会有错位（去掉这一行试试就知道效果了）
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        weakHandler = new WeakHandler();
        getSystemService(Context.ACTIVITY_SERVICE);
        //随机生成启动页面背景图
        int max = 3;
        int min = 0;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;//生成指定区间内的随机数
        mPm = getApplicationContext().getPackageManager();
//        if (s % 2 == 0){
//            switchIcon(1);
//        }else{
//            switchIcon(2);
//        }
        image.setImageResource(images[s]);
        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()){
                    return ;
                }
                startActivity(MainActivity.class);
                finish();
            }
        }, 1000);
//        String channel = SharedPreferenceUtils.getString(Constant.CHANNEL_KEY);
//        if (!TextUtils.isEmpty(channel)) {
//
//            return ;
//        }
//        getData();
    }

    /**
     *
     * @param useCode
     *
     * 1、 活动图标
     * 2、 普通图标
     */
    private void switchIcon(int useCode) {
        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag = "cn.xiandu.app.activity.MainAliasActivity";
            String icon_tag_1212 = "cn.xiandu.app.activity.icon_tag_1212";

            if (useCode != 3) {
                PackageManager pm = getPackageManager();
                ComponentName normalComponentName = new ComponentName(getBaseContext(), icon_tag);
                //正常图标新状态
                int normalNewState = useCode == 2 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            normalComponentName,
                            normalNewState,
                            PackageManager.DONT_KILL_APP);
                }

                ComponentName actComponentName = new ComponentName(getBaseContext(), icon_tag_1212);
                //正常图标新状态
                int actNewState = useCode == 1 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            actComponentName,
                            actNewState,
                            PackageManager.DONT_KILL_APP);
                }
            }
        } catch (Exception e) {
        }
    }
    /**
     * 获取频道id 、name
     */
    private void getData() {
        if (!CommonTool.isNetworkConnected(this)) {
            weakHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isFinishing()){
                        return ;
                    }
                    startActivity(MainActivity.class);
                }
            }, 2000);
            return;
        }
        OkHttpUtils
                .get()
                .url(Constant.CHANNAL_ID_URL)
                .addHeader("apikey", Constant.API_KEY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.i("", "e:" + e.getMessage());
                        startActivity(MainActivity.class);
                    }
                    @DebugLog
                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.optInt("showapi_res_code");
                            if (code == 0) {
                                JSONObject jo = jsonObject.optJSONObject("showapi_res_body");
                                if (jo == null){
                                    startActivity(MainActivity.class);
                                    return ;
                                }
                                JSONArray ja = jo.optJSONArray("channelList");
                                if (ja != null && ja.length() > 0) {
                                    SharedPreferenceUtils.setString(Constant.CHANNEL_KEY, ja.toString());
                                }
                            }
                            if (isFinishing()){
                                return ;
                            }
                            startActivity(MainActivity.class);
                        } catch (JSONException e) {
                            startActivity(MainActivity.class);
                            e.printStackTrace();
                        }
                    }
                });

    }
}
