package com.example.evsherpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Address_registration extends AppCompatActivity {

    private WebView browser;
    private TextView txt_addr;
    private Handler handler;

    class MyJavaScriptInterface{

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processData(String data){
            Bundle extra=new Bundle();
            Intent intent=new Intent();
            extra.putString("data",data);
            intent.putExtras(extra);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
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

        browser.loadUrl("http://a4382432.dothome.co.kr/daum.html");
    }





}