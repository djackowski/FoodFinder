package com.foodfinder.categories;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.models.Category;
import com.foodfinder.R;
import com.foodfinder.publishing.PublishActivity;
import com.foodfinder.seeking.SeekRestaurantActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.CategoryItemListener {
    public static final String EXTRA_CATEGORY_IMAGE_TRANSITION_NAME = "image_transition_name";
    public static final String EXTRA_CATEGORY_ITEM = "category_extras";
    public static final String EXTRA_CATEGORY_NAME_COLOR = "category_name_color";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private List<Category> categoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, PublishActivity.class);
                startActivity(intent);
            }
        });

        prepareCategories();

        final CategoryAdapter adapter = new CategoryAdapter(this, categoryList, this);
        collapsingToolbarLayout.setTitle("Choose Cuisine");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.cover);
        Palette palette = Palette.from(bitmap).generate();
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(lightVibrantColor);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void prepareCategories() {
        Category greek = new Category("Greek", 4, R.drawable.greek);
        Category chinese = new Category("Chinese", 2, R.drawable.chinese);
        Category japanese = new Category("Japanese", 44, R.drawable.japanese);
        Category polish = new Category("Polish", 192, R.drawable.polish);
        Category vietnamese = new Category("Vietnamese", 23, R.drawable.vietnamese);
        Category lithuanian = new Category("Lithuanian", 46, R.drawable.lithuanian);
        Category jewish = new Category("Jewish", 71, R.drawable.jewish);
        Category french = new Category("French", 9, R.drawable.french);

        categoryList.add(greek);
        categoryList.add(chinese);
        categoryList.add(japanese);
        categoryList.add(polish);
        categoryList.add(vietnamese);
        categoryList.add(lithuanian);
        categoryList.add(jewish);
        categoryList.add(french);
    }

    @Override
    public void onItemClickListener(int adapterPosition, Category category, ImageView sharedImageView, int lightVibrantColor) {
        Intent intent = new Intent(this, SeekRestaurantActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ITEM, category);
        String imageViewTransitionName = ViewCompat.getTransitionName(sharedImageView);
        intent.putExtra(EXTRA_CATEGORY_IMAGE_TRANSITION_NAME, imageViewTransitionName);
        intent.putExtra(EXTRA_CATEGORY_NAME_COLOR, lightVibrantColor);



        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                sharedImageView, imageViewTransitionName);

        startActivity(intent, options.toBundle());
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
