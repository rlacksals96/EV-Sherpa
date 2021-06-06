package com.example.evsherpa.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evsherpa.R;


public class WorkplaceAddressRegistration extends AppCompatActivity {

    private WebView browser;
    private TextView txt_addr;
    private Handler handler;

    private class MyJavaScriptInterface{

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data){
            Bundle extra=new Bundle();
            Intent intent=new Intent();
            extra.putString("data",data);
            intent.putExtras(extra);
            Log.e("before setResult","check");
            setResult(Activity.RESULT_OK,intent);
            finish();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_registration);

        browser = (WebView) findViewById(R.id.webview_address);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        //TODO: 서버와 연결시 해당 페이지 넘겨 받을 수 있도록 교체하기
        browser.loadUrl("http://a4382432.dothome.co.kr/daum.html");

    }
}