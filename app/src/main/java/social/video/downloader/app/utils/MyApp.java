package social.video.downloader.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import social.video.downloader.app.BuildConfig;
import social.video.downloader.app.R;


@SuppressLint("CommitPrefEdits")
public class MyApp extends Application {

    public static String ad_native = "ca-app-pub-3940256099942544/2247696110";
    public static String adOpen = "ca-app-pub-3940256099942544/3419835294";
    public static NativeAd BnativeAd;
    public static InterstitialAd interstitialAd;

    public static SQLiteDatabase db;
    // Load Native Ad //
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd maxAd = null;
    // Load interstial Ad //
    public static MaxInterstitialAd maxInterstitialAd;
    static SharedPreferences preferences;
    static SharedPreferences.Editor prefEditor;
    private static String admBanner = "ca-app-pub-3940256099942544/6300978111";
    private static AppOpenManager appOpenManager;
    private static AdManagerInterstitialAd mInterstitialAd;
    private static String AD_MANAGER_AD_UNIT_ID = "/6499/example/native";
    private static String AD_MANAGER_AD_UNIT_ID_INTER = "/6499/example/interstitial";
    private static AdView mBannerAd;
    private Activity mCurrentActivity = null;



    public static ArrayList<String> getMyDlink() {

        Gson gson = new Gson();
        String my = preferences.getString("myd", "");
        ArrayList<String> listItems = gson.fromJson(my, new TypeToken<ArrayList<String>>() {
        }.getType());

        return listItems;
    }

    public static void setMyDlink(ArrayList<String> myList) {
        Gson gson = new Gson();
        String list = gson.toJson(myList);
        prefEditor.putString("myd", list).commit();
    }

    public static void SetUrl(String Url) {
        prefEditor.putString("url", Url).commit();
    }

    public static String GetUrl() {return preferences.getString("url", "");
    }

    public static void SetAge(String Age) {prefEditor.putString("Age", Age).commit();}

    public static String GetAge() {
        return preferences.getString("Age", "");
    }

    public static void SetUvalid(String Uvalid) {
        prefEditor.putString("Uvalid", Uvalid).commit();
    }

    public static String GetUvalid() {
        return preferences.getString("Uvalid", "");
    }

    public static void SetName(String Name) {
        prefEditor.putString("Name", Name).commit();
    }

    public static String GetName() {
        return preferences.getString("Name", "No Name");
    }

    public static void SetMobNo(String MobNo) {
        prefEditor.putString("MobNo", MobNo).commit();
    }

    public static String GetMobNo() {
        return preferences.getString("MobNo", "");
    }

    public static void SetLOGIN(String LOGIN) {
        prefEditor.putString("LOGIN", LOGIN).commit();
    }

    public static String GetLOGIN() {
        return preferences.getString("LOGIN", "");
    }

