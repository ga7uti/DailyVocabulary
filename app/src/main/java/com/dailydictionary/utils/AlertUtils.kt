package com.dailydictionary.utils

import android.content.Context
import android.content.LocusId
import android.widget.Toast
import androidx.annotation.StringDef
import androidx.annotation.StringRes
import com.dailydictionary.R

class AlertUtils {

    companion object {
        fun showToast(context: Context, @StringRes message: Int) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}