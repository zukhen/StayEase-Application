package com.example.qlks.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.qlks.R;

public class DialogLoading extends Dialog {
    public DialogLoading(Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
        ImageView img_logo = findViewById(R.id.img_logo);
        img_logo.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.spin));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }
}
