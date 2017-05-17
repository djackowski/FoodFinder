package com.example.seeking;

import com.example.database.Database;
import com.example.models.Food;
import com.example.mvp.Presenter;

public class SeekRestaurantPresenter extends Presenter<SeekRestaurantView> {


    public void cardSwipedLeft(Object object) {

    }

    public void cardSwipedRight(Object object) {
        Database.addToChosenFoodList((Food) object);
    }

    public void cardsDepleted() {
        if (view == null) return;
        view.goToChosenFoodScreen();
    }
}
