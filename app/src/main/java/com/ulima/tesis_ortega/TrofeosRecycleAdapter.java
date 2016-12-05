package com.ulima.tesis_ortega;

import android.content.Context;
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
 * Created by Julian on 14/11/2016.
 */

public class TrofeosRecycleAdapter extends RecyclerView.Adapter<TrofeosRecycleAdapter.ViewHolder> {

    List<JSONObject> list=new ArrayList<>();
    Context c;

    public TrofeosRecycleAdapter(List<JSONObject> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c=parent.getContext();
        return new TrofeosRecycleAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trofeo,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject o=list.get(position);
        holder.nombre.setText(o.get("nombre").toString());
        if(o.get("nombre").toString().equals("ACTIVE TRAINING")){
            Picasso.with(c).load("http://res.cloudinary.com/actifit/image/upload/v1478614742/medal_d8nd38.png").into(holder.img);
        }else if(o.get("nombre").toString().equals("TRANSFORMATION")){
            Picasso.with(c).load("http://res.cloudinary.com/actifit/image/upload/v1478614732/award-symbol_zlrnd9.png").into(holder.img);
        }else if(o.get("nombre").toString().equals("MAXIMUM RESISTENCE")){
            Picasso.with(c).load("http://res.cloudinary.com/actifit/image/upload/v1478614830/trophy_d9fvdf.png").into(holder.img);
        }else{
            Picasso.with(c).load("http://res.cloudinary.com/actifit/image/upload/v1478614738/medal_1_xjoace.png").into(holder.img);
        }
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

        TextView nombre;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre=(TextView)itemView.findViewById(R.id.trofeo_label);
            img=(ImageView)itemView.findViewById(R.id.trofeo_img);

        }
    }
}
