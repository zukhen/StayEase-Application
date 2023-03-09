package com.example.qlks.Fragment.introduction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlks.Fragment.Auth.LoginFragment;
import com.example.qlks.MainActivity;
import com.example.qlks.R;

public class SplashFragment extends Fragment {
    private LinearLayout layout_splash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        layout_splash.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
        startSplashCountdownTimer();


    }

    private void startSplashCountdownTimer() {
        new CountDownTimer(3000, 1500) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (MainActivity.appPrefs.isFirstLaunch()) {
                    MainActivity.switchToFragment(getActivity().getSupportFragmentManager(), new IntroduceFragment());
                    MainActivity.appPrefs.setFirstLaunch(false);
                } else {
                    MainActivity.switchToFragment(getActivity().getSupportFragmentManager(), new LoginFragment());

                }

            }
        }.start();
    }


    private void initUi(View view) {
        layout_splash = view.findViewById(R.id.layout_splash);
    }
}