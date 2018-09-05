package com.dev.ds.coloradomountains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dev.ds.coloradomountains.models.Photo;
import com.dev.ds.coloradomountains.ui.picturelist.PictureListAdapter;
import com.dev.ds.coloradomountains.ui.picturelist.PictureListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    /**
     * Author: Derek Schindhelm
     *
     * Hello TrackVia! This is my submission for your take-home coding challenge.
     * My understanding is that the challenge is meant to be pretty open-ended, so I
     * took some liberty in adding a couple features. Most notably, the app contains
     * a search feature that allows the user to change the tag that is used to generate
     * the list of images.
     *
     * I used Retrofit for network interaction, and RxJava for subscribing to updates when the photo list gets updated.
     *
     *
     * If I had more time, I'd try to improve the animation between the photo list fragment and the photo detail fragment.
     * It would be pretty slick if the selected image expanded into the photo detail fragment.
     * I'd also like to get more than 20 images in the list.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PictureListFragment.newInstance()).commit();
        }
    }

}
