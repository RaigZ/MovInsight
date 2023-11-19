package com.example.movinsight

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.movinsight.API.APIInterface
import com.example.movinsight.API.searchItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ApiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var root: View
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_api, container, false)
        db = Firebase.firestore
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.findViewById<Button>(R.id.apiSave).setOnClickListener {
            saveJsonData()
        }
    }

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }

    private fun saveJsonData() {
        val data = retrofitBuilder.getAPIData()
        data.enqueue(object: Callback<List<searchItem>?> {
            override fun onResponse(
                call: Call<List<searchItem>?>,
                response: Response<List<searchItem>?>
            ) {
                val responseReturn = response.body()!!
                for(element in responseReturn.take(2)){
                    //Populate map
                    val data = buildMap<String, Any> {
                        put("id", element.id)
                        put("qid", element.qid)
                        put("title", element.title)
                        put("year", element.year)
                        put("stars", element.stars)
                        put("q", element.q)
                        put("image", element.image)
                    }

                    //Add to placeholder collection
                    db.collection("placeholder")
                        .add(data)
                        .addOnSuccessListener {
                            //Toast.makeText(context, "Account Created!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Account Creation Failed!", Toast.LENGTH_LONG).show()
                        }
                }
            }

            override fun onFailure(call: Call<List<searchItem>?>, t: Throwable) {
                Log.d("APIFAIL", "message" +  t.message)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ApiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ApiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}