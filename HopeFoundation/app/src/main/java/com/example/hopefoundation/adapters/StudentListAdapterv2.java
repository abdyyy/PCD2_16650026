package com.example.hopefoundation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hopefoundation.R;

import java.util.ArrayList;

/**
 * Created by I324201 on 10/30/2015.
 */
public class StudentListAdapterv2 extends RecyclerView.Adapter<StudentListAdapterv2.CustomViewHolder> {
    ArrayList<String> names;

    public StudentListAdapterv2(ArrayList<String> names) {
        this.names = names;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_single_item_v2, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        holder.tvStudentName.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tvStudentName;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvStudentName = (TextView) itemView.findViewById(R.id.tv_student_name);
        }
    }
}

