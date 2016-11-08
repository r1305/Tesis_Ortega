package com.ulima.tesis_ortega;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian on 19/10/2016.
 */

public class RankingRecyclerAdapter extends RecyclerView.Adapter<RankingRecyclerAdapter.ViewHolder> {


    List<JSONObject> list = new ArrayList<>();
    Context c;

    public RankingRecyclerAdapter(List<JSONObject> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        return new RankingRecyclerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject o = list.get(position);
        holder.nombre.setText(o.get("nombre").toString());
        holder.puntos.setText(o.get("puntaje").toString());
        holder.itemView.setTag(o);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, puntos;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.item_ranking_nombre);
            puntos = (TextView) itemView.findViewById(R.id.item_ranking_puntos);
            img = (ImageView) itemView.findViewById(R.id.item_ranking_img);

        }
    }
}
