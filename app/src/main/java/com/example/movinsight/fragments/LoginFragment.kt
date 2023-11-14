package com.example.movinsight.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.movinsight.MovInsightViewModel
import com.example.movinsight.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    //private var firebaseService = FirebaseService()
    private val viewModel: MovInsightViewModel by activityViewModels()
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("LoginFragment", "${currentUser}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_login, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        root.findViewById<Button>(R.id.testButton).setOnClickListener {
            //firebaseService.logOut()
            FirebaseAuth.getInstance().signOut()
            //auth.signOut()
            //logOut()
        }

        root.findViewById<Button>(R.id.submitLogin).setOnClickListener {
            val email = root.findViewById<TextInputEditText>(R.id.emailInputLogin).text.toString()
            val password = root.findViewById<TextInputEditText>(R.id.passwordInputLogin).text.toString()
            //firebaseService.signIn(email, password)
            signIn(email, password)
            //val user = firebaseService.getUser()
            //viewModel.selectItem(user)
            if(auth.currentUser != null){
                viewModel.selectItem(auth.currentUser!!)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d("FirebaseService", "${auth.currentUser}")
        if(auth.currentUser != null){
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.d("FirebaseService", "login with email/password: Success!")
                    //user = auth.currentUser
                } else {
                    Log.d("FirebaseService", "Login with email/password: Failed!", task.exception)
                }
            }
    }
}