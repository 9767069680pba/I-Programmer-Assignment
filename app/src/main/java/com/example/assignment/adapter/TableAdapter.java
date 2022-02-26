package com.example.assignment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.model.ImageDatum;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {

    Context context;
    List<ImageDatum> imgList = new ArrayList<>();
    ImageDatum imgItem;
    Integer id;
    int curPos;

    public TableAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ImageDatum> imgList) {
        this.imgList = imgList;
        notifyDataSetChanged();
    }

    public void removeItem(Integer id) {
        this.id = id;
        try {
            for (ImageDatum imageDatum : imgList) {
                if (imageDatum.getId().equals(id)) {
                    imgList.remove(imageDatum);
                    notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLDataItem(ImageDatum imgItem) {
        this.imgItem = imgItem;
        imgList.add(imgItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assign_table, parent, false);
        return new TableHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, @SuppressLint("RecyclerView") int position) {
        curPos = position;
        ImageDatum imageDatum = imgList.get(position);
        Glide.with(context).load(imageDatum.getThumbnailUrl())
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_error_24)
                .into(holder.imgView);

        holder.tvId.setText(imageDatum.getId().toString());
        holder.tvUrl.setText(imageDatum.getUrl());
        holder.tvTitle.setText(imageDatum.getTitle());
    }

    @Override
    public int getItemCount() {
        return imgList == null ? 0 : imgList.size();
    }

    public class TableHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView tvId, tvUrl, tvTitle;

        public TableHolder(@NonNull View iv) {
            super(iv);

            imgView = iv.findViewById(R.id.image);
            tvId = iv.findViewById(R.id.tv_id);
            tvUrl = iv.findViewById(R.id.tv_url);
            tvTitle = iv.findViewById(R.id.tv_title);
        }
    }
}
