package hu.jonat.darts2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hu.jonat.darts2.Data.ListViewItem;
import hu.jonat.darts2.R;


/**
 * Created by jonat on 2015. 10. 21..
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem> {

    public ListViewAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.description = (TextView) convertView.findViewById(R.id.type);
            viewHolder.player1 = (TextView) convertView.findViewById(R.id.player1);
            viewHolder.player2 = (TextView) convertView.findViewById(R.id.player2);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // update the item view
        ListViewItem item = getItem(position);
        viewHolder.description.setText(item.description);
        viewHolder.player1.setText(item.player1);
        viewHolder.player2.setText(item.player2);

        return convertView;
    }

    // smooth scrolling
    private static class ViewHolder {
        TextView description;
        TextView player1;
        TextView player2;
    }
}
