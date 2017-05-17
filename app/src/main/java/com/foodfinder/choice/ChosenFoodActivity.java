package com.foodfinder.choice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.database.Database;
import com.example.models.Food;
import com.foodfinder.R;
import com.foodfinder.details.FoodDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class ChosenFoodActivity extends AppCompatActivity implements FoodAdapter.FoodItemListener {

    public static final String EXTRA_FOOD_ITEM = "extra_food_item";
    public static final String EXTRA_SHARED_IMAGE_VIEW_TRANSITION_NAME = "extra_shared_image";
    @BindView(R.id.recycler_view_chosen_food)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_food);
        ButterKnife.bind(this);

        List<Food> chosenFoodList = Database.getChosenFoodList();
        FoodAdapter adapter = new FoodAdapter(this, chosenFoodList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(Food food, ImageView sharedImageView) {
        Intent intent = new Intent(ChosenFoodActivity.this, FoodDetailsActivity.class);
        intent.putExtra(EXTRA_FOOD_ITEM, food);
        String imageViewTransitionName = ViewCompat.getTransitionName(sharedImageView);
        intent.putExtra(EXTRA_SHARED_IMAGE_VIEW_TRANSITION_NAME, imageViewTransitionName);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                sharedImageView, imageViewTransitionName);

        startActivity(intent, options.toBundle());
    }
}
