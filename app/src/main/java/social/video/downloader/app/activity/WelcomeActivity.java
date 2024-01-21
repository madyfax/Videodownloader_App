package social.video.downloader.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import social.video.downloader.app.R;
import social.video.downloader.app.utils.A_Common;
import social.video.downloader.app.utils.MyApp;


public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        new AppOpenManager((MyApp) getApplicationContext()).showAdIfAvailable();
        
    }

    public void back(View view) {
       onBackPressed();
    }


    public void start(View view) {
        if (MyApp.GetFullAdsType().equalsIgnoreCase("4")) {
            AdsDialog adsDialog = new AdsDialog(((MyApp) getApplicationContext()).getCurrentActivity(), false, SocialAppActivity.class);
            adsDialog.show();
        } else {
            MyApp.loadFULLADS(((MyApp) getApplicationContext()).getCurrentActivity());
            startActivity(new Intent(((MyApp) getApplicationContext()).getCurrentActivity(), SocialAppActivity.class));
        }
    }

    public void share(View view) {
        A_Common.shareApp(WelcomeActivity.this);
    }

    public void rate(View view) {
        A_Common.rateApp(WelcomeActivity.this);
    }

    public void privacy(View view) {
        startActivity(new Intent(WelcomeActivity.this,PrivacyActivity.class));
    }

    public void more(View view) {
        A_Common.moreapp(WelcomeActivity.this);
    }
}
