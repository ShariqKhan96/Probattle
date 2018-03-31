package com.example.hp.probattletask;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.probattletask.API.Client;
import com.example.hp.probattletask.API.WebService;
import com.example.hp.probattletask.LDB.LDAL.KryptoAccessLayer;
import com.example.hp.probattletask.Wrappers.AllCryptosEnt;
import com.example.hp.probattletask.Wrappers.CryptoList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    boolean isConnected;
    int PERMISSION_ALL = 1;
    boolean gotData = false;

    FrameLayout ball1;
    FrameLayout ball2;
    FrameLayout ball3;
    FrameLayout ball4;
    TextView location;
    Snackbar wifiSnack;
    RelativeLayout splashRl;
    TextView progressUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ball1 = findViewById(R.id.splash_ball1);
        ball2 = findViewById(R.id.splash_ball2);
        ball3 = findViewById(R.id.splash_ball3);
        ball4 = findViewById(R.id.splash_ball4);
        splashRl = findViewById(R.id.splashRl);
        wifiSnack = Snackbar.make(splashRl, "TURN ON WIFI", Snackbar.LENGTH_INDEFINITE);
        //Toast.makeText(SplashActivity.this,"TURN ON WIFI",Toast.LENGTH_SHORT).show();
        progressUpdate = findViewById(R.id.progress_tv);

        getPermissions();
        isConnected = checkWifi();
        if (!isConnected) {
            wifiSnack.show();
            wifiSnack.setAction("RETRY", clickListener);
//            Toast.makeText(SplashActivity.this,"Retry",Toast.LENGTH_SHORT).show();

        } else {
            getData();
            startAnimation();
        }
    }

    private void getData() {
        WebService service = Client.getClient().create(WebService.class);
        final Call<List<CryptoList>> loginWrapperCall = service.getAllCrypto();
        loginWrapperCall.enqueue(new Callback<List<CryptoList>>() {
            @Override
            public void onResponse(Call<List<CryptoList>> call, Response<List<CryptoList>> response) {
                saveDataToLocal(response.body());
            }

            @Override
            public void onFailure(Call<List<CryptoList>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, t.toString(), Toast.LENGTH_LONG).show();


            }
        });
    }

    private void saveDataToLocal(List<CryptoList> body) {
        AllCryptosEnt allCryptosEnt = new AllCryptosEnt();
        allCryptosEnt.setCryptoList(body);
        KryptoAccessLayer accessLayer = new KryptoAccessLayer(SplashActivity.this);
        try {
            accessLayer.open();
            accessLayer.insert(allCryptosEnt);
            accessLayer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        gotData = true;

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isConnected = checkWifi();
            if (isConnected) {
                getData();
                startAnimation();
            } else {
               wifiSnack = Snackbar.make(splashRl, "TURN ON WIFI", Snackbar.LENGTH_INDEFINITE);
               wifiSnack.setAction("RETRY", clickListener);
               wifiSnack.show();
               // Toast.makeText(SplashActivity.this,"TURN ON WIFI",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void startAnimation() {


        final ArrayList<FrameLayout> frameLayouts = new ArrayList<>();
        frameLayouts.add(ball1);
        frameLayouts.add(ball2);
        frameLayouts.add(ball3);
        frameLayouts.add(ball4);

        final int[] i = {3};

        final ScaleAnimation growAnimation = new ScaleAnimation(0.1f, 1f, 0.1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final ScaleAnimation shrinkAnimation = new ScaleAnimation(1.0f, 0.1f, 1f, 0.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        growAnimation.setDuration(500);
        shrinkAnimation.setDuration(200);

        ball4.setAnimation(shrinkAnimation);
        //shrinkAnimation.start();
        ball4.startAnimation(shrinkAnimation);

        shrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                frameLayouts.get(i[0]).setAnimation(growAnimation);
                frameLayouts.get(i[0]).animate().alpha(1).setDuration(500);
                frameLayouts.get(i[0]).startAnimation(growAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        growAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (i[0] > 0) {
                    i[0]--;
                } else {
                    i[0] = 3;
                    if(gotData == true)
                    {
                        Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                frameLayouts.get(i[0]).setAnimation(shrinkAnimation);
                frameLayouts.get(i[0]).animate().alpha(0).setDuration(200);
                frameLayouts.get(i[0]).startAnimation(shrinkAnimation);
                //shrinkAnimation.start();
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void getPermissions() {


        String[] PERMISSIONS = {Manifest.permission.INTERNET};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSION_ALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[6] == PackageManager.PERMISSION_GRANTED
                    && grantResults[8] == PackageManager.PERMISSION_GRANTED
                    && grantResults[9] == PackageManager.PERMISSION_GRANTED
                    ) {
            } else {
                getPermissions();
            }
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                } else {

                }
            }
        }
        return true;
    }

    private boolean checkWifi() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}

