package com.example.movinsight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {
    private val mutableUserItem = MutableLiveData<Map<String, Any>>()
    val selectedItem: LiveData<Map<String, Any>> get() = mutableUserItem
    fun selectItem(item: Map<String, Any>){
        mutableUserItem.value = item
    }
}