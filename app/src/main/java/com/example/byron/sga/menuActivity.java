package com.example.byron.sga;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;

import java.util.HashMap;

public class menuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    UserSessionManager session;
    SearchView searchView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        session = new UserSessionManager(getApplicationContext());
        String user = intent.getStringExtra("user");
        String pass = intent.getStringExtra("pass");
        String rol = intent.getStringExtra("rol");
        session.createUserLoginSession(
                intent.getStringExtra("user"),
                intent.getStringExtra("pass"),
                intent.getStringExtra("rol")
        );
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //bundle.putString("user", user);
        //bundle.putString("pass", pass);
        //bundle.putString("rol", rol);
        hideItem();
    }

    private void hideItem()
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
            HashMap<String, String> hm = session.getUserDetails();
            String val = hm.get("type");
            switch (val){
                case "2":{
                    nav_Menu.findItem(R.id.nav_ciclos).setVisible(false);
                    nav_Menu.findItem(R.id.nav_alumnos).setVisible(false);
                    nav_Menu.findItem(R.id.nav_carreras).setVisible(false);
                    nav_Menu.findItem(R.id.nav_cursos).setVisible(false);
                    nav_Menu.findItem(R.id.nav_oferta).setVisible(false);
                    nav_Menu.findItem(R.id.nav_seguridad).setVisible(false);
                }
                break;
                case "3":{
                    nav_Menu.findItem(R.id.nav_ciclos).setVisible(false);
                    nav_Menu.findItem(R.id.nav_profesores).setVisible(false);
                    nav_Menu.findItem(R.id.nav_carreras).setVisible(false);
                    nav_Menu.findItem(R.id.nav_cursos).setVisible(false);
                    nav_Menu.findItem(R.id.nav_oferta).setVisible(false);
                    nav_Menu.findItem(R.id.nav_seguridad).setVisible(false);
                }
                break;
                default:{

                }
            }


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
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                //UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            this.session.logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ciclos) {
            loadFragment(new Ciclos());
        } else if (id == R.id.nav_carreras) {
            loadFragment(new Carreras());
        } else if (id == R.id.nav_cursos) {
            loadFragment(new Cursos());
        } else if (id == R.id.nav_oferta) {
            loadFragment(new OfertaAcademica());
        } else if (id == R.id.nav_profesores) {
            loadFragment(new Profesores());
        } else if (id == R.id.nav_seguridad) {
            loadFragment(new Seguridad());
        } else if (id == R.id.nav_alumnos){
            loadFragment(new Alumnos());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
