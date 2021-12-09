package com.example.gbbank.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.gbbank.R

fun ImageView.setImage(url: String?) {
    if(!url.isNullOrEmpty()) {
        Glide.with(context).load(url).placeholder(R.color.browser_actions_bg_grey).error(R.color.browser_actions_bg_grey).into(this)
    } else {
        setImageResource(R.color.browser_actions_bg_grey)
    }
}