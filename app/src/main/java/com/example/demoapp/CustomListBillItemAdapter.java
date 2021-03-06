package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListBillItemAdapter extends BaseAdapter {
    private List<BillItem> billItemList;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListBillItemAdapter(Context aContext, List<BillItem> listData) {
        this.context = aContext;
        this.billItemList = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return billItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return billItemList.get(position);
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

        BillItem billItem = this.billItemList.get(position);
        holder.nameView.setText(billItem.getName());
        holder.quantityView.setText(billItem.getQuantity());
        holder.priceView.setText(billItem.getPrice());
        holder.noteView.setText("NOTE: " + billItem.getDescription());

        return convertView;
    }
}
