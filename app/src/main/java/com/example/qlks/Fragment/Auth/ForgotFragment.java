package com.example.qlks.Fragment.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.qlks.Dialog.DialogLoading;
import com.example.qlks.MainActivity;
import com.example.qlks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotFragment extends Fragment implements View.OnClickListener {

    private ImageView imgBackToLogin;
    private EditText edtEmail;
    private AppCompatButton acbContinue;
    private FirebaseAuth mAuth;

    private DialogLoading diaLogLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
    }

    private void registerForOnclick() {
        imgBackToLogin.setOnClickListener(this);
        acbContinue.setOnClickListener(this);
    }

    private void initUi(View view) {
        imgBackToLogin = view.findViewById(R.id.img_BackToLogin);
        edtEmail = view.findViewById(R.id.edt_Email);
        acbContinue = view.findViewById(R.id.acb_Continue);
        diaLogLoading = new DialogLoading(getContext());
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_BackToLogin:
                MainActivity.popBackStack(getActivity().getSupportFragmentManager());
                break;
            case R.id.acb_Continue:
                handleForgotPassword(edtEmail.getText().toString());
                break;
        }
    }

    private void handleForgotPassword(String email) {
        if (!MainActivity.regex.isValidEmail(email)) {
            Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }
        diaLogLoading.show();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "Account not found", Toast.LENGTH_SHORT).show();
                            diaLogLoading.cancel();
                        } else {
                            diaLogLoading.cancel();
                            MainActivity.switchToFragment(getActivity().getSupportFragmentManager(), new SuccessFragment());
                        }
                    }
                });
    }
}