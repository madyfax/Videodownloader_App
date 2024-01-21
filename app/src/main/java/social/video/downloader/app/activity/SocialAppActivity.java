package social.video.downloader.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import social.video.downloader.app.R;
import social.video.downloader.app.utils.MyApp;


public class SocialAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialappdowlnd);
        getSupportActionBar().hide();
        
    }


    public void more(View view) {

            startActivity(new Intent(((MyApp) getApplicationContext()).getCurrentActivity(), MoreAppActivity.class));

    }

    public void next(View view) {

            startActivity(new Intent(((MyApp) getApplicationContext()).getCurrentActivity(), DownloaderActivity.class));

    }

    public void back(View view) {
        onBackPressed();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
