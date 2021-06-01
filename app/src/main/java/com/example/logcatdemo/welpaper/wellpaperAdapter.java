package com.example.logcatdemo.welpaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class wellpaperAdapter extends RecyclerView.Adapter<wellpaperAdapter.WellpaperViewHolder> {

    private Context context;
    private List<wellpaperModel> wellpaperModelList;

    public wellpaperAdapter(Context context, List<wellpaperModel> wellpaperModelList) {
        this.context = context;
        this.wellpaperModelList = wellpaperModelList;
    }


    @NonNull
    @Override
    public WellpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.image_wellpaper,parent,false);
        return new WellpaperViewHolder (view);
    }


    @Override
    public void onBindViewHolder(@NonNull WellpaperViewHolder holder, final int position) {
        Glide.with (context).load (wellpaperModelList.get (position).getMediumUrl ()).into (holder.imageView);
        holder.imageView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                context.startActivity (new Intent (context,fullscreenItems.class)
                .putExtra ("original",wellpaperModelList.get (position).getOriginalUrl ()));


            }
        });

    }

    @Override
    public int getItemCount() {
        return wellpaperModelList.size ();
    }

    static class WellpaperViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public WellpaperViewHolder(@NonNull View itemView) {
            super (itemView);
            imageView = itemView.findViewById (R.id.image);
        }
    }

}
