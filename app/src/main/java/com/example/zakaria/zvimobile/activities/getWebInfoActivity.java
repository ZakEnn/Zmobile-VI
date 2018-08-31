package com.example.zakaria.zvimobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.zakaria.zvimobile.R;

public class getWebInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_web_info);

        WebView webView =(WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://communities.vmware.com/community/vmtn/vsphere/content?filterID=contentstatus%5Bpublished%5D~objecttype~objecttype%5Bthread%5D~thread%5Banswered%5D");

    }
}
