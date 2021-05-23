package com.example.evsherpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

public class Address_registration extends AppCompatActivity {

    private WebView addressPage;
    private TextView txt_addr;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_registration);

        txt_addr=findViewById(R.id.txt_address_result);
        init_webView();

        handler=new Handler();
    }

    @SuppressLint("SetJavaScriptEnabled")
    void init_webView(){
        addressPage=findViewById(R.id.webview_address);
        addressPage.getSettings().setJavaScriptEnabled(true);
        addressPage.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        addressPage.addJavascriptInterface(new AndroidBridge(),"TestApp");
        addressPage.setWebChromeClient(new WebChromeClient());

        //addressPage.loadUrl("localhost:5500/address.html"); //address창 띄워줄 서버만 잇으면 된다.
    }

    private class AndroidBridge{
        @JavascriptInterface
        public void setAddress(final String arg1,final String arg2,final String arg3){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    txt_addr.setText(String.format("(%s) %s %s",arg1,arg2,arg3));

                    init_webView();
                }
            });
        }
    }
}