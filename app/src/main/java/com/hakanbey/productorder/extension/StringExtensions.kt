package com.hakanbey.productorder.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Hakan AKKAYA  │
//│ ──────────────────────── │
//│ contact@hakanakkaya.net  │            
//│ ──────────────────────── │
//│ 22.01.2022               │
//└──────────────────────────┘

fun String.toMoneyFormat(): String {
    val format: DecimalFormat = DecimalFormat("###,###,###.##", DecimalFormatSymbols.getInstance(Locale.ROOT))
    val price: Double = this.toDouble()
    return format.format(price)
}