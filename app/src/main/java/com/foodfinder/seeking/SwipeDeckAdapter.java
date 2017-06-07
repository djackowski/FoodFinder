package com.foodfinder.seeking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodfinder.Base64Parser;
import com.foodfinder.R;
import com.foodfinder.Food;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeckAdapter extends BaseAdapter {

    private List<Food> data = new ArrayList<>();
    private Context context;

    public SwipeDeckAdapter(List<Food> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate(R.layout.swipe_deck_single_card, parent, false);
        }
        ((TextView) v.findViewById(R.id.food_name)).setText(data.get(position).getName());
        ((TextView) v.findViewById(R.id.distance)).setText(data.get(position).getDistance() + "km");
        String photoId = data.get(position).getImage();

        Bitmap decoded = Base64Parser.decode(photoId);

        Bitmap scaled = scale(decoded);

        ((ImageView) v.findViewById(R.id.food_photo)).setImageBitmap(scaled);

        return v;
    }

    public void addAll(List<Food> result) {
        data.addAll(result);
        notifyDataSetChanged();
    }
    private Bitmap scale(Bitmap bitmap) {
        int height = (bitmap.getHeight() * 512 / bitmap.getWidth());
        return  Bitmap.createScaledBitmap(bitmap, 512, height, true);
    }

}