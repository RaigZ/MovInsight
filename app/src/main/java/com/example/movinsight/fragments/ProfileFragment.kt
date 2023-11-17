import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.movinsight.R
import com.example.movinsight.UserData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ProfileFragment : Fragment() {

    private val firestore = FirebaseFirestore.getInstance()
    private val collectionReference = firestore.collection("users")
    private lateinit var root: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root =  inflater.inflate(R.layout.fragment_profile, container, false)
        return root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDataFromFirebase()
    }

    private fun loadDataFromFirebase() {
        collectionReference.get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                // Check if the query snapshot is not null and contains documents
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val documentSnapshot = querySnapshot.documents[0]

                    // Map the document data to your data model
                    val dataModel = documentSnapshot.toObject(UserData::class.java)

                    // Update the UI with the retrieved data
                    updateUI(dataModel)
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure
            }
    }

    private fun updateUI(dataModel: UserData?) {
        // Update UI elements with the data from the data model

        val usernameTV = root.findViewById<TextView>(R.id.usernameTV)
        val emailTV = root.findViewById<TextView>(R.id.emailTV)
        val movieListTV = root.findViewById<TextView>(R.id.watchlistTV)

        usernameTV.text = "Field 1: ${dataModel?.username}"
        emailTV.text = "Field 2: ${dataModel?.email}"

        // Handle the array elements
        val arrayText = dataModel?.movieList?.joinToString(", ") ?: "No array elements"
        movieListTV.text = "Array Elements: $arrayText"
    }

}
