package com.lampnc.businfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private List<String> itemList;
    private List<String> labelList;
    private List<Long> tags;

    public ListViewAdapter(Activity context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.itemList = objects;
        this.labelList = null;
        this.tags = null;
    }

    public ListViewAdapter(Activity context, int resource, List<String> objects, List<String> labels) {
        super(context, resource, objects);
        this.activity = context;
        this.itemList = objects;
        this.labelList = labels;
        this.tags = null;
    }

    public ListViewAdapter(Activity context, int resource, List<String> objects, List<String> labels, List<Long> tags) {
        super(context, resource, objects);
        this.activity = context;
        this.itemList = objects;
        this.labelList = labels;
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public String getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (tags != null) {
            return tags.get(position);
        } else {
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.labelText.setText(getItem(position));

        //get first letter of each String item
        String firstLetter = "";
        if (labelList == null) {
            firstLetter = String.valueOf(getItem(position).charAt(0));
        } else {
            firstLetter = labelList.get(position);
        }
        if (tags != null) {
            holder.imageView.setTag(tags.get(position));
        }
        // ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        // int color = generator.getColor(getItem(position));
        // int color = generator.getRandomColor();

        // TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color); // radius in px

        holder.imageView.setText(firstLetter);

        return convertView;
    }

    private class ViewHolder {
        private TextView imageView;
        private TextView labelText;

        public ViewHolder(View v) {
            imageView = (TextView) v.findViewById(R.id.image_view);
            labelText = (TextView) v.findViewById(R.id.text);
        }
    }
}
