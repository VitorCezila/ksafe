package com.cezila.passwordmanager.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun copyToClipboard(text: String, context: Context) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("password", text)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(
        context,
        "Senha copiada para a área de transferência",
        Toast.LENGTH_SHORT
    ).show()
}