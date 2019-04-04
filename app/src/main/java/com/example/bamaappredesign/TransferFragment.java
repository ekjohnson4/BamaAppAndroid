package com.example.bamaappredesign;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TransferFragment extends Fragment {
    public TransferFragment() {
        //required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout for this fragment
        final View inputView = inflater.inflate(R.layout.fragment_transfer, container, false);

        //confirm transfer ticket button
        Button confirmtransfer = inputView.findViewById(R.id.confirmtransfer_button);
        View.OnClickListener ctransferListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragtran = getFragmentManager().beginTransaction();
                fragtran.replace(R.id.flMain, new cTransferFragment());
                fragtran.addToBackStack(null);
                fragtran.commit();
            }
        };

        confirmtransfer.setOnClickListener(ctransferListener);
        return inputView;
    }
}
