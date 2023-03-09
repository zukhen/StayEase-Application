package com.example.qlks;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.qlks.Fragment.Auth.LoginFragment;
import com.example.qlks.Fragment.introduction.SplashFragment;
import com.example.qlks.Receiver.NetworkChangeReceiver;
import com.example.qlks.Utils.AppPreferences;
import com.example.qlks.Utils.Regex;

public class MainActivity extends AppCompatActivity {
    public static AppPreferences appPrefs;
    public static Regex regex;
    public static boolean isVisible;
    private NetworkChangeReceiver broadcastReceiver;

    public static AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        switchToFragment(getSupportFragmentManager(), new SplashFragment());

    }



    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof LoginFragment) {
            //nhấn nút back từ màn hình Login sẽ thoát ứng dụng
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void initUi() {
        appPrefs = new AppPreferences(getApplicationContext());
        regex = new Regex();
        isVisible = false;
        broadcastReceiver = new NetworkChangeReceiver();
    }

    public static void replaceFragmentBackStack(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fl_container, fragment).addToBackStack("").commit();
    }

    public static void switchToFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit();
    }

    public static void popBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
    }
}