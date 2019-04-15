package com.example.bamaappredesign.CampusMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.bamaappredesign.R;

public class CampusMapFragment extends Fragment {
    private WebView mWebView;
    public CampusMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_campus_map, container, false);
        mWebView = view.findViewById(R.id.campus_map_webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://map.concept3d.com/?id=1222#!m/");

        return view;
    }

}
