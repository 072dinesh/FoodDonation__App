package com.example.fooddonationapp.utils

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.fooddonationapp.R

fun Fragment.getStatusBarHeight(): Int {
    val statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(statusBarHeightId)
}

fun Fragment.setLightStatusBar() {
    activity?.window?.decorView?.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}

fun Fragment.setDarkStatusBar() {
    activity?.window?.decorView?.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

//fun Fragment.createLoadingAlert(): KAlertDialog {
//    return KAlertDialog(requireContext(), KAlertDialog.PROGRESS_TYPE).apply {
//        setCancelable(false)
//    }
//}

fun Fragment.createLoadingAlert(): AlertDialog {
    val dialog = AlertDialog.Builder(requireContext(), R.style.Custom_Loading_Dialog)
    dialog.setView(R.layout.loading_dialog)
    dialog.setCancelable(false)

    return dialog.create()
}

fun Fragment.showAlert(
    title: String,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String?,
    showNegativeButton: Boolean,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
){
    val alert = AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, which ->
            dialog.dismiss()
            onPositiveClick()
        }

    if(showNegativeButton){
        alert.setNegativeButton(negativeButtonText){ dialog, which ->
            dialog.dismiss()
            onNegativeClick()
        }
    }

    alert.show()
}