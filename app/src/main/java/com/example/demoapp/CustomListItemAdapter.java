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

public class CustomListItemAdapter extends BaseAdapter {
    private List<Item> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListItemAdapter(Context aContext, List<Item> listData) {
        this.context = aContext;
        this.itemList = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView nameView;
        TextView quantityView;
        TextView priceView;
        TextView noteView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.item_name);
            holder.quantityView = (TextView) convertView.findViewById(R.id.item_quantity);
            holder.priceView = (TextView) convertView.findViewById(R.id.item_price);
            holder.noteView = (TextView)convertView.findViewById(R.id.item_note);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = this.itemList.get(position);
        holder.nameView.setText(item.getName());
        holder.quantityView.setText(item.getQuantity());
        holder.priceView.setText(item.getPrice());
        holder.noteView.setText("NOTE: " + item.getNote());

        return convertView;
    }
}
