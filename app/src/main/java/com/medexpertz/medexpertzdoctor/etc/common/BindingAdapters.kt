package com.medexpertz.medexpertzdoctor.etc.common

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.lzyzsd.circleprogress.DonutProgress
import com.wang.avi.AVLoadingIndicatorView

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 23 Nov 2017 at 5:35 PM
 */

@BindingAdapter("android:error")
fun setError(view: TextInputLayout, error: String?) {
    view.error = error
}

@BindingAdapter("android:stateLoading")
fun setStateLoading(view: AVLoadingIndicatorView, isLoading: Boolean) {
    if (isLoading) view.smoothToShow()
    else view.smoothToHide()
}

@BindingAdapter("android:donut_progress")
fun setProgress(view: DonutProgress, progress: Float) {
    view.progress = progress
}

@BindingAdapter("android:url", "android:error", requireAll = false)
fun setUrl(imageView: ImageView, url: String?, error: Drawable? = null) {
    Glide.with(imageView.context)
            .load(url)
            /*  .centerCrop()
              .error(error)*/
            .into(imageView)
}

@BindingAdapter("android:nestedScrollEnabled")
fun setNestedScrollingEnabled(view: RecyclerView, enabled: Boolean) {
    view.isNestedScrollingEnabled = enabled
}

@BindingAdapter("android:divider")
fun setDivider(view: RecyclerView, enabled: Boolean) {
    if (enabled) view.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
}

@BindingAdapter("app:logo")
fun setLogo(toolbar: Toolbar, logo: String?) {
    Glide.with(toolbar.context)
            .asBitmap()
            .load(logo)
            .into(object : SimpleTarget<Bitmap>(144, 144) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    toolbar.logo = BitmapDrawable(toolbar.resources, resource)
                }
            })
}