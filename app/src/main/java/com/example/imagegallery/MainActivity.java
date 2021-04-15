package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.imagegallery.adapters.GalleryImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.images);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        getData();

    }

    public void getData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.flickr.com/services/").addConverterFactory(ScalarsConverterFactory.create()).build();
        ApiCallInterface apiCallInterface = retrofit.create(ApiCallInterface.class);
        Call<String> stringCall = apiCallInterface.STRING_CALL();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        List<String> image_url = parseArray(jsonObject);
                        GalleryImageAdapter imageAdapter = new GalleryImageAdapter(MainActivity.this, image_url);
                        recyclerView.setAdapter(imageAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private List<String> parseArray(JSONObject jsonObject) throws JSONException {
        List<String> url = new ArrayList<>();
        JSONObject photos = (JSONObject) jsonObject.getJSONObject("photos");
        JSONArray photo = (JSONArray) photos.getJSONArray("photo");
        for(int i=0;i<photo.length();i++)
        {
            JSONObject user_object = photo.getJSONObject(i);
            url.add(user_object.getString("url_s"));
        }
        return url;
    }

}