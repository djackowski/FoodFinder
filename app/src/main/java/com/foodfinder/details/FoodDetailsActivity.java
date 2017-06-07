package com.foodfinder.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.foodfinder.Base64Parser;
import com.foodfinder.Food;
import com.foodfinder.R;
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
    @BindView(R.id.navigate)
    FloatingActionButton navigate;

    private OpinionAdapter opinionAdapter;
    private Food food;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ButterKnife.bind(this);
        handleSharedElementTransition();

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float latitude = food.getLatitude();
                float longitude = food.getLongitude();
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    private void handleSharedElementTransition() {
        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        food = (Food) extras.getSerializable(ChosenFoodActivity.EXTRA_FOOD_ITEM);

        foodName.setText(food.getName());
        restaurantName.setText(food.getRestaurantName());
        rating.setRating(food.getStars());

        String imageId = food.getImage();
        String imageTransitionName = extras.getString(ChosenFoodActivity.EXTRA_SHARED_IMAGE_VIEW_TRANSITION_NAME);
        imageView.setTransitionName(imageTransitionName);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        foodOpinions.setLayoutManager(mLayoutManager);
        foodOpinions.setItemAnimator(new DefaultItemAnimator());
        opinionAdapter = new OpinionAdapter(this, food);

        foodOpinions.setAdapter(opinionAdapter);

        Bitmap decode = Base64Parser.decode(imageId);
        imageView.setImageBitmap(decode);
    }
}
