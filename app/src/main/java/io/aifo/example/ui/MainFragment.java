package io.aifo.example.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.aifo.api.javassist.Inject;
import io.aifo.example.R;
import io.aifo.example.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {


    private FragmentMainBinding fragmentMainBinding;

//    @Inject
//    InfoAdapter infoAdapter ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_main, null, false);
        fragmentMainBinding = DataBindingUtil.bind(inflate);
//        fragmentMainBinding.rlView.setAdapter(infoAdapter);
//        infoAdapter.replaceData(new ArrayList<>());
//
        return inflate;
    }


}
