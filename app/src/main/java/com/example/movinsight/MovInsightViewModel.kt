package com.example.movinsight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User

class MovInsightViewModel: ViewModel() {
    private val mutableSelectedItem = MutableLiveData<FirebaseAuth>()
    val selectedItem: LiveData<FirebaseAuth> get() = mutableSelectedItem

    fun selectItem(item: FirebaseAuth){
        mutableSelectedItem.value = item
    }
}