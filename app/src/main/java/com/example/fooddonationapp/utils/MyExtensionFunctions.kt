package com.example.fooddonationapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Any?.toast(context: Context, isShort: Boolean = false): Toast {
    return Toast.makeText(
        context,
        this.toString(),
        if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).apply { show() }
}

fun String.snackBar(view: View, context: Context?=null) {
    val imm =
        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)

    val snackbar =
        Snackbar.make(view, this, Snackbar.LENGTH_LONG)
    val snackbarView = snackbar.view
    val snackTextView =
        snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
    snackTextView.maxLines = 4
    snackbar.show()
}