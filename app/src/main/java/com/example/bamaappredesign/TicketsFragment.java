package com.example.bamaappredesign;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;

public class TicketsFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    final String[] img = new String[1];
    final String[] game = new String[1];
    final String[] date = new String[1];
    final String[] time = new String[1];

    public TicketsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final View inputView = inflater.inflate(R.layout.fragment_tickets, container, false);

        //Transfer ticket button
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

        //Donate ticket button
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

        DocumentReference imgRef = db.collection("ticketInformation").document("game1");

        //Display upcoming game information image
        imgRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        img[0] = document.getString("banner");
                        game[0] = document.getString("opponent");
                        date[0] = document.getString("date");
                        time[0] = document.getString("time");

                        //Display ticket banner
                        new TicketsFragment.DownloadImageTask((ImageView) inputView.findViewById(R.id.banner))
                                .execute(img[0]);

                        //Display opponent
                        TextView o = inputView.findViewById(R.id.game);
                        o.setText(game[0]);
                     
                        //Display game date
                        TextView d = inputView.findViewById(R.id.date);
                        d.setText(date[0] + " @ " + time[0]);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        transferTicket.setOnClickListener(transferListener);
        donateTicket.setOnClickListener(donateListener);
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
}
