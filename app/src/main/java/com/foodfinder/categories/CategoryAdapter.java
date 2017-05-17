package com.foodfinder.categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.models.Category;
import com.foodfinder.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private CategoryItemListener listener;

    public CategoryAdapter(Context context, List<Category> categoryList, CategoryItemListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category_card, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {

        final Category category = categoryList.get(position);
        holder.getName().setText(category.getName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                category.getThumbnail());
        Palette palette = Palette.from(bitmap).generate();

        final int lightVibrantColor = palette.getLightVibrantColor(context.getResources().getColor(R.color.colorPrimary));
        holder.getName().setTextColor(lightVibrantColor);
        holder.getName().setBackgroundColor(context.getResources().getColor(R.color.blackTransparent));
        Glide.with(context).load(category.getThumbnail()).into(holder.getThumbnail());

        ViewCompat.setTransitionName(holder.getThumbnail(), String.valueOf(category.getThumbnail()));

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(holder.getAdapterPosition(), category, holder.getThumbnail(), lightVibrantColor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface CategoryItemListener {
        void onItemClickListener(int adapterPosition, Category category, ImageView sharedImageView, int lightVibrantColor);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.single_category_view_group)
        LinearLayout view;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;


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
    }
}
