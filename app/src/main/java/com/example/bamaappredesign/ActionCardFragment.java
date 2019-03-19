package com.example.bamaappredesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ActionCardFragment extends Fragment
{
    public ActionCardFragment()
    {
        //required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_card, container, false);
        View inputView =  inflater.inflate(R.layout.fragment_card,
                container,
                false
        );

         //Bama Cash button
        Button bamaCash = inputView.findViewById(R.id.bCash_button);
        View.OnClickListener bCashListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragTran = getFragmentManager().beginTransaction();
                fragTran.replace(R.id.flMain, new CashFragment());
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
         };

        //Dining Dollars button
        Button diningDollars = inputView.findViewById(R.id.dDollars_button);
        View.OnClickListener dDollarsListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragTran = getFragmentManager().beginTransaction();
                fragTran.replace(R.id.flMain, new DollarsFragment());
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
        };

        //Accounts Button
        Button accounts = inputView.findViewById(R.id.accounts_button);
        View.OnClickListener accountsListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragTran = getFragmentManager().beginTransaction();
                fragTran.replace(R.id.flMain, new AccountFragment());
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
        };

        //Deposit Button
        Button deposit = inputView.findViewById(R.id.deposit_button);
        View.OnClickListener depositListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragTran = getFragmentManager().beginTransaction();
                fragTran.replace(R.id.flMain, new DepositFragment());
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
        };

        //Lost Card Button
        Button lostCard = inputView.findViewById(R.id.lostCard_button);
        View.OnClickListener lostCardListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragTran = getFragmentManager().beginTransaction();
                fragTran.replace(R.id.flMain, new LostCardFragment());
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
        };

        //Change Password/Change Pin Button
        Button changeSecurity = inputView.findViewById(R.id.changeSecurity_button);
        View.OnClickListener changeSecurityListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                assert getFragmentManager() != null;
                FragmentTransaction fragTran = getFragmentManager().beginTransaction();
                fragTran.replace(R.id.flMain, new ChangeSecurityFragment());
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
        };


        bamaCash.setOnClickListener(bCashListener);
        diningDollars.setOnClickListener(dDollarsListener);
        accounts.setOnClickListener(accountsListener);
        deposit.setOnClickListener(depositListener);
        lostCard.setOnClickListener(lostCardListener);
        changeSecurity.setOnClickListener(changeSecurityListener);
        return inputView;
    }
}
