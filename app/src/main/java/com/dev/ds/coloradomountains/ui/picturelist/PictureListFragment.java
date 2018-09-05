package com.dev.ds.coloradomountains.ui.picturelist;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.dev.ds.coloradomountains.PhotoDetail;
import com.dev.ds.coloradomountains.R;
import com.dev.ds.coloradomountains.models.Photo;
import com.dev.ds.coloradomountains.models.PhotoFeed;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PictureListFragment extends Fragment implements PictureListAdapter.PhotoSelectedProtocol, PictureListAdapter.SearchProtocol {
    private static final String DEFAULT_TAG = "coloradomountains";
    private PictureListViewModel viewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public PictureListAdapter adapter;

    public static PictureListFragment newInstance() {
        return new PictureListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.picture_list_fragment, container, false);
        adapter = new PictureListAdapter(this);
        adapter.photoSelectedDelegate = this;
        adapter.searchDelegate = this;
        recyclerView = (RecyclerView) v.findViewById(R.id.recyler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingButtonClicked();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        viewModel = ViewModelProviders.of(getActivity()).get(PictureListViewModel.class);
        viewModel.retrievePhotoFeed(DEFAULT_TAG);
        setupFeedObservable();
    }

    // Scroll to search bar, focus on it, and bring up the keyboard
    private void floatingButtonClicked() {
        linearLayoutManager.scrollToPosition(0);
        adapter.focusOnSearchBar();
    }

    // Automatically update list of photos whenever the ViewModel's list of images is updated
    private void setupFeedObservable() {
        PublishSubject<PhotoFeed> feed = viewModel.feed;
        Disposable disposable = feed.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new  DisposableObserver<PhotoFeed>() {

                    @Override
                    public void onNext(PhotoFeed logo) {
                        adapter.setDataSet(logo.items);
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });
    }

    // PhotoSelectedProtocol
    public void photoSelected(Photo photo) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.container, PhotoDetail.newInstance(photo)).commit();
    }

    // SearchProtocol
    public void searchApplied(String search) {
        if (search.length() > 0) {
            viewModel.retrievePhotoFeed(search);

            closeKeyboard();
        }
    }

    private void closeKeyboard() {
        InputMethodManager imm;
        imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
