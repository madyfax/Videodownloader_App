package social.video.downloader.app.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import social.video.downloader.app.R;
import social.video.downloader.app.utils.MyApp;


public class DownViewActivity extends AppCompatActivity implements  social.video.downloader.app.activity.DownLinkAdapter.MyClick {


    ImageView iv;
    RecyclerView rv;
    String path;


    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        } catch (MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_downview_activity);

        iv = findViewById(R.id.iv);
        rv = findViewById(R.id.rv);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            path = bundle.getString("path");
            Glide.with(this).load(path).into(iv);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        social.video.downloader.app.activity.DownLinkAdapter downLinkAdapter = new social.video.downloader.app.activity.DownLinkAdapter(MyApp.getMyDlink(), DownViewActivity.this, this);
        rv.setAdapter(downLinkAdapter);

        showAdWithDelay();

    }

    @Override
    public void getUrl(String url) {
        downManager(url);
        showAdWithDelay();
    }

    private void downManager(String durl) {

        try {
            Toast.makeText(this, "Download Start.....", Toast.LENGTH_SHORT).show();


            String strFileName = getFileNameFromURL(durl);

            if (!strFileName.contains(".")) {
                if (durl.contains("mp4")) {
                    strFileName=strFileName+".mp4";
                }
            }

            Uri source = Uri.parse(durl);
            // Make a new request pointing to the .apk url
            DownloadManager.Request request = new DownloadManager.Request(source);
            // appears the same in Notification bar while downloading
            request.setDescription("Download Start......");
            request.setTitle(strFileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            // save the file in the "Downloads" folder of SDCARD
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, strFileName);
            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

        } catch (Exception e) {
            Toast.makeText(DownViewActivity.this, "Again Try. Not valid link", Toast.LENGTH_SHORT).show();
        }


    }


    private void showAdWithDelay() {
        MyApp.loadFULLADS(((MyApp) getApplicationContext()).getCurrentActivity());
    }
}
