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

public class CustomListRestaurantAdapter extends BaseAdapter {
    private List<Restaurant> listRestaurant;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListRestaurantAdapter(Context aContext, List<Restaurant> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item, null);
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
        holder.discriptionView.setText(restaurant.getDiscription());
        holder.ratingView.setText(restaurant.getRating());

        int imageId = this.getMipmapResIdByName(restaurant.getLogoName());

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
