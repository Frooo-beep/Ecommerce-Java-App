package com.frostdev.ecommerce.adapter;

import android.annotation.SuppressLint;
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
import com.frostdev.ecommerce.R;
import com.frostdev.ecommerce.activities.DetailedActivity;
import com.frostdev.ecommerce.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder>{

    private Context context;
    private List< NewProductsModel > newProductsModelList;

    public NewProductsAdapter(Context context, List< NewProductsModel> newProductsModelsList) {
        this.context = context;
        this.newProductsModelList = newProductsModelsList;
    }

    @NonNull
    @Override
    public NewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(newProductsModelList.get(position).getImg_url()).into(holder.newImg);
        holder.newName.setText(newProductsModelList.get(position).getName());
        holder.newPrice.setText(String.valueOf(newProductsModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", newProductsModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
         return newProductsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newImg;
        TextView newName, newPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice = itemView.findViewById(R.id.new_price);
        }
    }
}
