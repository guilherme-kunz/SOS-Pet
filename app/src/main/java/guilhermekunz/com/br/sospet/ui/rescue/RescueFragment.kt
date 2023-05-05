package guilhermekunz.com.br.sospet.ui.rescue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentRescueBinding
import guilhermekunz.com.br.sospet.model.Rescue

class RescueFragment : Fragment() {

    private var _binding: FragmentRescueBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var rescueRecyclerView: RecyclerView
    private lateinit var rescueArrayList: ArrayList<Rescue>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRescueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfoDialog()
        setupRecyclerView()
        getRescueData()
    }

    private fun setupRecyclerView() {
        rescueRecyclerView = binding.fragmentRescueRecyclerView
        rescueRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        rescueRecyclerView.setHasFixedSize(true)
        rescueArrayList = arrayListOf<Rescue>()
    }

    private fun getRescueData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("rescue")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (rescueSnapshot in snapshot.children) {
                        val rescue = rescueSnapshot.getValue(Rescue::class.java)
                        rescueArrayList.add(rescue!!)
                    }
                    rescueRecyclerView.adapter = RescueAdapter(rescueArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showInfoDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_rescue_info_dialog_title))
            .setMessage(resources.getString(R.string.fragment_rescue_info_dialog_content))
            .setNeutralButton(resources.getString(R.string.fragment_rescue_info_dialog_action)) { _, _ ->
            }
            .show()
    }

}