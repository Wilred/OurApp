package com.example.user.ourapp;

import android.content.ContentProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentProvider;
import android.content.Intent;
import android.net.Uri;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.ourapp.loginIn.LoginActivity;
import com.example.user.ourapp.projIss.IssuesFragment;
import com.example.user.ourapp.projIss.PieGraph;
import com.example.user.ourapp.projIss.ProjectFragment;
//import com.example.user.ourapp.weather.MainFragment;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.user.ourapp.projIss.IssuesFragment.projectNameAll;

public class Main2Activity_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "myLogs";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private CircleImageView imageView;
    private TextView name;
    private LinearLayout linearLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new ProjectFragment()).commit();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(Main2Activity_Drawer.this, LoginActivity.class));

                }

            }
        };



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.nameAcc);
        imageView = (CircleImageView) headerView.findViewById(R.id.iconAcc);

        String personName = getIntent().getExtras().getString("name");
        Log.e("onCreate", "yes");
        String personPhoto = getIntent().getExtras().getString("icon");
        Log.d("PersonPhotoString", personPhoto);
        name.setText(personName);
        Picasso.get()
                .load(Uri.parse(personPhoto))
                .resize(200, 200)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2_activity__drawer, menu);
        Log.e("onCreateOptionsMenu", "yes");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.e("onOptionsItemSelected", "yes");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.e("action_settings", "yes");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFragment(android.support.v4.app.Fragment fragment) {
        if (fragment != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_cont, fragment);
            ft.commit();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_project) {

            getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new ProjectFragment()).commit();

        } else if (id == R.id.nav_issues) {

            if (projectNameAll != null) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new IssuesFragment(projectNameAll)).commit();
            } else {
                Toast.makeText(this, "Sorri, you don't have any Project", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_pie_graph) {

            getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new PieGraph()).commit();

        } else if (id == R.id.nav_log_out) {

            mAuth.signOut();

            LoginManager.getInstance().logOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

}
