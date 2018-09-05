package com.dev.ds.coloradomountains;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dev.ds.coloradomountains.models.Photo;


public class PhotoDetail extends Fragment {
    private static final String ARG_PHOTO = "ARG_PHOTO";

    private Photo photo;

    public PhotoDetail() {
        // Required empty public constructor
    }


    public static PhotoDetail newInstance(Photo photo) {
        PhotoDetail fragment = new PhotoDetail();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.photo = getArguments().getParcelable(ARG_PHOTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_detail, container, false);


        ImageView imageView = v.findViewById(R.id.imageView);

        RequestManager glide = Glide.with(this);
        glide.load(photo.media.photoUrl).into(imageView);

        TextView titleView = v.findViewById(R.id.text_title);
        titleView.setText(photo.title);

        TextView authorView = v.findViewById(R.id.text_author);
        authorView.setText(photo.getAuthorDescription());

        TextView dateView = v.findViewById(R.id.text_date);
        dateView.setText(photo.getReadableDateTaken());

        TextView tagView = v.findViewById(R.id.text_tags);
        tagView.setText(photo.getTagsDescription());

        return v;
    }
}
