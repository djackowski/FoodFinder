package com.foodfinder.seeking;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.models.Category;
import com.foodfinder.Food;
import com.foodfinder.FoodFinderApplication;
import com.foodfinder.R;
import com.foodfinder.categories.CategoryActivity;
import com.foodfinder.choice.ChosenFoodActivity;
import com.foodfinder.database.Database;
import com.foodfinder.localization.GPSTracker;
import com.foodfinder.rest.FoodService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.foodfinder.categories.CategoryActivity.EXTRA_CATEGORY_NAME_COLOR;


public class SeekRestaurantActivity extends AppCompatActivity implements SeekRestaurantView,
        SwipeDeck.SwipeEventCallback {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @BindView(R.id.seek_restaurant_confirm)
    Button confirm_button;
    @BindView(R.id.seek_restaurant_cancel)
    Button cancel_button;
    @BindView(R.id.swipe_deck)
    SwipeDeck cardStack;
    @BindView(R.id.food_loading)
    ProgressBar foodLoading;
    @BindView(R.id.food_loading_text)
    TextView pleaseWaitCard;
    @BindView(R.id.no_food_found)
    LinearLayout noFoodFound;
    @BindView(R.id.ticks)
    RelativeLayout ticks;

    private SeekRestaurantPresenter presenter;
    private SwipeDeckAdapter adapter;
    private Category category;
    private GPSTracker gps;
    private double latitude;
    private double longitude;
    private Disposable subscribe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_restaurant);

        ButterKnife.bind(this);

        presenter = new SeekRestaurantPresenter();
        gps = new GPSTracker(this);
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

        downloadFoods();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    private void downloadFoods() {
        foodLoading.setVisibility(View.VISIBLE);
        pleaseWaitCard.setVisibility(View.VISIBLE);
        foodLoading.setIndeterminate(true);
        FoodService foodService = FoodFinderApplication.getRetrofit().create(FoodService.class);
        String name = category.getName();


        loadCurrentLocalization();

        subscribe = foodService.getFoods((float) latitude, (float) longitude, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Food>>() {
                    @Override
                    public void accept(@NonNull List<Food> food) throws Exception {
                        if (food.size() == 0) {
                            noFoodFound.setVisibility(View.VISIBLE);
                            ticks.setVisibility(View.GONE);
                        } else {
                            noFoodFound.setVisibility(View.GONE);
                            ticks.setVisibility(View.VISIBLE);
                        }
                        adapter.addAll(food);
                        foodLoading.setVisibility(View.GONE);
                        pleaseWaitCard.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    // check if GPS enabled
                    if (gps.canGetLocation()) {

                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        // \n is for new line
//                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Permission Denied
                 /*   Toast.makeText(this, "Denied", Toast.LENGTH_SHORT)
                            .show();*/
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void loadGps() {

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // Permission Denied
            /*Toast.makeText(this, "Denied", Toast.LENGTH_SHORT)
                    .show();*/
        }
    }

    private void loadCurrentLocalization() {

        int hasWriteContactsPermission = checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            loadGps();
        }


    }

    private void handleSharedElementTransition() {
        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        category = (Category) extras.getSerializable(CategoryActivity.EXTRA_CATEGORY_ITEM);

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
