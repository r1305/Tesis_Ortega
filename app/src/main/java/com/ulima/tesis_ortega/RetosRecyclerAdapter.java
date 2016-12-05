package com.ulima.tesis_ortega;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian on 30/09/2016.
 */

public class RetosRecyclerAdapter extends RecyclerView.Adapter<RetosRecyclerAdapter.ViewHolder> {

    List<JSONObject> list=new ArrayList<>();
    View.OnClickListener listener;
    Context c;

    public RetosRecyclerAdapter(List<JSONObject> list) {
        this.list = list;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c=parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retos,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject o=list.get(position);
        holder.act.setText(o.get("nombre").toString());
        holder.tiempo.setText(o.get("tiempo").toString());
        holder.itemView.setTag(o);
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }else {
            return list.size();

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView act,tiempo;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            act=(TextView)itemView.findViewById(R.id.item_act);
            tiempo=(TextView)itemView.findViewById(R.id.item_tiempo);
//            img=(ImageView)itemView.findViewById(R.id.item_img);

        }
    }
}
