package social.video.downloader.app.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.Toast;

import social.video.downloader.app.R;


@SuppressLint("WrongConstant")
public class A_Common {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    // Useing For Share an Apps
    public static void shareApp(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", context.getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "\nLet me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n");
            intent.setFlags(67108864);
            context.startActivity(Intent.createChooser(intent, "choose one"));
        } catch (Exception unused) {
        }
    }


    // Used For Rating an Apps
    public static void rateApp(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName()));
        try {
            intent.setFlags(67108864);
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())).setFlags(67108864));
        }
    }

    // Used For more Apps in The Console or in PAckages
    public static void moreapp(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(myAppLinkToMarket);
        } catch (Exception e) {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


}
