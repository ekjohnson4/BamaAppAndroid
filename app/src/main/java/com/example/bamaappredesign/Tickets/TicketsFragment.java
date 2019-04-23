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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamaappredesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class TicketsFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    final String[] img = new String[1];
    final String[] game = new String[1];
    final String[] date = new String[1];
    final String[] time = new String[1];
    final String[] bowl = new String[1];
    final String[] gate = new String[1];
    final String[] transferTo = {""};
    private static final String ALLOWED_CHARACTERS ="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    public TicketsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        final View inputView = inflater.inflate(R.layout.fragment_tickets, container, false);
        final DocumentReference gameRef = db.collection("ticketInformation").document("game1");
        final DocumentReference studentRef = db.collection("ticketInformation").document("game1").collection("stuTickets").document(Objects.requireNonNull(user.getEmail()));

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

                    studentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null) {
                                    if (document.getBoolean("ticket")){
                                        bowl[0] = document.getString("bowl");
                                        gate[0] = document.getString("gate");
                                        TextView g = inputView.findViewById(R.id.gateBowl);
                                        g.setText(bowl[0] + " Bowl - " + gate[0]);
                                    }
                                    else{
                                        TextView g = inputView.findViewById(R.id.gateBowl);
                                        g.setText("You have no ticket.");
                                    }
                                } else {
                                    Log.d("LOGGER", "No such document");
                                }
                            } else {
                                Log.d("LOGGER", "get failed with ", task.getException());
                            }
                        }
                    });

                } else {
                    Log.d("LOGGER", "No such document");
                }
            } else {
                Log.d("LOGGER", "get failed with ", task.getException());
            }
            }
        });

        //Transfer ticket button
        Button transferTicket = inputView.findViewById(R.id.transfer_button);
        View.OnClickListener transferListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                transferTicket(studentRef, gameRef);
            }
        };

        //Donate ticket button
        Button donateTicket = inputView.findViewById(R.id.donate_button);
        View.OnClickListener donateListener = new View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View view)
            {
                donateTicket(studentRef);
            }
        };

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

    private static String random()
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(15);
        for(int i = 0; i< 15; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private void donateTicket(final DocumentReference studentRef){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Donate Ticket");
        builder.setMessage("Are you sure you wish to donate your ticket?");

        builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Start donation process
                    studentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                if (!document.getBoolean("ticket")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setCancelable(true);
                                    builder.setTitle("Donation Failed!");
                                    builder.setMessage("You don't have a ticket to donate!");
                                    builder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                    AlertDialog dialog2 = builder.create();
                                    dialog2.show();
                                }
                                else{
                                    studentRef.update("ticket", false);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setCancelable(true);
                                    builder.setTitle("Donation Successful!");
                                    String conf = random();
                                    builder.setMessage("Thank you for donating your ticket! You confirmation number is " + conf + ".");
                                    builder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
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
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void transferTicket(final DocumentReference studentRef, final DocumentReference gameRef){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Transfer Ticket");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.transfer_ticket, null);
        builder.setView(dialogView);
        final EditText editText = dialogView.findViewById(R.id.transferEmail);

        builder.setPositiveButton("Transfer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //If edit text is empty, throw warning
                if(editText.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Transfer Failed!");
                    builder.setMessage("You must enter in a Crimson Email!");
                    builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                transferTicket(studentRef, gameRef);
                            }
                        });
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();
                }

                //If valid email, start transfer process
                else{
                    transferTo[0] = editText.getText().toString();
                    studentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            assert document != null;
                            bowl[0] = document.getString("bowl");
                            gate[0] = document.getString("gate");
                            if (!document.getBoolean("ticket")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setCancelable(true);
                                builder.setTitle("Transfer Failed!");
                                builder.setMessage("You don't have a ticket to transfer!");
                                builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                AlertDialog dialog2 = builder.create();
                                dialog2.show();
                            }
                            else{
                                final DocumentReference transferRef = gameRef.collection("stuTickets").document(transferTo[0]);
                                transferRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null) {
                                            if (document.exists()) {
                                                //Remove ticket from ticket holder
                                                studentRef.update("ticket", false);

                                                //Send ticket to transferee
                                                transferRef.update("ticket",true);
                                                transferRef.update("bowl",bowl[0]);
                                                transferRef.update("gate",gate[0]);

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setCancelable(true);
                                                builder.setTitle("Transfer Successful!");
                                                builder.setMessage("Your ticket has been transferred to " + transferTo[0] +
                                                        "! You confirmation number is " + random() + ".");
                                                builder.setPositiveButton("OK",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                                AlertDialog dialog2 = builder.create();
                                                dialog2.show();
                                            }
                                            else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setCancelable(true);
                                                builder.setTitle("Transfer Failed!");
                                                builder.setMessage("User doesn't seem to exist!");
                                                builder.setPositiveButton("OK",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();
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
                        } else {
                            Log.d("LOGGER", "get failed with ", task.getException());
                        }
                        }
                    });
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
