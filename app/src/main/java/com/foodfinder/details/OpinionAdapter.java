package com.foodfinder.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodfinder.Food;
import com.foodfinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.OpinionViewHolder> {
    private Context context;
    private Food food;

    public OpinionAdapter(Context context, Food food) {
        this.context = context;
        this.food = food;
    }

    @Override
    public OpinionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_opinion_item, parent, false);
        return new OpinionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OpinionViewHolder holder, int position) {

        holder.getPersonName().setText(food.getCustomer());
        holder.getComment().setText(food.getComment());
        holder.getRating().setText(String.valueOf(food.getStars()));

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class OpinionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.opinion_person_name)
        TextView personName;
        @BindView(R.id.opinion_rating)
        TextView rating;
        @BindView(R.id.opinion_comment)
        TextView comment;


        public OpinionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getPersonName() {
            return personName;
        }

        public TextView getRating() {
            return rating;
        }

        public TextView getComment() {
            return comment;
        }
    }


}
