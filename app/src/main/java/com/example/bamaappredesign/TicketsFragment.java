package com.example.bamaappredesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TicketsFragment extends Fragment {

    public TicketsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tickets, container, false);

        View inputView = inflater.inflate(R.layout.fragment_tickets, container, false);

        //transfer ticket button
        Button transferTicket = inputView.findViewById(R.id.transfer_button);
        View.OnClickListener transferListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragtran = getFragmentManager().beginTransaction();
                fragtran.replace(R.id.flMain, new TransferFragment());
                fragtran.addToBackStack(null);
                fragtran.commit();
            }
        };

        //donate ticket button
        Button donateTicket = inputView.findViewById(R.id.donate_button);
        View.OnClickListener donateListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragtran = getFragmentManager().beginTransaction();
                fragtran.replace(R.id.flMain, new DonateFragment());
                fragtran.addToBackStack(null);
                fragtran.commit();
            }
        };

        //add portion to page to display existing user ticket if there is one or hard code it

        transferTicket.setOnClickListener(transferListener);
        donateTicket.setOnClickListener(donateListener);
        return inputView;
    }
}
