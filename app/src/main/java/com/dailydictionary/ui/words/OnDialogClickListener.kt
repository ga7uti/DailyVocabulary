package com.dailydictionary.ui.words

import com.dailydictionary.db.entity.Dictionary

interface OnDialogClickListener {
    fun edit(word: Dictionary)
    fun delete(word: Dictionary)
}