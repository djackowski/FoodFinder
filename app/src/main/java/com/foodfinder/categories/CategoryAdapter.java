package com.foodfinder.categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foodfinder.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category_card, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.getName().setText(category.getName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                category.getThumbnail());
        Palette palette = Palette.from(bitmap).generate();

        holder.getName().setTextColor(palette.getLightVibrantColor(context.getResources().getColor(R.color.colorPrimary)));
        holder.getName().setBackgroundColor(context.getResources().getColor(R.color.blackTransparent));
        Glide.with(context).load(category.getThumbnail()).into(holder.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
       /* @BindView(R.id.quantity)
        TextView quantity;*/

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getName() {
            return name;
        }

        public ImageView getThumbnail() {
            return thumbnail;
        }

//        public TextView getQuantity() {
//            return quantity;
//        }
    }


}
