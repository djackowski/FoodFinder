package com.example.database;


import com.example.models.Food;
import com.example.models.Opinion;
import com.example.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<Food> foodList = new ArrayList<>();
    private static List<Food> chosenFoodList = new ArrayList<>();

    public static List<Food> getFoodListFilledWithData(int chinese, int french, int jewish) {
        foodList.clear();
        Restaurant firstRestaurant = new Restaurant("China restaurant", null);
        Restaurant secondRestaurant = new Restaurant("French restaurant", null);
        Restaurant thirdRestaurant = new Restaurant("Japanese restaurant", null);

        List<Opinion> firstOpinions = new ArrayList<>();
        Opinion firstOpinion = new Opinion("Kate", "It was great!", 4.5f);
        Opinion secondOpinion = new Opinion("Mary", "The best food I've ever eaten ;)", 5.0f);
        Opinion thirdOpinion = new Opinion("Henry", "Hmmm... not bad", 3.5f);

        firstOpinions.add(firstOpinion);
        firstOpinions.add(secondOpinion);
        firstOpinions.add(thirdOpinion);

        Food firstFood = new Food(chinese, "China Food", 4.5f, firstRestaurant, firstOpinions);
        Food secondFood = new Food(french, "French Food", 3.5f, secondRestaurant, firstOpinions);
        Food thirdFood = new Food(jewish, "Japanese Food", 5.0f, thirdRestaurant, firstOpinions);
        foodList.add(firstFood);
        foodList.add(secondFood);
        foodList.add(thirdFood);
        return foodList;
    }


    public static void addToChosenFoodList(Food food) {
        chosenFoodList.add(food);
    }

    public static List<Food> getChosenFoodList() {
        return chosenFoodList;
    }
}
