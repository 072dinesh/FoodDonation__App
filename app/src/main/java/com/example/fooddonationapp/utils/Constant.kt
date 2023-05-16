package com.example.fooddonationapp.utils

object Constant {
    const val IS_LOGIN = "IS_LOGIN"
    const val IS_INTRO_COMPLETE = "IS_INTRO_COMPLETE"
    const val BOTTOM_NAV_BROADCAST_NAME = "BOTTOM_NAV_BROADCAST_NAME"

    interface BROADCAST_RECEIVER {
        companion object{
            const val REQUEST_FORM = "com.example.fooddonationapp.request_form"
        }
    }

}