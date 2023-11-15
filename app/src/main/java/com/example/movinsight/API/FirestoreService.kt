package com.example.movinsight.API

import android.util.Log
import com.example.movinsight.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FirestoreService {

    companion object {
        private var db: FirebaseFirestore = Firebase.firestore

        //Function call to firestore to return query, will be utilized with room db to persist data through the app.
        fun getUser(email: String, viewModel: UserViewModel){ //, viewModel: FirestoreViewModel
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener() { task ->
                    if(task.isSuccessful){
                        var query = task.result.documents[0]
                        if(query != null){
                            val user = buildMap<String, Any>{
                                put("email", query.get("email")!!)
                                put("password", query.get("password")!!)
                                put("username", query.get("username")!!)
                            }
                            viewModel.selectItem(user)
                        }
                        //Log.d("FirestoreService file", "${task.result.documents}")
                    } else {
                        Log.d("FirestoreService file", "not successfull")
                    }
                }
        }

        //Function call to create user in Firestore storage
        fun createUser(email: String, viewModel: UserViewModel){}
    }

}