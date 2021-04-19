package com.example.imagegallery.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.imagegallery.repository.model.ImageModel;
import com.example.imagegallery.repository.ImageRepository;
import java.util.List;


public class ImageViewModel extends ViewModel {

    public MutableLiveData<List<ImageModel>> getImageListObserver() {
        ImageRepository imageRepository = new ImageRepository();
        return imageRepository.makeApiCall();
    }


}
