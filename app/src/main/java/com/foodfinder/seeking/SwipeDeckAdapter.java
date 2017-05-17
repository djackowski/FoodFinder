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

import com.foodfinder.R;
import com.example.models.Food;

import java.util.List;

public class SwipeDeckAdapter extends BaseAdapter {

    private List<Food> data;
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
        ((TextView) v.findViewById(R.id.distance)).setText("2 km");//TODO: (Distance) hardcoded CHANGE IT
        int photoId = data.get(position).getPhotoId();

        ((ImageView) v.findViewById(R.id.food_photo)).setImageBitmap(scale(photoId));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food item = (Food)getItem(position);
                Log.i("MainActivity", item.getName());
            }
        });

        return v;
    }
    private Bitmap scale(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
        int height = (bitmap.getHeight() * 512 / bitmap.getWidth());
        return  Bitmap.createScaledBitmap(bitmap, 512, height, true);
    }

}