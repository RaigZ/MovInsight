package com.example.movinsight.API

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class FirestoreService {
    companion object {
        private var db: FirebaseFirestore = Firebase.firestore
        private var currentUserEmail: String = ""
        private var currentUsername: String = ""
        private var currentUserId: String = ""
        private var currentWatchlist: MutableList<String> = mutableListOf()

        fun instance(): FirestoreService = FirestoreService()

        fun getUserV2(viewModel: UserViewModel, context: Context){
            if(currentUserId.isNotEmpty()){
                db.collection("users").document(currentUserId)
                    .get()
                    .addOnSuccessListener { document ->
                        if(document != null){
                            val user = buildMap<String, Any> {
                                put("email", document.getString("username")!!)
                                put("username", document.getString("username")!!)
                                put("watchlist", document.get("watchlist")!!)
                            }
                            currentUsername = document.getString("username") ?: ""
                            currentUserEmail = document.getString("email") ?: ""
                            currentWatchlist = (document.get("watchlist") as List<String>).toMutableList()
                            viewModel.selectItem(user)


                            // Insert user into the Room database
                            viewModel.viewModelScope.launch {
                                addUserToRoomDB(
                                    document.getString("username") as String,
                                    document.getString("email") as String,
                                    document.get("watchlist") as ArrayList<String>,
                                    context
                                )
                            }

                        }
                    }
                    .addOnFailureListener {
                        Firebase.auth.signOut()
                        Log.d("FirestoreService", "GetUserv2 failed")
                    }
            }
        }

        fun getUserV2(viewModel: UserViewModel){
            if(currentUserId.isNotEmpty()){
                db.collection("users").document(currentUserId)
                    .get()
                    .addOnSuccessListener { document ->
                        if(document != null){
                            val user = buildMap<String, Any> {
                                put("email", document.getString("email")!!)
                                put("username", document.getString("username")!!)
                                put("watchlist", document.get("watchlist")!!)
                            }
                            currentUsername = document.getString("username") ?: ""
                            currentUserEmail = document.getString("email") ?: ""
                            currentWatchlist = (document.get("watchlist") as List<String>).toMutableList()
                            viewModel.selectItem(user)
                        }
                    }
                    .addOnFailureListener {
                        Firebase.auth.signOut()
                        Log.d("FirestoreService", "GetUserv2 failed")
                    }
            }
        }

        //Function call to firestore to return query, will be utilized with room db to persist data through the app.
        @Deprecated("Use getUserV2")
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

                            /*
                            // Insert user into the Room database
                            viewModel.viewModelScope.launch {
                                addUserToRoomDB(query.get("email") as String,
                                    query.get("username") as String,
                                    query.get("watchlist") as ArrayList<String>,
                                    context)
                            }*/
                            //currentUserEmail = query.get("email") as String ?: ""
                            //currentDocId = query.id
                        }
                        //Log.d("FirestoreService file", "${task.result.documents}")
                    } else {
                        Firebase.auth.signOut()
                        //currentUserEmail = ""
                        //currentDocId = ""
                        Log.d("FirestoreService file", "not successfull")
                    }
                }
        }

        /*
        * Creates user account based off the Firebase auth user id, makes updating and getting user info easier.
        * */
        fun createUserAccount(uid: String, username: String, email: String, viewModel: UserViewModel, context: Context) {
            val user = buildMap<String, Any>{
                put("username", username)
                put("email", email)
                put("watchlist", ArrayList<String>())
            }
            db.collection("users").document(uid)
                .set(user)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        currentUserId = uid
                        currentUsername = username
                        currentUserEmail = email
                        Toast.makeText(context, "Account creation Success!", Toast.LENGTH_LONG).show()
                        viewModel.selectItem(user)
                    } else {
                        reset()
                        Firebase.auth.signOut()
                        Toast.makeText(context, "Account creation failed!", Toast.LENGTH_LONG).show()
                    }
                }
            /*
            // Insert user into the Room database
            viewModel.viewModelScope.launch {
                addUserToRoomDB(
                    username,
                    email,
                    context
                )
            }
            */
            return
        }

        //Function call to create user in Firestore storage
        @Deprecated("Use createUserAccount")
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
                    reset()
                    Firebase.auth.signOut()
                    Toast.makeText(context, "Account creation failed!", Toast.LENGTH_LONG).show()
                }
        }

        /*
        * Adds movie to list inside of firestoreService and updates the corresponding users watchlist on firestore.
        * */
        fun addToWatchlist(movieName: String) {
            currentWatchlist.add(movieName)
            db.collection("users").document(currentUserId)
                .update("watchlist", ArrayList<String>(currentWatchlist))
                .addOnSuccessListener {
                    Log.d("FirebaseService","Successfully saved movie")
                }
                .addOnFailureListener {
                    Log.d("FirebaseService", "Failed to save movie")
                }

        }

        fun getUserEmail(): String = currentUserEmail
        fun getCurrentUserId(): String = currentUserId
        fun getUsername(): String = currentUsername
        fun getWatchlist(): MutableList<String> = currentWatchlist
        fun setUserEmail(userEmail: String) {
            currentUserEmail = userEmail
        }
        fun setCurrentUserId(id: String){
            currentUserId = id
        }

        fun setUsername(username: String){
            currentUsername = username
        }
        private fun reset(){
            currentUserId = ""
            currentUsername = ""
            currentUserEmail = ""
            currentWatchlist = mutableListOf()
        }

        // Adds user to User database in Room
        private suspend fun addUserToRoomDB(username: String, email: String, watchlist: ArrayList<String>, context: Context)
        {
            val db = UserDatabase.getInstance(context)
            val userDao = db.userDao()
            //val watchlist: ArrayList<String> = ArrayList()
            val roomUser = User(username, email, watchlist, "none") // Create user based on given info
            userDao.insertUser(roomUser) // Insert user into UserDatabase
        }
    }

}