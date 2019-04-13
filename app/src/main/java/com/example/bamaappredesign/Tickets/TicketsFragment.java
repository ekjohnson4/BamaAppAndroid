package com.example.bamaappredesign.Tickets;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.bamaappredesign.Home.HomeStudentFragment;
import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.Random;

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
        final DocumentReference gameRef = db.collection("ticketInformation").document("game1");
        final DocumentReference gameRef2 = db.collection("ticketInformation").document("game1").collection("stuTickets").document(user.getUid());

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Donate Ticket");
                builder.setMessage("Are you sure you wish to donate your ticket?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Donate ticket
                                gameRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document != null) {
                                                if (document.getBoolean("ticket") == false) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    builder.setCancelable(true);
                                                    builder.setTitle("Donation Failed!");
                                                    builder.setMessage("You don't have a ticket to donate!");
                                                    builder.setPositiveButton("OK",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                }
                                                            });
                                                    AlertDialog dialog2 = builder.create();
                                                    dialog2.show();
                                                }
                                                else{
                                                    gameRef2.update("ticket", false);
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    builder.setCancelable(true);
                                                    builder.setTitle("Donation Successful!");
                                                    builder.setMessage("Thank you for donating your ticket! You confirmation number is " + random(15) + ".");
                                                    builder.setPositiveButton("OK",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                }
                                                            });
                                                    AlertDialog dialog2 = builder.create();
                                                    dialog2.show();
                                                }
                                            } else {
                                                Log.d("LOGGER", "No such document");
                                            }
                                        } else {
                                            Log.d("LOGGER", "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        //Display upcoming game information image
        gameRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

    private static final String ALLOWED_CHARACTERS ="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    private static String random(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
