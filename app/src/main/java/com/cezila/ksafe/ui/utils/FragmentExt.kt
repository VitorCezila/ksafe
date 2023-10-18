package com.cezila.ksafe.ui.utils

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

fun Fragment.navTo(@IdRes dest: Int) = findNavController().navigate(dest)
fun Fragment.navTo(@IdRes dest: Int, args: Bundle? = null) = findNavController().navigate(dest, args)
fun Fragment.toast(msg: String?) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Fragment.showSnackbar(
    view: View,
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    actionCallback: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(view, message, duration)
    actionText?.let { text ->
        snackbar.setAction(text) { actionCallback?.invoke() }
    }
    snackbar.show()
}