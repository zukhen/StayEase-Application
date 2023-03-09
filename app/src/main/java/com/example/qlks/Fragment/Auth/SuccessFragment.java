package com.example.qlks.Fragment.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlks.MainActivity;
import com.example.qlks.R;

public class SuccessFragment extends Fragment implements View.OnClickListener {
    private ImageView imgBackToLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
    }

    private void registerForOnclick() {
        imgBackToLogin.setOnClickListener(this);
    }

    private void initUi(View view) {
        imgBackToLogin = view.findViewById(R.id.img_BackToLogin);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_BackToLogin) {
            MainActivity.switchToFragment(getActivity().getSupportFragmentManager(), new LoginFragment());
        }
    }
}