    public static void loadAdxBigBanner(Activity context, ViewGroup bannerAd) {
        mBannerAd = new AdView(context);
        mBannerAd.setAdSize(AdSize.LARGE_BANNER);

        if (BuildConfig.DEBUG) {
            mBannerAd.setAdUnitId(admBanner);
        } else {
            mBannerAd.setAdUnitId(MyApp.GetBAD());
        }


        ViewGroup adContainer = bannerAd;
        adContainer.addView(mBannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAd.loadAd(adRequest);

        mBannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.

                bannerAd.removeAllViews();
                View adView =
                        context.getLayoutInflater().inflate(R.layout.ad_startap_banner_ad_layout, null);
                bannerAd.addView(adView);
                adContainer.addView(mBannerAd);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    public static void SetSiteLink(String SiteLink) {
        prefEditor.putString("SiteLink", SiteLink).commit();
    }

    public static String GetSiteLink() {
        return preferences.getString("SiteLink", "");
    }

    public static void SetSiteName(String SiteName) {
        prefEditor.putString("SiteName", SiteName).commit();
    }

    public static String GetSiteName() {
        return preferences.getString("SiteName", "https://video.appinstallearn.co.in/");
    }

    public static void SetPROXY_HOST(String PROXY_HOST) {
        prefEditor.putString("PROXY_HOST", PROXY_HOST).commit();
    }

    public static String GetPROXY_HOST() {
        return preferences.getString("PROXY_HOST", "192.163.206.200");
    }

    public static void SetQueryS(String QueryS) {
        prefEditor.putString("QueryS", QueryS).commit();
    }

    public static String GetQueryS() {
        return preferences.getString("QueryS", "");
    }

    public static void SetUserID(String UserID) {
        prefEditor.putString("UserID", UserID).commit();
    }

    public static String GetUserID() {
        return preferences.getString("UserID", "");
    }

    public static void SetToken(String Token) {
        prefEditor.putString("Token", Token).commit();
    }

    public static String GetToken() {
        return preferences.getString("Token", "");
    }

    public static void SetLoadADs(int count) {
        prefEditor.putInt("loadcount", count).commit();
    }

    public static int GetLoadADs() {
        return preferences.getInt("loadcount", 0);
    }

    public static void SetPORT(int PORT) {
        prefEditor.putInt("PORT", PORT).commit();
    }

    public static int GetPORT() {
        return preferences.getInt("PORT", 3128);
    }

    public static void SetOAPPLoadADs(int count) {
        prefEditor.putInt("OAPPLoadADs", count).commit();
    }

    public static int GetOAPPLoadADs() {
        return preferences.getInt("OAPPLoadADs", 0);
    }

    public static void SetCount(int count) {
        prefEditor.putInt("loopcount", count).commit();
    }

//    public static ArrayList<String> getMyDlink() {
//
//        Gson gson = new Gson();
//        String my = preferences.getString("myd", "");
//        ArrayList<String> listItems = gson.fromJson(my, new TypeToken<ArrayList<String>>() {
//        }.getType());
//
//        return listItems;
//    }
//
//    public static void setMyDlink(ArrayList<String> myList) {
//        Gson gson = new Gson();
//        String list = gson.toJson(myList);
//        prefEditor.putString("myd", list).commit();
//    }

    public static int GetCount() {
        return preferences.getInt("loopcount", 0);
    }

    public static void SetTADCount(int count) {
        prefEditor.putInt("tadscount", count).commit();
    }

    public static int GetTADCount() {
        return preferences.getInt("tadscount", 1);
    }

    public static boolean setLoadAds(Context context) {
        int count = MyApp.GetLoadADs();
        Random r = new Random();
//        Log.d("TAG", "setLoadAds: "+count);
//        Log.d("TAG", "setLoadAds: "+A_PreferenceManager.GetCount());

        if (count == MyApp.GetCount()) {
            count = 0;
            MyApp.SetLoadADs(count);

            return true;
        } else {
            count++;
            MyApp.SetLoadADs(count);
            return false;
        }
    }

    public static boolean getinvisibleads(Context context) {
        int count = MyApp.GetTADCount();
        Log.d("TAG", "onCreate: TADS " + count);
        Log.d("TAG", "onCreate: TADS " + MyApp.GetTADS());
        if (count == Integer.parseInt(MyApp.GetTADS())) {
            count = 1;
            MyApp.SetTADCount(count);
            return true;
        } else {
            count++;
            MyApp.SetTADCount(count);
            return false;
        }
    }

    public static void SetALLDOWNLINK(String ALLDOWNLINK) {
        prefEditor.putString("ALLDOWNLINK", ALLDOWNLINK).commit();
    }

    public static String GetALLDOWNLINK() {
        return preferences.getString("ALLDOWNLINK", "");
    }

    public static void SetTADS(String TADS) {
        prefEditor.putString("TADS", TADS).commit();
    }

    public static String GetTADS() {
        return preferences.getString("TADS", "");
    }

    public static void SetTADSTATUS(String TADSTATUS) {
        prefEditor.putString("TADSTATUS", TADSTATUS).commit();
    }

    public static String GetTADSTATUS() {
        return preferences.getString("TADSTATUS", "");
    }

    public static void SetFIAD(String FIAD) {
        prefEditor.putString("FIAD", FIAD).commit();
    }

    public static String GetFIAD() {
        return preferences.getString("FIAD", "");
    }

    public static void SetLovinI(String LovinI) {
        prefEditor.putString("LovinI", LovinI).commit();
    }

    public static String GetLovinI() {
        return preferences.getString("LovinI", "");
    }

    public static void SetLSDK(String LSDK) {
        prefEditor.putString("LSDK", LSDK).commit();
    }

    public static String GetLSDK() {
        return preferences.getString("LSDK", "");
    }

    public static void SetLovinB(String LovinB) {
        prefEditor.putString("LovinB", LovinB).commit();
    }

    public static String GetLovinB() {
        return preferences.getString("LovinB", "");
    }

    public static void SetQAD1(String QAD1) {
        prefEditor.putString("QAD1", QAD1).commit();
    }

    public static String GetQAD1() {
        return preferences.getString("QAD1", "");
    }

    public static void SetMyOwnBannerPhoto(String MyOwnBannerPhoto) {
        prefEditor.putString("MyOwnBannerPhoto", MyOwnBannerPhoto).commit();
    }

    public static String GetMyOwnBannerPhoto() {
        return preferences.getString("MyOwnBannerPhoto", "");
    }

    public static void SetMyOwnNativeAdsPhoto(String MyOwnNativeAdsPhoto) {
        prefEditor.putString("MyOwnNativeAdsPhoto", MyOwnNativeAdsPhoto).commit();
    }

    public static String GetMyOwnNativeAdsPhoto() {
        return preferences.getString("MyOwnNativeAdsPhoto", "");
    }

    public static void SetMyOwnNativeAdsClickLink(String MyOwnNativeAdsClickLink) {
        prefEditor.putString("MyOwnNativeAdsClickLink", MyOwnNativeAdsClickLink).commit();
    }

    public static String GetMyOwnNativeAdsClickLink() {
        return preferences.getString("MyOwnNativeAdsClickLink", "");
    }

    public static void SetMyOwnBannerAdsClickLink(String MyOwnBannerAdsClickLink) {
        prefEditor.putString("MyOwnBannerAdsClickLink", MyOwnBannerAdsClickLink).commit();
    }

    public static String GetMyOwnBannerAdsClickLink() {
        return preferences.getString("MyOwnBannerAdsClickLink", "");
    }

    public static void SetShowNativeAdType(String ShowNativeAdType) {
        prefEditor.putString("ShowNativeAdType", ShowNativeAdType).commit();
    }

    public static String GetShowNativeAdType() {
        return preferences.getString("ShowNativeAdType", "");
    }

    public static void SetShowBannerAdType(String ShowBannerAdType) {
        prefEditor.putString("ShowBannerAdType", ShowBannerAdType).commit();
    }

    public static String GetShowBannerAdType() {
        return preferences.getString("ShowBannerAdType", "");
    }

    public static void SetFullAdsType(String FullAdsType) {
        prefEditor.putString("FullAdsType", FullAdsType).commit();
    }

    public static String GetFullAdsType() {
        return preferences.getString("FullAdsType", "");
    }

    public static void SetQAD2(String QAD2) {
        prefEditor.putString("QAD2", QAD2).commit();
    }

    public static String GetQAD2() {
        return preferences.getString("QAD2", "");
    }

    public static void SetQAD3(String QAD3) {
        prefEditor.putString("QAD3", QAD3).commit();
    }

    public static String GetQAD3() {
        return preferences.getString("QAD3", "");
    }

    public static void SetFBAD(String FBAD) {
        prefEditor.putString("FBAD", FBAD).commit();
    }

    public static String GetFBAD() {
        return preferences.getString("FBAD", "");
    }

    public static void SetFNAD(String FNAD) {
        prefEditor.putString("FNAD", FNAD).commit();
    }

    public static String GetFNAD() {
        return preferences.getString("FNAD", "");
    }

    public static void SetQUERKA(String QUERKA) {
        prefEditor.putString("QUERKA", QUERKA).commit();
    }

    public static String GetQUERKA() {
        return preferences.getString("QUERKA", "http://483.live.qureka.com");
    }

    public static void SetIAD(String IAD) {
        prefEditor.putString("IAD", IAD).commit();
    }

    public static String GetIAD() {
        return preferences.getString("IAD", "");
    }

    public static void SetNAD(String NAD) {
        prefEditor.putString("NAD", NAD).commit();
    }

    public static String GetNAD() {
        return preferences.getString("NAD", "");
    }

    public static void SetBAD(String BAD) {
        prefEditor.putString("BAD", BAD).commit();
    }

    public static String GetBAD() {
        return preferences.getString("BAD", "");
    }

    public static void SetBADS(String BADS) {
        prefEditor.putString("BADS", BADS).commit();
    }

    public static String GetBADS() {
        return preferences.getString("BADS", "");
    }

    public static void SetOAD(String OAD) {
        prefEditor.putString("OAD", OAD).commit();
    }

    public static String GetOAD() {
        return preferences.getString("OAD", "");
    }

    public static String encode(String s, String key) {
        return new String(Base64.encode(xorWithKey(s.getBytes(), key.getBytes()), Base64.DEFAULT));
    }

    public static String decode(String s, String key) {
        return new String(xorWithKey(base64Decode(s), key.getBytes()));
    }

    private static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;
    }

    private static byte[] base64Decode(String s) {
        return Base64.decode(s, Base64.DEFAULT);
    }

    public static void googleadsinit(Context context) {

//        private InterstitialAd mInterstitialAd;

        String adUnitId = "";
        if (BuildConfig.DEBUG) {
            adUnitId = AD_MANAGER_AD_UNIT_ID_INTER;
        } else {
            adUnitId = MyApp.GetIAD();

        }

        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        AdManagerInterstitialAd.load(
                context,
                adUnitId,
                adRequest,
                new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d("TAG", "The ad was dismissed.");
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        Log.d("TAG", "The ad failed to show.");
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;

                    }
                });

