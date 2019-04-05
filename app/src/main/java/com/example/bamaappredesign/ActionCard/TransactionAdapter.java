package com.example.bamaappredesign.ActionCard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bamaappredesign.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    Context mContext;
    List<Transaction> mData;

    public TransactionAdapter(Context mContext, List<Transaction> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.transaction_list,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.location.setText(mData.get(i).getLocation());
        myViewHolder.price.setText("- $" + mData.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView location;
        private TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item =  itemView.findViewById(R.id.contact_item);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
        }
    }
}