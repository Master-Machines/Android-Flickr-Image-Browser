package com.dev.ds.coloradomountains;

import com.dev.ds.coloradomountains.models.PhotoFeed;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PhotoApi {

    @GET("feeds/photos_public.gne?format=json&nojsoncallback=1")
    Observable<PhotoFeed> getPhotos(@Query("tags") String tag);

}
