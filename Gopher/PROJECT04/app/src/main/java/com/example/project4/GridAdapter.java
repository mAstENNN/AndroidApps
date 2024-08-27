package com.example.project4;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class GridAdapter extends BaseAdapter {
    private Context context;
    private String[] gridData;

    public GridAdapter(Context context, String[] gridData) {
        this.context = context;
        this.gridData = gridData;
    }

    @Override
    public int getCount() {
        return gridData.length;
    }

    @Override
    public Object getItem(int position) {
        return gridData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView itemView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            itemView = (TextView) convertView.findViewById(R.id.grid_item_label);
            convertView.setTag(itemView);
        } else {
            itemView = (TextView) convertView.getTag();
        }

        itemView.setText(gridData[position]);
        return convertView;
    }

}
