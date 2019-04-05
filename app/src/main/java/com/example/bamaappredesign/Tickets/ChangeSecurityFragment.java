package com.example.bamaappredesign.Tickets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;

import com.example.bamaappredesign.R;

public class ChangeSecurityFragment extends Fragment
{
    public ChangeSecurityFragment()
    {
        //required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_changesecurity, container, false);
    }
}
