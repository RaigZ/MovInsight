package com.example.movinsight.API

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movinsight.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import androidx.room.Room
import com.example.movinsight.Room.User
import com.example.movinsight.Room.UserDao
import com.example.movinsight.Room.UserDatabase
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class FirestoreService {

    companion object {
        private var db: FirebaseFirestore = Firebase.firestore

        //Function call to firestore to return query, will be utilized with room db to persist data through the app.
        fun getUser(email: String, viewModel: UserViewModel, context: Context){ //, viewModel: FirestoreViewModel
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener() { task ->
                    if(task.isSuccessful){
                        var query = task.result.documents[0]
                        if(query != null){
                            val user = buildMap<String, Any>{
                                put("email", query.get("email")!!)
                                //put("password", query.get("password")!!)
                                put("username", query.get("username")!!)
                                put("watchlist", query.get("watchlist")!!)
                            }
                            viewModel.selectItem(user)

                            //Log.d("FLAG 1", "BEFORE ENTERING COROUTINE")
                            // Insert user into the Room database
                            viewModel.viewModelScope.launch {
                                //Log.d("FLAG 2", "RIGHT BEFORE FUNCTION CALL")
                                addUserToRoomDB(query.get("email") as String,
                                    query.get("username") as String,
                                    query.get("watchlist") as ArrayList<String>,
                                    context)
                                //Log.d("FLAG 5", "FUNCTION FULLY EXECUTED")
                            }
                        }
                        //Log.d("FirestoreService file", "${task.result.documents}")
                    } else {
                        Firebase.auth.signOut()
                        Log.d("FirestoreService file", "not successfull")
                    }
                }
        }

        fun getUser(email: String, callback: (result: ArrayList<String>) -> Unit){
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener() { task ->
                    if(task.isSuccessful){
                        var query = task.result.documents[0]
                        if(query != null){
                            callback(query.get("watchlist") as ArrayList<String>)
                        }
                    } else {
                        Firebase.auth.signOut()
                        Log.d("FirestoreService file", "not successfull")
                    }
                }
        }

        //Function call to create user in Firestore storage
        fun createUser(username: String, email: String, viewModel: UserViewModel, context: Context){
            val user = buildMap<String, Any>{
                put("username", username)
                put("email", email)
                put("watchlist", ArrayList<String>())
            }
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(context, "Account creation Success!", Toast.LENGTH_LONG).show()
                    viewModel.selectItem(user)
                }
                .addOnFailureListener {
                    Firebase.auth.signOut()
                    Toast.makeText(context, "Account creation failed!", Toast.LENGTH_LONG).show()
                }
        }

        fun addToWatchlist(email: String, movieName: String) {
            getUser(email) { result ->
                //Result contains the watchlist from current user
            }
        }

        // Adds user to User database in Room
        private suspend fun addUserToRoomDB(email: String, username: String, watchlist: ArrayList<String>, context: Context)
        {
            //Log.d("FLAG 3", "FUNCTION ENTERED")
            val userDB = Room.databaseBuilder(
                context,
                UserDatabase::class.java, "user"
            ).build()
            //Log.d("FLAG 4", "DATABASE BUILT")
            val userDao = userDB.userDao() // Get UserDao
            val roomUser = User(email, username, watchlist) // Create user based on given info
            userDao.insertUser(roomUser) // Insert user into UserDatabase
        }
    }

}