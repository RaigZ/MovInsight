package com.example.movinsight.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.movinsight.API.FirestoreService
import com.example.movinsight.UserViewModel
import com.example.movinsight.MovInsightViewModel
import com.example.movinsight.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private var db = FirestoreService
    //View models
    private val viewModel: MovInsightViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_signup, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.findViewById<Button>(R.id.submitSignup).setOnClickListener {
            val username = root.findViewById<TextInputEditText>(R.id.usernameInputSignup).text.toString()
            val email = root.findViewById<TextInputEditText>(R.id.emailInputSignup).text.toString()
            val password = root.findViewById<TextInputEditText>(R.id.passwordInputSignup).text.toString()

            if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty())
                signUp(username, email, password)
            else
                Toast.makeText(context, "Please fill every field", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun signUp(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FirebaseService", "Created user with email/password successfully")
                    //viewModel.selectItem(auth)
                    root.findViewById<TextInputEditText>(R.id.usernameInputSignup).setText("")
                    root.findViewById<TextInputEditText>(R.id.emailInputSignup).setText("")
                    root.findViewById<TextInputEditText>(R.id.passwordInputSignup).setText("")
                    //Need to save user first then pass through userViewModel
                    db.createUser(username, email, userViewModel, requireContext())
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show()
                    Log.d("FirebaseService", "Signup with email/password unsuccessful", task.exception)
                }
            }
    }
}