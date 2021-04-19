package com.example.imagegallery.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.imagegallery.repository.model.ImageModel;
import com.example.imagegallery.network.ApiCallInterface;
import com.example.imagegallery.network.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {

    private MutableLiveData<List<ImageModel>> imageList;

    public ImageRepository() {
        imageList = new MutableLiveData<>();
    }
    public MutableLiveData<List<ImageModel>> makeApiCall() {
        ApiCallInterface apiCallInterface = RetrofitInstance.getRetrofitClient().create(ApiCallInterface.class);
        Call<String> stringCall = apiCallInterface.STRING_CALL();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        List<ImageModel> image_url = parseArray(jsonObject);
                        imageList.postValue(image_url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                imageList.postValue(null);

            }
        });
        return imageList;
    }
    private List<ImageModel> parseArray(JSONObject jsonObject) throws JSONException {
        List<ImageModel> url = new ArrayList<>();
        JSONObject photos = (JSONObject) jsonObject.getJSONObject("photos");
        JSONArray photo = (JSONArray) photos.getJSONArray("photo");
        for(int i=0;i<photo.length();i++)
        {
            JSONObject user_object = photo.getJSONObject(i);
            url.add(new ImageModel(user_object.getString("url_s")));
        }
        return url;
    }
}
