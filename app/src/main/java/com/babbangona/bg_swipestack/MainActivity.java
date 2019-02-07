package com.babbangona.bg_swipestack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.babbangona.bg_swipestack.CardStackAdapter.*;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.*;

public class MainActivity extends AppCompatActivity implements  CardStackListener{

    public static final String TAG = "BABBANGONA";

    private DrawerLayout drawerLayout;
    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private  CardStackAdapter cardStackAdapter;

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START)) drawerLayout.closeDrawers();
        else
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout  = findViewById(R.id.drawer_layout);
        cardStackView = findViewById(R.id.card_stack_view);
        cardStackLayoutManager = new CardStackLayoutManager(this,this);
        cardStackAdapter = new CardStackAdapter(); cardStackAdapter.setSpots(createSpots());


        setupNavigation();
        setupCardStackView();
        setupButton();



    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
        Log.i(TAG, "onCardDragging: " + direction.toString());
    }

    @Override
    public void onCardSwiped(Direction direction) {
        Log.i(TAG, "onCardSwiped: " + direction.toString());
        if(cardStackLayoutManager.getTopPosition() == cardStackAdapter.getItemCount() - 5)
            paginate();
    }

    @Override
    public void onCardRewound() {
        Log.i(TAG, "onCardRewound: " + cardStackLayoutManager.getTopPosition());
    }

    @Override
    public void onCardCanceled() {
        Log.i(TAG, "onCardCanceled: " + cardStackLayoutManager.getTopPosition());
    }

    @Override
    public void onCardAppeared(View view, int position) {
        TextView val = findViewById(R.id.item_name);
        Log.i(TAG, "onCardAppeared: " + val.getText());
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        TextView val = findViewById(R.id.item_name);
        Log.i(TAG, "onCardDisAppeared: " + val.getText());
    }

    private void setupCardStackView() {
        this.initialize();

    }

    public List<Spot> createSpots(){
        ArrayList<Spot> spots =  new ArrayList<Spot>();
        spots.add(new Spot(0,"Yasaka Shrine",  "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/600x800"));
        spots.add(new Spot(0,"Fushimi Inari Shrine",  "Kyoto",  "https://source.unsplash.com/NYyCqdBOKwc/600x800"));
        spots.add(new Spot(0,"Bamboo Forest",  "Kyoto",  "https://source.unsplash.com/buF62ewDLcQ/600x800"));
        spots.add(new Spot(0,"Brooklyn Bridge",  "New York",  "https://source.unsplash.com/THozNzxEP3g/600x800"));
        spots.add(new Spot(0,"Empire State Building",  "New York",  "https://source.unsplash.com/USrZRcRS2Lw/600x800"));
        spots.add(new Spot(0,"The statue of Liberty",  "New York",  "https://source.unsplash.com/PeFk7fzxTdk/600x800"));
        spots.add(new Spot(0,"Louvre Museum",  "Paris",  "https://source.unsplash.com/LrMWHKqilUw/600x800"));
        spots.add(new Spot(0,"Eiffel Tower",  "Paris",  "https://source.unsplash.com/HN-5Z6AmxrM/600x800"));
        spots.add(new Spot(0,"Big Ben",  "London",  "https://source.unsplash.com/CdVAUADdqEc/600x800"));
        spots.add(new Spot(0,"Great Wall of China",  "China",  "https://source.unsplash.com/AWh9C-QjhE4/600x800"));
        return spots;

    }

    public void setupNavigation () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.reload : reload();
                    break;
                    case R.id.add_spot_to_first : addFirst(1);
                    break;
                    case R.id.add_spot_to_last :  addLast(1);
                    break;
                    case R.id.remove_spot_from_first : removeFirst(1);
                    break;
                    case R.id.remove_spot_from_last : removeLast(1);
                    break;
                    case R.id.replace_first_spot :  replace();
                    break;
                    case R.id.swap_first_for_last : swap();
                    break;
                }
                drawerLayout.closeDrawers();
                return  true;
            }
        });

    }

    private void paginate(){

        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New =  new ArrayList<>();
        New.addAll(Old);
        New.addAll(createSpots());
        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);

    }

    public void reload(){
        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = createSpots();
        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);
    }

    public void addFirst(int i){

        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = new ArrayList<>();
        New.addAll(Old);
        for(int j = 0; j< i; j++)
        New.add(cardStackLayoutManager.getTopPosition(), createSpot());

        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);
    }

    public void removeFirst(int position){
        if(cardStackAdapter.getSpots().isEmpty())
            return;

        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = createSpots();
        New.addAll(Old);
        for( int i = 0; i < position; i++)
            New.remove(cardStackLayoutManager.getTopPosition());

        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);

    }

    public void removeLast(int position){
        if(cardStackAdapter.getSpots().isEmpty())
            return;

        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = createSpots();
        New.addAll(Old);
        for( int i = 0; i < position; i++)
            New.remove(New.size()-1);

        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);
    }

    public void replace(){
        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = new ArrayList<>();
        New.addAll(Old);
            New.remove(cardStackLayoutManager.getTopPosition());
            New.add(cardStackLayoutManager.getTopPosition(), createSpot());

        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);

    }

    public void swap(){
        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = new ArrayList<>();
        New.addAll(Old);

        Spot first = New.remove(cardStackLayoutManager.getTopPosition());
        Spot Last = New.remove(New.size()-1);

        New.add(cardStackLayoutManager.getTopPosition(),Last);
        New.add(first);

        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);

    }

    public void addLast( int position){
        List<Spot> Old = this.cardStackAdapter.getSpots();
        List<Spot> New = new ArrayList<>();
        New.addAll(Old);
        New.add(createSpot());

        SpotDiffCallback callback  = new SpotDiffCallback(Old, New);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        cardStackAdapter.setSpots(New);
        result.dispatchUpdatesTo(cardStackAdapter);
    }


    public Spot createSpot(){

        return  new Spot(0,
                "Yasaka Shrine",
                "Kyoto",
                "https://source.unsplash.com/Xq1ntWruZQI/600x800");
    }

    private void initialize(){
        cardStackLayoutManager.setStackFrom(StackFrom.None);
        cardStackLayoutManager.setVisibleCount(3);
        cardStackLayoutManager.setTranslationInterval(8.0F);
        cardStackLayoutManager.setScaleInterval(0.95F);
        cardStackLayoutManager.setSwipeThreshold(0.3F);
        cardStackLayoutManager.setMaxDegree(20.0F);
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setCanScrollVertical(true);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        cardStackView.setAdapter(cardStackAdapter);
        RecyclerView.ItemAnimator itemAnimator = cardStackView.getItemAnimator();
        if (itemAnimator instanceof DefaultItemAnimator)
            ((DefaultItemAnimator)itemAnimator).setSupportsChangeAnimations(false);
    }

    private void setupButton(){
        FloatingActionButton skip = findViewById(R.id.skip_button);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Left)
                        .setDuration(200)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                cardStackLayoutManager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });

        FloatingActionButton rewind = findViewById(R.id.rewind_button);
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().
                        setDirection(Direction.Bottom)
                        .setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .build();
                cardStackLayoutManager.setSwipeAnimationSetting(setting);
                cardStackView.rewind();
            }
        });

        FloatingActionButton like = findViewById(R.id.like_button);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Left)
                        .setDuration(200)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                cardStackLayoutManager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });
    }

}
