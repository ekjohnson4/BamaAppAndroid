package com.example.bamaappredesign.Laundry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bamaappredesign.R;

public class LaundryFragment extends Fragment {
    private WebView mWebView;

    public LaundryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_laundry, container, false);
        mWebView = view.findViewById(R.id.laundry_web_view);
        mWebView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.laundryview.com/home/27/1937650");

        return view;
    }

}
