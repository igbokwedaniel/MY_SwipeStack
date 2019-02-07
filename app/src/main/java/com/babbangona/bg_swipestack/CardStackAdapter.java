package com.babbangona.bg_swipestack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<Spot> spots;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_spot, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Spot spot = spots.get(i);
        viewHolder.name.setText(spot.getName());
        viewHolder.city.setText(spot.getCity());
        //use
        Glide.with(viewHolder.image).load(spot.getUrl()).into(viewHolder.getImage());
        //Glide.with(viewHolder.image).load(PUT BITMAP HERE)into(viewHolder.getImage());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),spot.getName(),Toast.LENGTH_SHORT);
            }
        });



    }

    @Override
    public int getItemCount() {
        return spots.size();
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @NonNull
        private TextView city;
        @NonNull
        private ImageView image;
        @NonNull
        private final TextView name;

        public ViewHolder(@NonNull View view ){
            super(view);
            View localView = view.findViewById(R.id.card_stack_view);
            this.name = view.findViewById(R.id.item_name);
            this.city = view.findViewById(R.id.item_city);
            this.image= view.findViewById(R.id.item_image);

        }

        @NonNull
        public TextView getCity() {
            return city;
        }

        @NonNull
        public ImageView getImage() {
            return image;
        }

        @NonNull
        public TextView getName() {
            return name;
        }

        public void setCity(@NonNull TextView city) {
            this.city = city;
        }

        public void setImage(@NonNull ImageView image) {
            this.image = image;
        }

    }
}
