package com.cezila.passwordmanager.ui.utils

import android.view.View

fun View.enable(isEnable: Boolean) {
    visibility = if(isEnable) View.VISIBLE else View.GONE
}