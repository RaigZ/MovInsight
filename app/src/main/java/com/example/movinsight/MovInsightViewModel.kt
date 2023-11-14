package com.example.movinsight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User

class MovInsightViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<FirebaseUser>()
    val selectedItem: LiveData<FirebaseUser> get() = mutableSelectedItem

    fun selectItem(item: FirebaseUser){
        mutableSelectedItem.value = item
    }
}