package com.dailydictionary.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


class Utils {
    companion object {
        fun hideKeyBoard(activity: Activity?) {
            if (activity != null) {
                val view: View? = activity.currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
    }
}