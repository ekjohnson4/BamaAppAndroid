package com.example.bamaappredesign.Home;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamaappredesign.CampusMapFragment;
import com.example.bamaappredesign.CatalogFragment;
import com.example.bamaappredesign.DirectoryFragment;
import com.example.bamaappredesign.Emergency.EmergencyFragment;
import com.example.bamaappredesign.Events.EventsFragment;
import com.example.bamaappredesign.LaundryFragment;
import com.example.bamaappredesign.Links.LinksFragment;
import com.example.bamaappredesign.MainActivity;
import com.example.bamaappredesign.News.NewsFragment;
import com.example.bamaappredesign.R;
import com.example.bamaappredesign.StudentFragment;
import com.example.bamaappredesign.Settings.StudentSettingsFragment;
import com.example.bamaappredesign.TransportationFragment;
import com.example.bamaappredesign.Settings.VisitorSettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import static com.example.bamaappredesign.R.layout.action_bar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isLoggedIn = false;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        int value = -1; // or other values
        final String[] name = new String[1];

        if(b != null)
            value = b.getInt("key");

        //User is logged in, update layout for student
        if(value == 1){
            isLoggedIn = true;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new HomeStudentFragment());
            ft.commit();

            //Display Name
            DocumentReference nameRef = db.collection("studentInformation").document(user.getUid());
            nameRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            name[0] = document.getString("fname");
                            TextView n = findViewById(R.id.nameID);
                            n.setText("Welcome, " + name[0] + "!");

                        } else {
                            Log.d("LOGGER", "No such document");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });
        }
        else{
            isLoggedIn = false;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new HomeVisitorFragment());
            ft.commit();
        }

        //Nav menu setup
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(action_bar);

        //Change menu to student menu if user is logged in
        if(value == 1){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.student_activity_drawer);

            //Make logo in ActionBar shortcut to Student home page
            ImageView img = findViewById(R.id.icon);
            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.flMain, new HomeStudentFragment());
                    ft.commit();
                }
            });
        }
        else{
            //Make logo in ActionBar shortcut to Visitor home page
            ImageView img = findViewById(R.id.icon);
            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.flMain, new HomeVisitorFragment());
                    ft.commit();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        if(!isLoggedIn){
            MenuItem signIn = menu.findItem(R.id.sign_out);
            signIn.setTitle("Sign In");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar view_pager clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(isLoggedIn) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new StudentSettingsFragment());
                ft.commit();
            }
            else{
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new VisitorSettingsFragment());
                ft.commit();
            }
        }
        else if (id == R.id.sign_out){
            if(isLoggedIn) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
            else{
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view view_pager clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            if(isLoggedIn) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, new HomeStudentFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
            else{
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        }
        else if (id == R.id.map) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new CampusMapFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.student) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new StudentFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.catalog) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new CatalogFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.events) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new EventsFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.transportation) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new TransportationFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.emergency) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new EmergencyFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.laundry) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new LaundryFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.directory) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new DirectoryFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.news) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new NewsFragment());
            ft.addToBackStack(null);
            ft.commit();
        }
        else if (id == R.id.links) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flMain, new LinksFragment());
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean getLoggedIn(){
        return isLoggedIn;
    }

}
