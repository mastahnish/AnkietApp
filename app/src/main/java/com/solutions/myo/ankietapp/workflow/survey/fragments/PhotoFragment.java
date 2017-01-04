package com.solutions.myo.ankietapp.workflow.survey.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.databinding.FragmentPhotoBinding;


public class PhotoFragment extends Fragment {

    private static final String TAG = PhotoFragment.class.getSimpleName();

    private FragmentPhotoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false);
        View root = binding.getRoot();
        return root;
    }

    //TODO implement face tracking and getting happines data from it

}
