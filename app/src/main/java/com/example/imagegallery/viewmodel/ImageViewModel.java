package com.example.imagegallery.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.imagegallery.model.ImageModel;
import com.example.imagegallery.network.ApiCallInterface;
import com.example.imagegallery.network.RetrofitInstance;
import com.example.imagegallery.view.MainActivity;
import com.example.imagegallery.view.adapters.GalleryImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageViewModel extends ViewModel {

    private MutableLiveData<List<ImageModel>> imageList;
    public ImageViewModel() {
        imageList = new MutableLiveData<>();
    }

    public MutableLiveData<List<ImageModel>> getImageListObserver() {
        return imageList;
    }

    public void makeApiCall() {
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
