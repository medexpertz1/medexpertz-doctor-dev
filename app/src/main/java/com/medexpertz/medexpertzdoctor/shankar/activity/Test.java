package com.medexpertz.medexpertzdoctor.shankar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.medexpertz.medexpertzdoctor.shankar.model.DaysSelection;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
    ArrayList<DaysSelection> list = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
