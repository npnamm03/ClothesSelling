package com.example.miniproject_prm392.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miniproject_prm392.Activities.ShowAllActivity;
import com.example.miniproject_prm392.Models.NewProductsModel;
import com.example.miniproject_prm392.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.ViewHolder> {
    private Context context;
    private List<NewProductsModel> list;
    private List<NewProductsModel> filteredProductList;
    OnItemClickListener onItemClickListener;

    public ProductAdminAdapter(Context context, List<NewProductsModel> list) {
        this.context = context;
        this.list = list;
        this.filteredProductList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ProductAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdminAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_products_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdminAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.prImg);
        holder.prName.setText(list.get(position).getName());
        holder.prDes.setText(list.get(position).getDescription());
        holder.prRating.setText(list.get(position).getRating());
        holder.prPrice.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(filteredProductList.get(position)));
    }

    public void filter(String query) {
        filteredProductList.clear();
        if (query.isEmpty()) {
            filteredProductList.addAll(list);
        } else {
            for (NewProductsModel category : list) {
                if (category.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProductList.add(category);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredProductList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView prImg;
        TextView prName, prDes, prPrice, prRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prImg = itemView.findViewById(R.id.pr_img);
            prName = itemView.findViewById(R.id.pr_name);
            prDes = itemView.findViewById(R.id.pr_des);
            prPrice = itemView.findViewById(R.id.pr_price);
            prRating = itemView.findViewById(R.id.pr_rating);
        }
    }

    public void setOnItemClickListener(ProductAdminAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(NewProductsModel newProductsModel);
    }
}

