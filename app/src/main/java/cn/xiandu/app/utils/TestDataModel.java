package cn.xiandu.app.utils;

import android.content.Context;
import android.widget.TextView;

import cn.xiandu.app.activity.R;

/**
 * Created by dell on 2017/1/5.
 */

public class TestDataModel {
    private static TestDataModel sInstance;
    private Context context;
    public static TestDataModel getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TestDataModel(context);
        }
        return sInstance;
    }

    private TestDataModel(Context context){
        this.context = context;
    }

    public void setRetainedTextView(TextView textView) {
        String stringId = context.getResources().getString(R.string.aboutus);
        textView.setText(stringId);
    }
}
