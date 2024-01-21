package social.video.downloader.app.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import social.video.downloader.app.Api.RestApi;
import social.video.downloader.app.R;
import social.video.downloader.app.utils.MyApp;


public class DownloaderActivity extends AppCompatActivity {

    EditText et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloader);


        getSupportActionBar().hide();
        et=findViewById(R.id.et);



    }

    public void download(View view) {
        String link = et.getText().toString().trim();

        if (link.isEmpty()) {
            Toast.makeText(DownloaderActivity.this, "Add Valid Link", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!link.contains("://")) {
            Toast.makeText(DownloaderActivity.this, "Add Valid Link!!!!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (link.toLowerCase().contains("youtube") || link.toLowerCase().contains("youtu.be")) {
            Toast.makeText(DownloaderActivity.this, "YouTube Not Support", Toast.LENGTH_SHORT).show();
            return;
        }
        if (link.toLowerCase().contains("soundcloud")) {
            Toast.makeText(DownloaderActivity.this, "Site Not Support", Toast.LENGTH_SHORT).show();
            return;
        }


        sendRequest(MyApp.decode(MyApp.GetALLDOWNLINK(), "HelloWorld@All")+link);

    }

    public void back(View view) {
        onBackPressed();

    }
    public void onBackPressed() {
        super.onBackPressed();
    }




    private void sendRequest(String url) {

        Loader loader = new Loader(this, false);
        loader.show();
        RestApi restApi = Global.initRetrofit(this);
        Call<ResponseBody> loginCall = restApi.login3(url);

        loginCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loader.dismiss();

                if (response.body() != null) {
                    try {

                        final ArrayList<String> downArrayList = new ArrayList<>();
                        final ArrayList<String> imgArrayList = new ArrayList<>();

                        String myr = response.body().string() + "";

                        if (myr.contains("not found")) {
                            Toast.makeText(DownloaderActivity.this, "Try Again Later!!!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("kamlesh", myr + "");
                            JSONObject jsonObject = new JSONObject(myr);

                            if (jsonObject.has("img") && !jsonObject.isNull("img")) {
                                String img = jsonObject.getString("img");
                                imgArrayList.add(img);
                            }

                            if (jsonObject.has("urls") && !jsonObject.isNull("urls")) {
                                JSONArray urls = jsonObject.getJSONArray("urls");
                                if (urls.length() > 0) {
                                    for (int i = 0; i < urls.length(); i++) {
                                        JSONObject jsonObject1 = urls.getJSONObject(i);
                                        String url = jsonObject1.getString("url");
                                        downArrayList.add(url);
                                    }
                                }
                            }

                            if (downArrayList.size() > 0) {
                                Toast.makeText(DownloaderActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                MyApp.setMyDlink(downArrayList);
                                if (imgArrayList.size() > 0) {
                                    startActivity(new Intent(DownloaderActivity.this, DownViewActivity.class).putExtra("path", imgArrayList.get(0) + ""));
                                } else {
                                    startActivity(new Intent(DownloaderActivity.this, DownViewActivity.class).putExtra("path", ""));

                                }
                                MyApp.loadFULLADS(((MyApp) getApplicationContext()).getCurrentActivity());


                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", "" + t);
                loader.dismiss();
                Toast.makeText(DownloaderActivity.this, "Something went wrong! Net Check", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pastdownloadlink(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            CharSequence textToPaste = clipboard.getPrimaryClip().getItemAt(0).getText();
            et.setText(textToPaste + "");
            Toast.makeText(this, "Paste Success", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "pastdownloadlink: "+textToPaste);


        } catch (Exception e) {
            return;
        }
    }

}
