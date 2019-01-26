package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class CompanyDetails extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        webView = (WebView)findViewById(R.id.webView2);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.geeksforgeeks.org");
    }
}
