package com.dailydictionary.ui.words

import com.dailydictionary.data.entity.Dictionary

interface OnDialogClickListener {
    fun edit(word: Dictionary)
    fun delete(word: Dictionary)
}