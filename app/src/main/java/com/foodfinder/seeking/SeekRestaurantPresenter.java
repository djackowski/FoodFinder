package com.foodfinder.seeking;

import com.foodfinder.Food;
import com.foodfinder.database.Database;
import com.foodfinder.mvp.Presenter;


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
