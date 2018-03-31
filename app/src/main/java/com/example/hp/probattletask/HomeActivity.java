package com.example.hp.probattletask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.hp.probattletask.Adapters.MainAdapter;
import com.example.hp.probattletask.LDB.LDAL.KryptoAccessLayer;
import com.example.hp.probattletask.Wrappers.CryptoList;
import com.example.hp.probattletask.charts.ChartActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RadioButton rbAll, rbFavourites;
    RecyclerView rvAll;
    ArrayList<CryptoList> cryptoLists, favouritesList;
    MainAdapter allAdapter, favouritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setRadioButtonsListeners();
        getDataFromLocal();
    }

    private void setRecyclersData() {
        rvAll.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        allAdapter = new MainAdapter(HomeActivity.this, cryptoLists);
        favouritesAdapter  = new MainAdapter(HomeActivity.this,favouritesList);
        rvAll.setAdapter(allAdapter);



    }

    private void setRadioButtonsListeners() {
        rbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getDataFromLocal();
                   rvAll.setAdapter(allAdapter);
                } else {
                    getDataFromLocal();
                    rvAll.setAdapter(favouritesAdapter);
                  }
            }
        });

    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crypto");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        rbAll = (RadioButton) findViewById(R.id.rbAll);
        rbFavourites = (RadioButton) findViewById(R.id.rbFavourites);
        rvAll = (RecyclerView) findViewById(R.id.rvAll);
        rbAll.setChecked(true);
    }

    @Override
    public void setContentView(View view) {

        super.setContentView(view);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(HomeActivity.this, ChartActivity.class));
        } else if (id == R.id.nav_gallery) {
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<CryptoList> getAllCryptosFromLocal() {
        return new ArrayList<>();
    }

    public ArrayList<CryptoList> getFavouriteCryptos() {
        return new ArrayList<>();
    }

    public void getDataFromLocal() {
        KryptoAccessLayer accessLayer = new KryptoAccessLayer(HomeActivity.this);
        try{
            accessLayer.open();
            cryptoLists = new ArrayList<>();
            cryptoLists = accessLayer.getAllCryptos();
            favouritesList = new ArrayList<>();
            favouritesList = accessLayer.getFavouriteCryptos();
            accessLayer.close();
            if(cryptoLists != null && favouritesList != null)


            setRecyclersData();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
