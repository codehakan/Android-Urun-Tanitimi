package com.hakanbey.productorder.extension

import android.widget.EditText
import java.util.regex.Pattern

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

private const val EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+.(com|org|net|edu|gov|mil|biz|info|mobi)(.[A-Z]{2})?$"

/**
 * Kullanıcının girdiği e-posta adresinin geçerli bir
 * e-posta adresi olup olmadığını true-false şeklinde dönderir.
 * Geçerli=>true
 * Geçersiz=>false
 */
fun EditText.isValidEmail(): Boolean {
    val data: String = this.text.toString()
    val pattern: Pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE)
    return pattern.matcher(data).find()
}

/**
 * Boşluk var mı?
 * Evet => true
 * Hayır => false
 */
fun EditText.isHaveSpace(): Boolean {
    if (this.length() > 0) {
        val data: String = this.text.toString()
        for (element in data) {
            if (element == ' ') {
                return true
            }
        }
    }
    return false
}