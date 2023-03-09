package com.example.qlks.Fragment.introduction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlks.Adapter.ViewPagerAdapter;
import com.example.qlks.Fragment.Auth.LoginFragment;
import com.example.qlks.MainActivity;
import com.example.qlks.R;


public class MainSplash extends Fragment implements View.OnClickListener {

    private ViewPager2 vp_Slider;
    private AppCompatButton acb_Next, acb_Skip;
    private int images[] = {R.drawable.background2, R.drawable.background3, R.drawable.background4};
    private int headings[] = {R.string.heading_one, R.string.heading_two, R.string.heading_three};
    private int descriptions[] = {R.string.lorem, R.string.lorem, R.string.lorem};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_splash, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
        // Disable the swipe between ViewPager's slides
        vp_Slider.setUserInputEnabled(false);


    }

    private void registerForOnclick() {
        acb_Next.setOnClickListener(this);
        acb_Skip.setOnClickListener(this);
    }

    private void initUi(View view) {
        vp_Slider = view.findViewById(R.id.vp_Slider);
        acb_Next = view.findViewById(R.id.acb_Next);
        acb_Skip = view.findViewById(R.id.acb_Skip);
        //init Adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), images, headings, descriptions);
        //set Adapter
        vp_Slider.setAdapter(viewPagerAdapter);
        vp_Slider.setOffscreenPageLimit(2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acb_Skip:
                MainActivity.switchToFragment(getActivity().getSupportFragmentManager(),new LoginFragment());
                break;
            case R.id.acb_Next:
                int currentItem = vp_Slider.getCurrentItem();
                if (currentItem < 2) {
                    vp_Slider.setCurrentItem(currentItem + 1, false);
                } else {
                    MainActivity.switchToFragment(getActivity().getSupportFragmentManager(),new LoginFragment());
                }
                break;
        }
    }


}