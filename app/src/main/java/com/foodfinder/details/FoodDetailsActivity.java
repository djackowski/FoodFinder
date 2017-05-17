package com.foodfinder.details;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.models.Food;
import com.foodfinder.R;
import com.foodfinder.categories.CategoryActivity;
import com.foodfinder.choice.ChosenFoodActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailsActivity extends AppCompatActivity {

    @BindView(R.id.food_detail_photo)
    ImageView imageView;
    @BindView(R.id.food_detail_name)
    TextView foodName;
    @BindView(R.id.food_detail_restaurant_name)
    TextView restaurantName;
    @BindView(R.id.food_detail_restaurant_rating)
    RatingBar rating;
    @BindView(R.id.food_details_opinions)
    RecyclerView foodOpinions;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private OpinionAdapter opinionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ButterKnife.bind(this);
        handleSharedElementTransition();
    }

    private void handleSharedElementTransition() {
        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        Food food = (Food) extras.getSerializable(ChosenFoodActivity.EXTRA_FOOD_ITEM);

        foodName.setText(food.getName());
        restaurantName.setText(food.getRestaurant().getName());
        rating.setRating(food.getRating());

        int imageId = food.getPhotoId();
        String imageTransitionName = extras.getString(ChosenFoodActivity.EXTRA_SHARED_IMAGE_VIEW_TRANSITION_NAME);
        imageView.setTransitionName(imageTransitionName);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        foodOpinions.setLayoutManager(mLayoutManager);
        foodOpinions.setItemAnimator(new DefaultItemAnimator());
        opinionAdapter = new OpinionAdapter(this, food.getOpinion());

        foodOpinions.setAdapter(opinionAdapter);

        Picasso.with(this)
                .load(imageId)
                .noFade()
                .into(imageView, new Callback() {
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });
    }
}
