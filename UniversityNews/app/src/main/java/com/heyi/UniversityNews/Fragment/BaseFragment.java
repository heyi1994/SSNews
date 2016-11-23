package com.heyi.UniversityNews.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by heyi on 2016/11/23.
 */

public abstract class BaseFragment extends Fragment {
    private Activity mActivity;
    public BaseFragment(Activity activity){
        mActivity=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public abstract View initView();
    public void initData(){}
}
