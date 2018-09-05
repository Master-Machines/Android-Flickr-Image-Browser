package com.dev.ds.coloradomountains.ui.picturelist;

import android.util.Log;

import com.dev.ds.coloradomountains.PhotoService;
import com.dev.ds.coloradomountains.models.PhotoFeed;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class PictureListViewModel extends ViewModel {

    public PublishSubject<PhotoFeed> feed;

    public PictureListViewModel() {
        feed = PublishSubject.create();
    }

    public void retrievePhotoFeed(String tag) {
        Observable<PhotoFeed> feedObservable = PhotoService.getInstance().getPhotoApi().getPhotos(tag);

        Disposable disposable = feedObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PhotoFeed>() {
                    @Override
                    public void onNext(PhotoFeed info) {
                        feed.onNext(info);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", e.getMessage());
                    }
                    @Override
                    public void onComplete() { }
                });

    }
}
