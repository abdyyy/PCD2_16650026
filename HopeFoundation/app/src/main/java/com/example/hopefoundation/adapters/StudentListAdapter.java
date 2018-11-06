package com.example.hopefoundation.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopefoundation.R;
import com.example.hopefoundation.activities.StudentDetailsActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by I324201 on 10/30/2015.
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.CustomViewHolder> {
    private final ArrayList<String> userNames;
    private final DisplayImageOptions options;
    ArrayList<String> names, addresses;
    ImageLoader imageLoader;
    Context context;

    public StudentListAdapter(ArrayList<String> names, ArrayList<String> userNames, Context context, ArrayList<String> addresses) {
        this.names = names;
        this.userNames = userNames;
        imageLoader = ImageLoader.getInstance();
        this.context = context;
        this.addresses = addresses;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public StudentListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_single_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentListAdapter.CustomViewHolder holder, final int position) {
        holder.tvStudentName.setText(names.get(position));
        holder.tvStudentLocation.setText(addresses.get(position));
        ImageSize targetSize = new ImageSize(80, 50);
        imageLoader.loadImage("http://10.207.114.12:3000/images/" + userNames.get(position) + ".jpg", targetSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.ivUserImage.setImageBitmap(loadedImage);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentDetailsActivity.class);
                intent.putExtra("username", userNames.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tvStudentName, tvStudentLocation;
        ImageView ivUserImage;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvStudentName = (TextView) itemView.findViewById(R.id.tv_student_name);
            ivUserImage = (ImageView) itemView.findViewById(R.id.iv_user_image);
            tvStudentLocation = (TextView) itemView.findViewById(R.id.tv_student_location);
        }
    }
}
