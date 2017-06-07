package com.foodfinder.publishing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodfinder.Base64Parser;
import com.foodfinder.Food;
import com.foodfinder.FoodFinderApplication;
import com.foodfinder.R;
import com.foodfinder.rest.FoodService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class PublishActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 10;
    @BindView(R.id.publish_image)
    ImageView imageView;
    @BindView(R.id.publish_spinner)
    Spinner spinner;
    @BindView(R.id.restaurants_autocomplete_list)
    EditText restaurants;
    @BindView(R.id.food_name_publish)
    TextView foodName;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.author)
    EditText author;
    @BindView(R.id.stars)
    RatingBar stars;
    @BindView(R.id.publish_progress_bar)
    ProgressBar progressBar;
    private GoogleApiClient mGoogleApiClient;
    private Food food = new Food();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        dispatchTakePictureIntent();

        initCategories();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(PublishActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException ignored) {
                }
            }
        });


    }


    private void initCategories() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Chinese");
        adapter.add("Jewish");
        adapter.add("Polish");
        adapter.add("Lithuanian");
        adapter.add("French");
        adapter.add("Japanese");
        adapter.add("Vietnamese");
        adapter.add("Greek");
        adapter.add("Choose Cuisine");

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //display hint
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmClick() {
//        Toast.makeText(this, "The food has been added", Toast.LENGTH_SHORT).show();
        food.setCategory(String.valueOf(spinner.getSelectedItem()));
        food.setComment(String.valueOf(comment.getText()));
        food.setName(String.valueOf(foodName.getText()));
        food.setStars(stars.getRating());
        food.setCustomer(String.valueOf(author.getText()));
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        FoodService foodService = FoodFinderApplication.getRetrofit().create(FoodService.class);
        foodService.saveFood(food)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<Void>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Response<Void> voidResponse) throws Exception {
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(PublishActivity.this, "Something went wrong. Try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            String base64Image = Base64Parser.encode(imageBitmap);
            food.setImage(base64Image);
        }

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                CharSequence restaurantName = place.getName();
                restaurants.setText(restaurantName);
                food.setRestaurantName(String.valueOf(restaurantName));
                food.setLatitude((float) place.getLatLng().latitude);
                food.setLongitude((float) place.getLatLng().longitude);
                Log.i("GOOGLE PLACES:", "Place: " + restaurantName);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("GOOGLE PLACES", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
