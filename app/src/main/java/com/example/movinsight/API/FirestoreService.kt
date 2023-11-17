package com.example.movinsight.API

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.movinsight.Room.UserDao
import com.example.movinsight.Room.UserDatabase
import com.example.movinsight.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.movinsight.Room.User

class FirestoreService {

    companion object {
        private var db: FirebaseFirestore = Firebase.firestore
        private lateinit var userDB: UserDatabase
        private lateinit var userDao: UserDao

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
                                //put("password", query.get("password")!!)
                                put("username", query.get("username")!!)
                                put("watchlist", query.get("watchlist")!!)
                            }
                            viewModel.selectItem(user)
                            // Insert user into the Room database
                            val context = ApplicationProvider.getApplicationContext<Context>() // Get context
                            userDB = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java).build() // Get UserDatabase
                            userDao = userDB.userDao() // Get UserDao
                            val roomUser = User(query.get("email") as String, // Create user based on info
                                query.get("username") as String,
                                query.get("watchlist") as ArrayList<String>)
                            userDao.insertUser(roomUser) // Insert user into UserDatabase
                        }
                        //Log.d("FirestoreService file", "${task.result.documents}")
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
    }

}