package com.foodfinder.seeking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.database.Database;
import com.example.models.Category;
import com.example.seeking.SeekRestaurantPresenter;
import com.example.seeking.SeekRestaurantView;
import com.foodfinder.R;
import com.foodfinder.categories.CategoryActivity;
import com.foodfinder.choice.ChosenFoodActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.foodfinder.categories.CategoryActivity.EXTRA_CATEGORY_NAME_COLOR;

//TODO:
//1. Get current localization
//2. Get list of nearby restaurants according to got localization
public class SeekRestaurantActivity extends AppCompatActivity implements SeekRestaurantView,
        SwipeDeck.SwipeEventCallback {

    @BindView(R.id.seek_restaurant_confirm)
    Button confirm_button;
    @BindView(R.id.seek_restaurant_cancel)
    Button cancel_button;
    @BindView(R.id.swipe_deck)
    SwipeDeck cardStack;

    private SeekRestaurantPresenter presenter;
    private SwipeDeckAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_restaurant);

        ButterKnife.bind(this);

        presenter = new SeekRestaurantPresenter();

        adapter = new SwipeDeckAdapter(Database.getFoodListFilledWithData(R.drawable.chinese,
                R.drawable.french, R.drawable.jewish), this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(this);

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        handleSharedElementTransition();

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStack.swipeTopCardRight(5000);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStack.swipeTopCardLeft(5000);
            }
        });
    }

    private void handleSharedElementTransition() {
        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        Category category = (Category) extras.getSerializable(CategoryActivity.EXTRA_CATEGORY_ITEM);

        ImageView imageView = (ImageView) findViewById(R.id.seek_restaurant_header);
        TextView textView = (TextView) findViewById(R.id.seek_restaurant_cuisine_name);
        textView.setText(category.getName());
        textView.setBackgroundColor(getResources().getColor(R.color.blackTransparent));
        int color = extras.getInt(EXTRA_CATEGORY_NAME_COLOR);
        textView.setTextColor(color);

        int imageId = category.getThumbnail();
        String imageTransitionName = extras.getString(CategoryActivity.EXTRA_CATEGORY_IMAGE_TRANSITION_NAME);
        imageView.setTransitionName(imageTransitionName);



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

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        presenter.detach();
        super.onStop();
    }

    @Override
    public void cardSwipedLeft(int position) {
        Object object = adapter.getItem(position);
        presenter.cardSwipedLeft(object);
        Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
    }

    @Override
    public void cardSwipedRight(int position) {
        Object object = adapter.getItem(position);
        presenter.cardSwipedRight(object);
        Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
    }

    @Override
    public void cardsDepleted() {
        presenter.cardsDepleted();
        Log.i("MainActivity", "no more cards");
    }

    @Override
    public void cardActionDown() {

    }

    @Override
    public void cardActionUp() {

    }

    @Override
    public void goToChosenFoodScreen() {
        Intent intent = new Intent(this, ChosenFoodActivity.class);
        startActivity(intent);
        finish();
    }
}
