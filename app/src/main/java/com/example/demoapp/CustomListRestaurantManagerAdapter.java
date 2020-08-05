package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoapp.Data.Restaurant;

import java.util.List;

public class CustomListRestaurantManagerAdapter extends BaseAdapter {
    private List<Restaurant> listRestaurant;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListRestaurantManagerAdapter(Context aContext, List<Restaurant> listData) {
        this.context = aContext;
        this.listRestaurant = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listRestaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return listRestaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView logoView;
        TextView restaurantNameView;
        TextView discriptionView;
        TextView ratingView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_res_item, null);
            holder = new ViewHolder();
            holder.logoView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.restaurantNameView = (TextView) convertView.findViewById(R.id.restaurantName);
            holder.discriptionView = (TextView) convertView.findViewById(R.id.resDiscription);
            holder.ratingView = (TextView) convertView.findViewById(R.id.rating);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = this.listRestaurant.get(position);
        holder.restaurantNameView.setText(restaurant.getName());
        holder.discriptionView.setText(getRestaurantRevenue(restaurant));
        holder.ratingView.setText(restaurant.getRating());

        int imageId = this.getMipmapResIdByName(restaurant.getLogo());

        holder.logoView.setImageResource(imageId);

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    //Get restaurant revenue
    public String getRestaurantRevenue(Restaurant restaurant){
        int resID = restaurant.getID();
        int revenue = 0;
        for(int i = 0; i < DisplayLogin.orderList.size(); i++){
            if(DisplayLogin.orderList.get(i).getRestaurantID() == resID && DisplayLogin.orderList.get(i).getStatus().equals("completed")) revenue += Float.valueOf(DisplayLogin.orderList.get(i).getPrice());
        }
        String res = "Revenue: ";
        if (revenue == 0) res += "0 VNĐ";
        else res += String.valueOf(revenue) + "000 VNĐ";
        return res;
    }
}
