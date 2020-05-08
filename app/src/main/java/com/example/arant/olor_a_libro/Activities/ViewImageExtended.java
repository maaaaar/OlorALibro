package com.example.arant.olor_a_libro.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.arant.olor_a_libro.R;

public class ViewImageExtended extends AppCompatDialogFragment
{
    public Bitmap PICTURE_SELECTED;
    public static ViewImageExtended newInstance(Bundle arguments) {
        Bundle args = arguments;
        ViewImageExtended fragment = new ViewImageExtended();
        fragment.setArguments(args);
        return fragment;
    }

}
