package com.example.qlks.Fragment.Auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.qlks.Dialog.DialogLoading;
import com.example.qlks.Firebase.FirebaseDAO;
import com.example.qlks.MainActivity;
import com.example.qlks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private ImageView imgExitApp;
    private EditText edtEmail;
    private EditText edtPassword;
    private ImageView imgVisible;
    private EditText edtRePassword;
    private ImageView imgReVisible;
    private AppCompatButton acbSignUp;
    private TextView tvSignIn;
    private FirebaseAuth mAuth;
    private DialogLoading diaLogLoading;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaLogLoading = new DialogLoading(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
    }

    private void registerForOnclick() {
        imgExitApp.setOnClickListener(this);
        edtEmail.setOnClickListener(this);
        edtPassword.setOnClickListener(this);
        imgVisible.setOnClickListener(this);
        acbSignUp.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
        imgReVisible.setOnClickListener(this);
    }


    private void initUi(View view) {
        imgExitApp = view.findViewById(R.id.img_BackToLogin);
        edtEmail = view.findViewById(R.id.edt_Email);
        edtPassword = view.findViewById(R.id.edt_Password);
        imgVisible = view.findViewById(R.id.img_Visible);
        edtRePassword = view.findViewById(R.id.edt_RePassword);
        imgReVisible = view.findViewById(R.id.img_ReVisible);
        acbSignUp = view.findViewById(R.id.acb_SignUp);
        tvSignIn = view.findViewById(R.id.tv_SignIn);
        imgVisible.setBackgroundResource(R.drawable.baseline_visibility_off_24);
        imgReVisible.setBackgroundResource(R.drawable.baseline_visibility_off_24);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_BackToLogin:
            case R.id.tv_SignIn:
                MainActivity.popBackStack(getActivity().getSupportFragmentManager());
                break;
            case R.id.acb_SignUp:
                handleRegister();
                break;
            case R.id.img_Visible:
                togglePasswordVisibility();
                break;
            case R.id.img_ReVisible:
                toggleRePasswordVisibility();

                break;
        }

    }

    private void toggleRePasswordVisibility() {

        if (MainActivity.isVisible) {
            edtRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgReVisible.setBackgroundResource(R.drawable.baseline_visibility_off_24);
        } else {
            edtRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgReVisible.setBackgroundResource(R.drawable.baseline_visibility_24);
        }
        MainActivity.isVisible = !MainActivity.isVisible;
    }

    private void togglePasswordVisibility() {
        if (MainActivity.isVisible) {
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgVisible.setBackgroundResource(R.drawable.baseline_visibility_off_24);

        } else {
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgVisible.setBackgroundResource(R.drawable.baseline_visibility_24);
        }
        MainActivity.isVisible = !MainActivity.isVisible;
    }

    private void handleRegister() {
        String email = edtEmail.getText().toString();
        String rePassword = edtRePassword.getText().toString();
        String password = edtPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(getContext(), R.string.login_empty_email, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MainActivity.regex.isValidEmail(email)) {
            Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(getContext(), R.string.login_empty_password, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!rePassword.equals(password)) {
            Toast.makeText(getContext(), "Re-entered password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() <= 6) {
            Toast.makeText(getContext(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        diaLogLoading.show();
        RegisterWithFirebase(email, password);
    }

    private void RegisterWithFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDAO.addUser(email, password, 0);
                    Toast.makeText(getContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                    diaLogLoading.cancel();
                } else {
                    Toast.makeText(getContext(), "Some thing went wrong! Try later", Toast.LENGTH_SHORT).show();
                    diaLogLoading.cancel();
                }
            }
        });
    }


}