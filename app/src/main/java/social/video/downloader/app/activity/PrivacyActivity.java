package social.video.downloader.app.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import social.video.downloader.app.R;


public class PrivacyActivity extends AppCompatActivity {

    private WebView webPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        webPrivacyPolicy = (WebView) findViewById(R.id.wvPrivacyPolicy);
        webPrivacyPolicy.loadUrl("https://sites.google.com/view/sani-apps/home");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}