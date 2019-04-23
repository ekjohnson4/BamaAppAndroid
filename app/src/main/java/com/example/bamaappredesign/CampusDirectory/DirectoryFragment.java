package com.example.bamaappredesign.CampusDirectory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.bamaappredesign.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryFragment extends Fragment {


    public DirectoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_directory, container, false);
        WebView mWebView = view.findViewById(R.id.directoryWB);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.ua.edu/directory/");//"https://www.ua.edu/directory/?l=&f=michael&p=&d=&t=all&_action=submit");

        return view;
    }

}
