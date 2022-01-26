package com.hakanbey.productorder.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

fun ImageView.loadUri(url: String) {
    Glide.with(this.context).load(url).into(this)
}