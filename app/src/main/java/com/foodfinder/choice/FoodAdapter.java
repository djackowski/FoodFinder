package com.foodfinder.choice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.models.Food;
import com.foodfinder.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private List<Food> foodList;
    private FoodItemListener listener;

    public FoodAdapter(Context context, List<Food> foodList, FoodItemListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_food_card, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, int position) {

        final Food food = foodList.get(position);
        int photoId = food.getPhotoId();
        holder.getPhoto().setImageBitmap(scale(photoId));
        holder.getName().setText(food.getName());

        ViewCompat.setTransitionName(holder.getPhoto(), String.valueOf(food.getName() + position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(food, holder.getPhoto());
            }
        });
    }

    private Bitmap scale(int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
        int height = (bitmap.getHeight() * 512 / bitmap.getWidth());
        return Bitmap.createScaledBitmap(bitmap, 512, height, true);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public interface FoodItemListener {

        void onItemClickListener(Food food, ImageView shareImageView);
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.single_food_view)
        LinearLayout view;
        @BindView(R.id.chosen_food_name)
        TextView name;
        @BindView(R.id.chosen_food_photo)
        ImageView photo;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getName() {
            return name;
        }

        public ImageView getPhoto() {
            return photo;
        }
    }


}
