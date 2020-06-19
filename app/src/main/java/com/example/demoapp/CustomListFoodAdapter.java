package com.example.demoapp;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListFoodAdapter extends BaseAdapter {
    private List<Food> listFood;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListFoodAdapter(Context aContext, List<Food> listData) {
        this.context = aContext;
        this.listFood = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public Object getItem(int position) {
        return listFood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView logoView;
        TextView foodNameView;
        TextView discriptionView;
        TextView quantityView;
        TextView priceView;
        TextView ratingView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_food_item, null);
            holder = new ViewHolder();
            holder.logoView = (ImageView) convertView.findViewById(R.id.imageFoodView);
            holder.foodNameView = (TextView) convertView.findViewById(R.id.foodName);
            holder.quantityView = (TextView) convertView.findViewById(R.id.foodQuantity);
            holder.ratingView = (TextView) convertView.findViewById(R.id.foodRating);
            holder.priceView = (TextView)convertView.findViewById(R.id.foodPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Food food = this.listFood.get(position);
        holder.foodNameView.setText(food.getName());
        holder.ratingView.setText(food.getRating());
        holder.quantityView.setText("REMAIN: " + Integer.toString(food.getQuantity()));
        holder.priceView.setText(food.getPrice() + "VNĐ");

        int imageId = this.getMipmapResIdByName(food.getLogo());

        holder.logoView.setImageResource(imageId);

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
}
