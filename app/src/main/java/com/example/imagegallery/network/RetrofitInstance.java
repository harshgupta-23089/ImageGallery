package com.example.imagegallery.network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    public static Retrofit getRetrofitClient() {
      if(retrofit == null)
          retrofit = new Retrofit.Builder().baseUrl("https://api.flickr.com/services/").addConverterFactory(ScalarsConverterFactory.create()).build();
      return retrofit;
    }

}