        setfbinter(context);

    }

    public static void LoadInterstitial(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
            Log.d("TAG", "setLoadAds: yes");
        } else {
            Log.d("TAG", "setLoadAds: no");
            showfb(activity);
        }
    }

    public static void admobBannerCall(Activity acitivty, ViewGroup adview) {

        AdView adView = new AdView(acitivty);
        if (BuildConfig.DEBUG) {

            adView.setAdUnitId(admBanner);
        } else {
            if (MyApp.GetBAD().equals("")) {
                return;
            }
            adView.setAdUnitId(MyApp.GetBAD());
        }
        adView.setAdSize(AdSize.BANNER);
        AdRequest.Builder builder = new AdRequest.Builder();
        adView.loadAd(builder.build());
        adview.addView(adView);

    }

    public static void loadAdxBanner(Activity activity, ViewGroup viewGroup) {

        viewGroup.addView(setAdmobBanner(activity, new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.w("msg", "AM Banner Failed");

                viewGroup.removeAllViews();
                View adView =
                        activity.getLayoutInflater().inflate(R.layout.ad_startap_banner_ad_layout, null);
                viewGroup.addView(adView);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.w("msg", "AM Banner Impression");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }
        }));

    }

    public static AdView setAdmobBanner(Activity activity, AdListener adListener) {

        String googlebanner = "";
        if (BuildConfig.DEBUG) {
            googlebanner = admBanner;
        } else {
            googlebanner = MyApp.GetBAD();
        }

        AdView adView = new AdView(activity);
        adView.setAdUnitId(googlebanner != null ? googlebanner : "");
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        adView.loadAd(new AdRequest.Builder().build());
        adView.setAdListener(adListener);

        return adView;
    }

    public static AdSize getAdSize(Activity activity) {
        Display display = (activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static void admobMediumBannerCall(Activity acitivty, LinearLayout linerlayout) {

        AdView adView = new AdView(acitivty);
        adView.setAdUnitId(admBanner);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        AdRequest.Builder builder = new AdRequest.Builder();
        adView.loadAd(builder.build());
        linerlayout.addView(adView);
    }

    public static void showIntestitialAds(final Activity context) {
        if (!MyApp.setLoadAds(context)) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
                MyApp.LoadInterstitial(context);
            }
        }, 200); // Show the ad after s sec
    }

    public static void CustomeBannerpopulateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }

