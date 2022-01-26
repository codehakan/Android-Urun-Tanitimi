package com.hakanbey.productorder.extension

import android.view.View

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

/**
 * Görünümü Göster
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * Görünümü Gizle
 */
fun View.hide() {
    this.visibility = View.GONE
}

/**
 * Enabled=>true
 */
fun View.enable() {
    this.isEnabled = true
}

/**
 * Enabled=>false
 */
fun View.disable() {
    this.isEnabled = false
}