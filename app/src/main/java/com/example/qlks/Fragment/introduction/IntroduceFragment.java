package com.example.qlks.Fragment.introduction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.qlks.MainActivity;
import com.example.qlks.R;


public class IntroduceFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout rl_introduce1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduce, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
        rl_introduce1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));

    }

    private void registerForOnclick() {
        rl_introduce1.setOnClickListener(this);
    }

    private void initUi(View view) {
        rl_introduce1 = view.findViewById(R.id.rl_introduce1);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_introduce1) {
            MainActivity.switchToFragment(getActivity().getSupportFragmentManager(),new MainSplash());
        }
    }
}