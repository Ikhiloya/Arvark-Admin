package com.loya.android.arvark_admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loya.android.arvark_admin.R;
import com.loya.android.arvark_admin.models.Equipment;

import java.util.ArrayList;

/**
 * Created by user on 12/10/2017.
 */

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Equipment> equipments;

    public EquipmentAdapter(Context context, ArrayList<Equipment> equipments) {
        this.context = context;
        this.equipments = equipments;
    }

    @Override
    public EquipmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquipmentAdapter.ViewHolder holder, int position) {
        Equipment equipment = equipments.get(position);
        holder.name.setText(equipment.getName());
        holder.category.setText(equipment.getCategory());
        holder.imageView.setImageResource(equipment.getImage());
        holder.originalPrice.setText(equipment.getOriginalPrice());
        holder.discountPrice.setText(equipment.getDiscountPrice());
        holder.size.setText(equipment.getSize());
        holder.desc.setText(equipment.getDesc());
    }

    @Override
    public int getItemCount() {
        return equipments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView category;
        private ImageView imageView;
        private TextView originalPrice;
        private TextView discountPrice;
        private TextView size;
        private TextView desc;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            category = itemView.findViewById(R.id.category_name);
            imageView = itemView.findViewById(R.id.item_image);
            originalPrice = itemView.findViewById(R.id.original_price);
            discountPrice = itemView.findViewById(R.id.discount_price);
            size = itemView.findViewById(R.id.item_size);
            desc = itemView.findViewById(R.id.item_desc);
        }
    }
}