//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }

//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.

    }

    public static void loadAdxBannerNative(Activity activity, ViewGroup viewGroup) {

        LoadAdmobNativeAd2(activity, viewGroup, new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.w("msg", "AM Native Failed");
                viewGroup.removeAllViews();
                View adView =
                        activity.getLayoutInflater().inflate(R.layout.ad_startap_banner_ad_layout, null);
                viewGroup.addView(adView);

            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.w("msg", "AM Native Impression");
            }
        });

    }

    public static void LoadAdmobNativeAd2(final Activity activity, ViewGroup container, AdListener adListener) {


        String google = "";

        if (BuildConfig.DEBUG) {
            google = AD_MANAGER_AD_UNIT_ID;
        } else {
            google = MyApp.GetNAD();
        }
        AdLoader.Builder builder = new AdLoader.Builder(activity, google != null ? google : "");
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                adListener.onAdLoaded();
                container.setBackground(null);
                container.removeAllViews();
                LayoutInflater inflater = LayoutInflater.from(activity);
                NativeAdView adView = (NativeAdView) inflater.inflate(R.layout.ad_unified_banner, null);
                populateUnifiedNativeAdView2(nativeAd, adView);
                container.removeAllViews();
                container.addView(adView);
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adListener.onAdFailedToLoad(loadAdError);

                container.removeAllViews();
                View adView =
                        activity.getLayoutInflater().inflate(R.layout.ad_startap_banner_ad_layout, null);
                container.addView(adView);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                adListener.onAdImpression();
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private static void populateUnifiedNativeAdView2(NativeAd nativeAd, NativeAdView adView) {


        adView.setMediaView(adView.findViewById(R.id.ad_media));


        adView.setHeadlineView(adView.findViewById(R.id.ad_title));
        adView.setBodyView(adView.findViewById(R.id.ad_description));
        adView.setCallToActionView(adView.findViewById(R.id.btnApply));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());


        NativeAd.Image icon = nativeAd.getIcon();


        adView.setNativeAd(nativeAd);

    }

    public static void CustomrBannerAd(final Activity activity, final ViewGroup frameLayout) {

        AdLoader.Builder builder;

        if (BuildConfig.DEBUG) {

            builder = new AdLoader.Builder(activity, ad_native);
        } else {
            if (MyApp.GetNAD().equals("")) {
                return;
            }
            builder = new AdLoader.Builder(activity, MyApp.GetNAD());

        }


        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = activity.isDestroyed();
                        }
                        if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (BnativeAd != null) {
                            BnativeAd.destroy();
                        }
                        BnativeAd = nativeAd;
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified_banner, null);
                        CustomeBannerpopulateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });


        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        frameLayout.removeAllViews();
                                        View adView =
                                                activity.getLayoutInflater().inflate(R.layout.ad_startap_banner_ad_layout, null);
                                        frameLayout.addView(adView);
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdManagerAdRequest.Builder().build());

    }

    public static void loadadxNative(final Activity activity, final ViewGroup frameLayout) {

        AdLoader.Builder builder;

        if (BuildConfig.DEBUG) {

            builder = new AdLoader.Builder(activity, AD_MANAGER_AD_UNIT_ID);
        } else {
            if (MyApp.GetNAD().equals("")) {
                return;
            }
            builder = new AdLoader.Builder(activity, MyApp.GetNAD());

        }


        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = activity.isDestroyed();
                        }
                        if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (BnativeAd != null) {
                            BnativeAd.destroy();
                        }
                        BnativeAd = nativeAd;
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified_big, null);
                        CustomeBannerpopulateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);

                    }


                });


        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        String error =
                                                String.format(
                                                        "domain: %s, code: %d, message: %s",
                                                        loadAdError.getDomain(),
                                                        loadAdError.getCode(),
                                                        loadAdError.getMessage());

                                        frameLayout.removeAllViews();
                                        View adView =
                                                activity.getLayoutInflater().inflate(R.layout.ad_layout, null);
                                        frameLayout.addView(adView);
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdManagerAdRequest.Builder().build());

    }

    public static void openWebPage(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void loadmyownbanner(Activity activity, ViewGroup viewGroup) {
        View adView =
                activity.getLayoutInflater().inflate(R.layout.ad_banner_my_own, null);

        ImageView imageView = adView.findViewById(R.id.iv);
        Glide.with(activity).load(MyApp.GetMyOwnBannerPhoto()).into(imageView);
        viewGroup.addView(adView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    openWebPage(activity, MyApp.GetMyOwnBannerAdsClickLink());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void loadmyownnative(Activity activity, ViewGroup viewGroup) {
        View adView =
                activity.getLayoutInflater().inflate(R.layout.ad_native_my_own, null);

        ImageView imageView = adView.findViewById(R.id.iv);
        Glide.with(activity).load(MyApp.GetMyOwnNativeAdsPhoto()).into(imageView);
        viewGroup.addView(adView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    openWebPage(activity, MyApp.GetMyOwnNativeAdsClickLink());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void setqurekaads(Context context, ImageView qad1, ImageView qad2, ImageView qad3) {

        Glide.with(context).asGif().load(MyApp.GetQAD1()).into(qad1);
        Glide.with(context).asGif().load(MyApp.GetQAD2()).into(qad2);
        Glide.with(context).asGif().load(MyApp.GetQAD3()).into(qad3);

        Log.d("TAG", "setqurekaads: ");
        qad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    openWebPage(context, MyApp.GetQUERKA());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        qad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    openWebPage(context, MyApp.GetQUERKA());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        qad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    openWebPage(context, MyApp.GetQUERKA());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public static void setfbbannerads(Context context, ViewGroup adContainer) {


        String FBBANNERADS = "";

        if (BuildConfig.DEBUG) {

            FBBANNERADS = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
        } else {
            FBBANNERADS = MyApp.GetFBAD();

        }

        com.facebook.ads.AdView fbadView = new com.facebook.ads.AdView(context, FBBANNERADS, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        adContainer.addView(fbadView);
        fbadView.loadAd();


    }

    public static void setfbinter(Context context) {

        if (BuildConfig.DEBUG) {
            AdSettings.addTestDevice("90f5a51a-a1e9-489b-a526-2db891d10238");
        }

        String FBINTERADS = "";

        if (BuildConfig.DEBUG) {

            FBINTERADS = "YOUR_PLACEMENT_ID";
        } else {
            FBINTERADS = MyApp.GetFIAD();

        }

        interstitialAd = new InterstitialAd(context, FBINTERADS);
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("TAG", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("TAG", "Interstitial ad dismissed.");
            }


            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("TAG", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("TAG", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("TAG", "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public static void showfb(Activity activity) {
        if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
//            LoadInterstitial(activity);
            Log.d("TAG", "showfb: no ");
            StartAppAd.showAd(activity);
            return;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if (interstitialAd.isAdInvalidated()) {
//            LoadInterstitial(activity);
            Log.d("TAG", "showfb: no ");
            StartAppAd.showAd(activity);
            return;
        }
        // Show the ad
        interstitialAd.show();
    }

    public static void showfbDirect(Activity activity) {

        Log.d("TAG", "showfbDirect: ");

        if (!MyApp.setLoadAds(activity)) {
            Log.d("TAG", "showfbDirect: return");
            return;
        }
        if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
//            LoadInterstitial(activity);
            Log.d("TAG", "showfb: no ");
            StartAppAd.showAd(activity);
            return;
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if (interstitialAd.isAdInvalidated()) {
//            LoadInterstitial(activity);
            Log.d("TAG", "showfb: no ");
            StartAppAd.showAd(activity);
            return;
        }
        // Show the ad
        interstitialAd.show();
    }

    public static void loadNATIVEMAIN(Activity activity, ViewGroup viewGroup) {

        if (MyApp.GetLOGIN().equalsIgnoreCase("f")) {
            return;
        }


        if (MyApp.GetShowNativeAdType().equalsIgnoreCase("1")) {
            loadadxNative(activity, viewGroup);
        }
        if (MyApp.GetShowNativeAdType().equalsIgnoreCase("2")) {
            loadNativeAdMax(activity, viewGroup);
        }
        if (MyApp.GetShowNativeAdType().equalsIgnoreCase("3")) {
            fbnativeadshow(activity, viewGroup);
        }
        if (MyApp.GetShowNativeAdType().equalsIgnoreCase("4")) {
            loadmyownnative(activity, viewGroup);
        }

    }

    public static void loadBANNERMAIN(Activity activity, ViewGroup viewGroup) {

        if (MyApp.GetLOGIN().equalsIgnoreCase("f")) {
            return;
        }

//        1=adx banner 2=adx native banner 3=adx big banner 4=fb banner

        if (MyApp.GetShowBannerAdType().equalsIgnoreCase("1")) {
            loadAdxBanner(activity, viewGroup);
        }
        if (MyApp.GetShowBannerAdType().equalsIgnoreCase("2")) {
            loadAdxBannerNative(activity, viewGroup);
        }
        if (MyApp.GetShowBannerAdType().equalsIgnoreCase("3")) {
            loadAdxBigBanner(activity, viewGroup);
        }
        if (MyApp.GetShowBannerAdType().equalsIgnoreCase("4")) {
            setfbbannerads(activity, viewGroup);
        }
        if (MyApp.GetShowBannerAdType().equalsIgnoreCase("5")) {
            loadmyownbanner(activity, viewGroup);
        }


    }

    public static void loadFULLADS(Activity activity) {

        if (MyApp.GetLOGIN().equalsIgnoreCase("f")) {
            return;
        }
        if (MyApp.GetFullAdsType().equalsIgnoreCase("1")) {
            showIntestitialAds(activity);
        }
        if (MyApp.GetFullAdsType().equalsIgnoreCase("2")) {
            showfbDirect(activity);
        }
        if (MyApp.GetFullAdsType().equalsIgnoreCase("3")) {
            loadInterstialAdMax(activity);
        }

    }

    public static void loadNativeAdMax(Activity activity, ViewGroup viewGroup) {
        String adId = MyApp.GetLovinB();
        nativeAdLoader = new MaxNativeAdLoader(adId, activity);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (maxAd != null) {
                    nativeAdLoader.destroy(maxAd);
                }

                // Save ad for cleanup.
                maxAd = ad;

                // Add ad view to view.
                viewGroup.removeAllViews();
                viewGroup.addView(nativeAdView);
                viewGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                // We recommend retrying with exponentially higher delays up to a maximum delay
                viewGroup.removeAllViews();
                View adView =
                        activity.getLayoutInflater().inflate(R.layout.ad_layout, null);
                viewGroup.addView(adView);
            }


        });

        nativeAdLoader.loadAd();
    }

    public static void loadInterstialAdMax(Activity activity) {

        if (!MyApp.setLoadAds(activity)) {
            return;
        }

        String adId = MyApp.GetLovinI();
        maxInterstitialAd = new MaxInterstitialAd(adId, activity);
        MaxAdListener listener = new MaxAdListener() {

            @Override
            public void onAdLoaded(MaxAd ad) {

                Log.d("InterstialAdTAG", "On Ad Loaded");
                maxInterstitialAd.showAd();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("InterstialAdTAG", "On Ad Displayed");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("InterstialAdTAG", "On Ad Hidden");

            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("InterstialAdTAG", "On Ad Clicked");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("InterstialAdTAG", "On Ad Failed");


            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("InterstialAdTAG", "On Ad Display Failed");


            }
        };
        maxInterstitialAd.setListener(listener);
        maxInterstitialAd.loadAd();
    }

    public static void fbnativeadshow(Activity activity, ViewGroup viewGroup) {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        String fbnativeid = "";
        if (BuildConfig.DEBUG) {
            fbnativeid = "VID_HD_9_16_39S_APP_INSTALL#{your-placement-id}";
        } else {
            fbnativeid = MyApp.GetFNAD();
        }

        com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(activity, fbnativeid);

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.d("fb", "onError: " + adError.getErrorMessage());

                viewGroup.removeAllViews();
                View adView =
                        activity.getLayoutInflater().inflate(R.layout.ad_layout, null);
                viewGroup.addView(adView);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd, viewGroup, activity);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    public static void inflateAd(com.facebook.ads.NativeAd nativeAd, ViewGroup main_ad_view, Activity activity) {


        LayoutInflater main_inflater = LayoutInflater.from(activity);

        ViewGroup fb_ad_view = (ViewGroup) main_inflater.inflate(R.layout.ad_facebook_native, null);
        main_ad_view.addView(fb_ad_view);

        nativeAd.unregisterView();
        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = main_ad_view.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        ViewGroup adView = (ViewGroup) inflater.inflate(R.layout.ad_facebook_native_contanier, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        com.facebook.ads.MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("news", MODE_PRIVATE);
        prefEditor = preferences.edit();
        setupActivityListener();
        db = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        db.execSQL("create table if not exists ads(data text)");
        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                    }
                });

        appOpenManager = new AppOpenManager(this);
        googleadsinit(this);
        if (BuildConfig.DEBUG) {
            AdSettings.addTestDevice("59e58e9f-a4bb-4d89-a5f9-cb32baf33ddd");
        }
    }

    public void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mCurrentActivity = activity;
                MyApp.googleadsinit(((MyApp) getApplicationContext()).getCurrentActivity());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                clearReferences();
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                clearReferences();
            }
        });
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    private void clearReferences() {
        Activity currActivity = getCurrentActivity();
        if (this.equals(currActivity))
            setCurrentActivity(null);
    }

}