package cn.xiandu.app.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.xiandu.app.utils.ToastUtils;
import okhttp3.OkHttpClient;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.accountinput)
    TextInputLayout accountinput;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passwordinput)
    TextInputLayout passwordinput;
    @BindView(R.id.account_sign_in_button)
    Button accountSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (accountinput.getError() != null){
                    accountinput.setError(null);
                }
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ( id == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    //inputMethodManager.showSoftInput(getWindow().getDecorView(),InputMethodManager.SHOW_FORCED);//显示
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    //attemptLogin();
                    ToastUtils.showMyToast("开始登录...");
                    return true;
                }
                return false;
            }
        });
        /**
         * 这三个类型分别代表：
             Params: 执行时传入的参数类型
             Progress: 更新时的传入的参数类型
             Result: 异步返回结果的参数类型。
             如果某个类型不用，在继承的时候直接使用Void类。
         */
        new AsyncTask<String , Integer , Long>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected Long doInBackground(String... params) {

                return null;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();

            }
        }.execute();

        try {
            InputStream inputStream = getAssets().open("server.cer");
            SSLSocketFactory sslSocketFactory = getSslSocketFactory(inputStream);
            new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SSLSocketFactory getSslSocketFactory(InputStream certificates) {
        SSLContext sslContext = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate ca;
            try {
                ca = certificateFactory.generateCertificate(certificates);
            } finally {
                certificates.close();
            }
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return sslContext != null? sslContext.getSocketFactory():null;
    }
}
