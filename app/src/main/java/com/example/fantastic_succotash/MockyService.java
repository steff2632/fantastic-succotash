package com.example.fantastic_succotash;

import com.example.fantastic_succotash.data.News;

import retrofit2.http.GET;
import rx.Single;

/**
 * Created by stefanmay on 27/10/2016.
 */

public interface MockyService {

    @GET("http://www.mocky.io/v2/57ee2ca8260000f80e1110fa")
    Single<News[]> getData();
}
