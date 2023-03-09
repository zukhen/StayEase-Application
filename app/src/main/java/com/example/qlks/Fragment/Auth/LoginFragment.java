package com.example.qlks.Fragment.Auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.qlks.Dialog.DialogLoading;
import com.example.qlks.Fragment.MainFragment;
import com.example.qlks.MainActivity;
import com.example.qlks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private ImageView imgExitApp;
    private EditText edtEmai;
    private EditText edtPassword;
    private ImageView imgVisible;
    private CheckBox chkRemember;
    private AppCompatButton acbSignIn;
    private TextView tvForgotPassWord;
    private TextView tvSignUp;

    private boolean isRemember = false;
    public static FirebaseAuth mAuth;

    private DialogLoading diaLogLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaLogLoading = new DialogLoading(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        registerForOnclick();
    }

    private void fillUserDataFromPrefs() {
        edtEmai.setText(MainActivity.appPrefs.getEmailUser(getContext()));
        edtPassword.setText(MainActivity.appPrefs.getPasswordUser(getContext()));
        chkRemember.setChecked(MainActivity.appPrefs.getRemember(getContext()));
    }

    private void registerForOnclick() {
        imgExitApp.setOnClickListener(this);
        edtEmai.setOnClickListener(this);
        edtPassword.setOnClickListener(this);
        imgVisible.setOnClickListener(this);
        acbSignIn.setOnClickListener(this);
        tvForgotPassWord.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        chkRemember.setOnCheckedChangeListener((compoundButton, isChecked) -> isRemember = isChecked);
    }

    private void initUi(View view) {
        imgExitApp = view.findViewById(R.id.img_ExitApp);
        edtEmai = view.findViewById(R.id.edt_Email);
        edtPassword = view.findViewById(R.id.edt_Password);
        imgVisible = view.findViewById(R.id.img_Visible);
        chkRemember = view.findViewById(R.id.chk_Remember);
        acbSignIn = view.findViewById(R.id.acb_SignIn);
        tvForgotPassWord = view.findViewById(R.id.tv_ForgotPassword);
        tvSignUp = view.findViewById(R.id.tv_SignUp);

        diaLogLoading = new DialogLoading(getContext());
        imgVisible.setBackgroundResource(R.drawable.baseline_visibility_off_24);

        mAuth = FirebaseAuth.getInstance();
        fillUserDataFromPrefs();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_ExitApp:
                getActivity().finish();
                break;

            case R.id.acb_SignIn:
                handleLogin();
                break;
            case R.id.img_Visible:
                togglePasswordVisibility();
                break;
            case R.id.tv_SignUp:
                MainActivity.replaceFragmentBackStack(getActivity().getSupportFragmentManager(), new RegisterFragment());
                break;
            case R.id.tv_ForgotPassword:
                MainActivity.replaceFragmentBackStack(getActivity().getSupportFragmentManager(), new ForgotFragment());
                break;
        }
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

    private void handleLogin() {
        String email = edtEmai.getText().toString();
        String password = edtPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(getContext(), R.string.login_empty_email, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!MainActivity.regex.isValidEmail(email)) {
                Toast.makeText(getContext(), R.string.invalidate_email, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (password.isEmpty()) {
            Toast.makeText(getContext(), R.string.login_empty_password, Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity.appPrefs.remeberUser(getContext(), isRemember ? email : "", isRemember ? password : "", isRemember);
        diaLogLoading.show();
        checkLoginFirebase(email, password);
    }

    private void checkLoginFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
                    diaLogLoading.cancel();
                } else {

                    MainActivity.switchToFragment(getActivity().getSupportFragmentManager(), new MainFragment());
                    diaLogLoading.cancel();
                }
            }
        });
    }
}