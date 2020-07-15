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

public class CustomListBillCookAdapter extends BaseAdapter {
    private List<Bill> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListBillCookAdapter(Context aContext, List<Bill> listData) {
        this.context = aContext;
        this.list = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView finishedView;
        TextView billIDView;
        TextView priceView;
        TextView timeView;
    }

    //View food information in food court's interface.
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_bill_cook_item, null);
            holder = new ViewHolder();
            holder.billIDView = (TextView) convertView.findViewById(R.id.bill_id);
            holder.timeView = (TextView) convertView.findViewById(R.id.delivery_time);
            holder.priceView = (TextView)convertView.findViewById(R.id.total_price);
            holder.finishedView = (ImageView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bill bill = this.list.get(position);
        holder.billIDView.setText(bill.getBillID());
        holder.timeView.setText(bill.getTime());
        holder.priceView.setText(bill.getTotalPrice() + "VNĐ");

        if (bill.getStatus().equals("unconfirmed")){
            holder.finishedView.setImageDrawable(context.getResources().getDrawable(R.drawable.unconfirmed));
        }
        else if (bill.getStatus().equals("being_prepared")){
            holder.finishedView.setImageDrawable(context.getResources().getDrawable(R.drawable.being_prepared));
        }
        else{
            holder.finishedView.setImageDrawable(context.getResources().getDrawable(R.drawable.finished));
        }

        return convertView;
    }
}