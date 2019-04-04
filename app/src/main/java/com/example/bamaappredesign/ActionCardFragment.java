package com.example.bamaappredesign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ActionCardFragment extends Fragment{
    private TextView diningDollarText;
    private TextView bamaCashText;
    private RecyclerView myrecyclerview;
    private Button diningDollarsButton;
    private Button bamaCashButton;
    private ImageView card;
    TransactionAdapter adapter;
    FirebaseFirestore db;
    private List<Transaction> linkList = new ArrayList<>();
    FirebaseUser user;

    public ActionCardFragment()
    {
        //required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Set view
        linkList.clear();
        final View inputView = inflater.inflate(R.layout.fragment_card, container, false);
        myrecyclerview = (RecyclerView) inputView.findViewById(R.id.rvTransactions);
        adapter = new TransactionAdapter(getContext(),linkList);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println(linkList.size());
        myrecyclerview.setAdapter(adapter);
        myrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //Inflate the layout for this fragment
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final String[] fname = new String[1];
        final String[] lname = new String[1];
        final String[] occ = new String[1];
        final String[] url = new String[1];


        //Set TextViews
        diningDollarText = inputView.findViewById(R.id.textview2);
        bamaCashText = inputView.findViewById(R.id.textview3);

        // Create a query against the collection.
        db = FirebaseFirestore.getInstance();
        assert user != null;
        DocumentReference urlRef = db.collection("actionCards").document(user.getUid());

        //Display action card image
        urlRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        url[0] = document.getString("image");

                        //Display action card
                        new DownloadImageTask((ImageView) inputView.findViewById(R.id.card_image))
                                .execute(url[0]);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Display name
        DocumentReference nameRef = db.collection("studentInformation").document(user.getUid());
        nameRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        fname[0] = document.getString("fname");
                        lname[0] = document.getString("lname");
                        occ[0] = document.getString("occupation");
                        TextView n = inputView.findViewById(R.id.textview1);
                        n.setText(fname[0] + " " + lname[0]);
                        TextView o = inputView.findViewById(R.id.textview4);
                        o.setText(occ[0]);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Display monetary information
        DocumentReference actRef = db.collection("actionCards").document(user.getUid());
        actRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Spannable bamaCashColor = new SpannableString("$" + document.getString("bamaCash"));
                        Spannable diningDollarColor = new SpannableString("$" + document.getString("diningDollars"));
                        diningDollarColor.setSpan(new ForegroundColorSpan(Color.rgb(26, 117, 37)), 0, diningDollarColor.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        bamaCashColor.setSpan(new ForegroundColorSpan(Color.rgb(26, 117, 37)), 0, bamaCashColor.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        bamaCashText.append(bamaCashColor);
                        diningDollarText.append(diningDollarColor);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        //Lost button
        Button lost = inputView.findViewById(R.id.lost);
        View.OnClickListener lostListener = new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Lost Card");
                builder.setMessage("Immediately report your card lost or stolen. During regular business hours, call Action Card at 205-348-2288 and after hours or holidays, call UAPD at 205-348-5454.");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };
        diningDollarsButton = inputView.findViewById(R.id.dollars_button);
        diningDollarsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDiningDollarTransactions();
            }
        });
        bamaCashButton = inputView.findViewById(R.id.cash_button);
        bamaCashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  setBamaCashTransactions();
            }
        });
        lost.setOnClickListener(lostListener);
       // setDiningDollarTransactions();
        return inputView;
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void setDiningDollarTransactions(){
       // linkList.clear();
        db.collection("transactions")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("Type").equals("Dining Dollars")){
                                    Transaction a = new Transaction(String.valueOf(document.get("Price")), String.valueOf(document.get("Location")), String.valueOf(document.get("Type")));
                                    linkList.add(a);
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        adapter.notifyDataSetChanged();
    }
    private void setBamaCashTransactions(){
        //linkList.clear();
        db.collection("transactions")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("Type").equals("Bama Cash")){
                                    Transaction a = new Transaction(String.valueOf(document.get("Price")), String.valueOf(document.get("Location")), String.valueOf(document.get("Type")));
                                    linkList.add(a);
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}