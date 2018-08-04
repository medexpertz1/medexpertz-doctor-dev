package com.medexpertz.medexpertzdoctor.shankar.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.medexpertz.medexpertzdoctor.R
import uk.co.senab.photoview.PhotoViewAttacher
import android.widget.ImageView
import com.bumptech.glide.Glide


class DocumentFullScreen: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fullscreen_document)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val image = findViewById(R.id.documentImageIV) as ImageView
        Glide.with(this).load(intent.getStringExtra("image")).into(image)
        val photoViewAttacher = PhotoViewAttacher(image)
        photoViewAttacher.update();


    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}