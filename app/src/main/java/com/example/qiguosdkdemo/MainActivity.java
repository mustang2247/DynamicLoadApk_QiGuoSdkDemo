package com.example.qiguosdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kding.api.QiGuoApi;
import com.kding.api.QiGuoCallBack;

public class MainActivity extends Activity {

    private TextView loginSucLabel;
    private TextView loginFailureLabel;
    private TextView loginCancelLabel;
    private TextView paySucLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        loginSucLabel = (TextView) findViewById(R.id.login_suc_label);
        loginFailureLabel = (TextView) findViewById(R.id.login_failure_label);
        loginCancelLabel = (TextView) findViewById(R.id.login_cancel_label);

        paySucLabel = (TextView) findViewById(R.id.pay_suc_label);
        findViewById(R.id.show_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化sdk
                initSdk();
            }
        });

        findViewById(R.id.show_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaySelection();
            }
        });

    }

    private void initSdk() {
        //20160725-test为测试appid，请换成正式appid
        QiGuoApi.INSTANCE.initSdk(MainActivity.this, "20160725-test", new QiGuoCallBack() {

            @Override
            public void onSuccess() {
                Log.e("init ", " init  suc");
                //初始化成功后方可调用登陆
                showLogin();
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("init ", errorMsg);

            }

            @Override
            public void onCancel() {
                Log.e("init ", "取消");
            }
        });
    }

    private void showLogin() {
        QiGuoApi.INSTANCE.showLogin(new QiGuoCallBack() {

            @Override
            public void onSuccess() {

                String userId = QiGuoApi.INSTANCE.getUserId();
                Log.e("login ", " login  suc" + "   userId = " + userId);
                loginSucLabel.setText("登陆成功，当前账号uid为 " + userId);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("login ", errorMsg);
                loginFailureLabel.setText("登陆失败 " + errorMsg);
            }

            @Override
            public void onCancel() {
                Log.e("login ", "登陆取消");
                loginCancelLabel.setText("登陆取消");
            }
        });
    }

    private void showPaySelection() {
        String money = ((EditText) findViewById(R.id.input_money)).getText().toString();

        if (money == null || money.equals("")) {
            money = "1";
        }
        QiGuoApi.INSTANCE.pay("元宝", "游戏币", money, "" + System.currentTimeMillis(), new QiGuoCallBack() {
            @Override
            public void onSuccess() {
                Log.e("pay ", " pay  suc");
                paySucLabel.setText("支付成功");
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.e("pay ", " pay  fail  " + errorMsg);
                paySucLabel.setText("支付失败");
            }

            @Override
            public void onCancel() {
                Log.e("pay ", " 支付取消");
                paySucLabel.setText("支付取消");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        QiGuoApi.INSTANCE.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        QiGuoApi.INSTANCE.onPause();

    }

}
