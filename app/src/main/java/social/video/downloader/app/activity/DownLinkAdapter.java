package social.video.downloader.app.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import social.video.downloader.app.R;


public class DownLinkAdapter extends RecyclerView.Adapter<DownLinkAdapter.Myh> {

    ArrayList<String> linkArrayList;
    Context context;
    MyClick myClick;

    public DownLinkAdapter(ArrayList<String> linkArrayList, Context context, MyClick myClick) {
        this.linkArrayList = linkArrayList;
        this.context = context;
        this.myClick = myClick;
    }

    @NonNull
    @Override
    public Myh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.link_down_link_item, parent, false);
        return new Myh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myh holder, int position) {

        final String link = linkArrayList.get(position);
        Bitmap favicon = null;

        int a = position + 1;
        holder.tv.setText("Save Link(Click Here) " + a);
        if (favicon == null) {
            holder.favicon.setImageDrawable(context.getResources().getDrawable(R.drawable.logo));
        } else {
            holder.favicon.setImageBitmap(favicon);
        }

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClick.getUrl(link);
            }
        });
    }

    @Override
    public int getItemCount() {
        return linkArrayList.size();
    }

    public interface MyClick {

        void getUrl(String url);
    }

    public class Myh extends RecyclerView.ViewHolder {
        ImageView favicon;
        TextView tv;

        public Myh(@NonNull View itemView) {
            super(itemView);

            favicon = itemView.findViewById(R.id.favicon);
            tv = itemView.findViewById(R.id.textView1);
        }
    }
}
