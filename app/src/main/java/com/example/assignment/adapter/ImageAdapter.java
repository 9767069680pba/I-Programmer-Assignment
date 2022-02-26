package com.example.assignment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.R;
import com.example.assignment.model.ImageDatum;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    MutableLiveData<ImageDatum> imageDatumMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> imageId = new MutableLiveData<>();

    String REMOVE_BUTTON = "REMOVE";
    String CONVERT_BUTTON = "CONVERT";


    String androidLogo = "https://cdn.vox-cdn.com/thumbor/-_iz84sxjFk1L4JHRwhY6TCdu9o=/0x0:2040x1560/920x613/filters:focal(857x617:1183x943):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/65088839/Android_logo_stacked__RGB_.5.jpg";

    Context context;
    List<ImageDatum> imgList = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ImageDatum> imgList) {
        this.imgList = imgList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assign, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        ImageDatum imageDatum = imgList.get(position);

        Glide.with(context)
                .load(imageDatum.getUrl())
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_error_24)
                .into(holder.imgView);

        holder.btnComp.setOnClickListener(v -> {
            try {
                if (holder.btnComp.getText().equals(CONVERT_BUTTON)) {
                    ImageDatum idata = new ImageDatum();
                    idata.setId(imageDatum.getId());
                    idata.setUrl(imageDatum.getUrl());
                    idata.setAlbumId(imageDatum.getAlbumId());
                    idata.setTitle(imageDatum.getTitle());
                    idata.setThumbnailUrl(imageDatum.getThumbnailUrl());

                    imageDatumMutableLiveData.setValue(idata);
                    holder.btnComp.setText(R.string.remove_button);
                } else {
                    imageId.setValue(imageDatum.getId());
                    Log.d("###", "td ="+imageId.getValue());
                    holder.btnComp.setText(R.string.convert_button);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public MutableLiveData<ImageDatum> getImageDatumMutableLiveData() {
        return imageDatumMutableLiveData;
    }

    public MutableLiveData<Integer> getImageId() {
        return imageId;
    }

    @Override
    public int getItemCount() {
        Log.d("ADAPTER SIZE", "" + imgList.size());
        return imgList == null ? 0 : imgList.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        Button btnComp;

        public ImageHolder(@NonNull View iv) {
            super(iv);

            imgView = iv.findViewById(R.id.image);
            btnComp = iv.findViewById(R.id.compareRemove);
        }
    }
}
