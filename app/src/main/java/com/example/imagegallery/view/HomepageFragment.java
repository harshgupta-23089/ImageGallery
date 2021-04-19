package com.example.imagegallery.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagegallery.R;
import com.example.imagegallery.repository.model.ImageModel;
import com.example.imagegallery.view.adapters.GalleryImageAdapter;
import com.example.imagegallery.viewmodel.ImageViewModel;

import java.util.List;

public class HomepageFragment extends Fragment {
    private List<ImageModel> imageModelList;
    private GalleryImageAdapter adapter;
    private ImageViewModel viewModel;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment_layout, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.images);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GalleryImageAdapter(getActivity(), imageModelList);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        viewModel.getImageListObserver().observe(getViewLifecycleOwner(), new Observer<List<ImageModel>>() {
            @Override
            public void onChanged(List<ImageModel> imageModels) {
                if (imageModels != null) {
                    imageModelList = imageModels;
                    adapter.setUrl_list(imageModels);
                }

            }
        });

        return view;
    }
}
