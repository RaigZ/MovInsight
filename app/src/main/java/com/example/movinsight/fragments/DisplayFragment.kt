package com.example.movinsight.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movinsight.API.APIInterface
import com.example.movinsight.API.APIService.imdbAPI
import com.example.movinsight.API.SearchMovieResponse
import com.example.movinsight.API.searchItem
import com.example.movinsight.R
import com.example.movinsight.RecyclerView.RVAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    lateinit var movieAdapter: RVAdapter
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
        root = inflater.inflate(R.layout.fragment_display, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var movieAdapter = RVAdapter(emptyList())
        recyclerView.adapter = movieAdapter
        db = Firebase.firestore

        fun searchMovie(api: APIInterface, adapter: RVAdapter) {
            val data = api.searchIMDB("Lordoftherings")
            data.enqueue(object: Callback<SearchMovieResponse> {
                override fun onResponse(
                    call: Call<SearchMovieResponse>,
                    response: Response<SearchMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("ApiResponse", "${apiResponse}")
                        apiResponse?.let {
                            adapter.setData(it)
                        }
                    } else {
                        // Log the error response
                        Log.e("ApiResponseError", response.errorBody()?.string() ?: "Unknown error")
                    }
                }

                override fun onFailure(call: Call<SearchMovieResponse>, t: Throwable) {
                    Log.d("APIFAIL", "message" +  t.message)
                }
            })
        }
        searchMovie(imdbAPI, movieAdapter)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    fun fetchData(){
        db.collection("placeholder")
            .get()
            .addOnSuccessListener {task ->
                //Log.d("Inside display fragment", "${task.documents.get(0)}")
                if(task.isEmpty){

                    Toast.makeText(context, "Query empty", Toast.LENGTH_LONG).show()
                } else {
                    var data = mutableListOf<searchItem>()
                    for(element in task){
                        val id = (element.data["id"] as String)
                        val qid = (element.data["qid"] as String)
                        val title = element.data["title"] as String
                        val year = (element.data["year"] as Long).toInt()
                        val stars = (element.data["stars"] as String)
                        val q = (element.data["q"] as String)
                        val image = (element.data["image"] as String)


                        data.add(searchItem(id, qid, title, year, stars, q, image))
                    }
                    Log.d("Testing", "${data.size}")
                    recyclerView.adapter = RVAdapter(data)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Query failed!", Toast.LENGTH_LONG).show()
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisplayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}