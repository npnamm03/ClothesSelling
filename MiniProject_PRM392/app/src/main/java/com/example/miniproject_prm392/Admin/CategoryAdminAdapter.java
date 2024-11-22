package com.example.miniproject_prm392.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniproject_prm392.Activities.ShowAllActivity;
import com.example.miniproject_prm392.Adapter.CategoryAdapter;
import com.example.miniproject_prm392.Models.CategoryModel;
import com.example.miniproject_prm392.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdminAdapter extends RecyclerView.Adapter<CategoryAdminAdapter.ViewHolder> {
    private Context context;
    private List<CategoryModel> list;
    private List<CategoryModel> filteredCategoryList;
    OnItemClickListener onItemClickListener;

    public CategoryAdminAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
        this.filteredCategoryList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(filteredCategoryList.get(position).getImg_url()).into(holder.catImg);
        holder.catName.setText(filteredCategoryList.get(position).getName());
        holder.catType.setText(filteredCategoryList.get(position).getType());

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(filteredCategoryList.get(position)));
    }

    public void filter(String query) {
        filteredCategoryList.clear();
        if (query.isEmpty()) {
            filteredCategoryList.addAll(list);
        } else {
            for (CategoryModel category : list) {
                if (category.getName().toLowerCase().trim().contains(query.toLowerCase().trim())) {
                    filteredCategoryList.add(category);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImg;
        TextView catName, catType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.cat_img);
            catName = itemView.findViewById(R.id.cat_name);
            catType = itemView.findViewById(R.id.cat_type);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(CategoryModel categoryModel);
    }

}
