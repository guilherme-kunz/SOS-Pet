package guilhermekunz.com.br.sospet.ui.wanted

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
import guilhermekunz.com.br.sospet.databinding.FragmentWantedBinding
import guilhermekunz.com.br.sospet.model.Rescue
import guilhermekunz.com.br.sospet.model.Wanted
import guilhermekunz.com.br.sospet.ui.rescue.RescueAdapter

class WantedFragment : Fragment() {

    private var _binding: FragmentWantedBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var rescueRecyclerView: RecyclerView
    private lateinit var wantedArrayList: ArrayList<Wanted>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWantedBinding.inflate(inflater, container, false)
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
        rescueRecyclerView = binding.fragmentWantedRecyclerView
        rescueRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        rescueRecyclerView.setHasFixedSize(true)
        wantedArrayList = arrayListOf<Wanted>()
    }

    private fun getRescueData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("wanted")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (wantedSnapshot in snapshot.children) {
                        val wanted = wantedSnapshot.getValue(Wanted::class.java)
                        wantedArrayList.add(wanted!!)
                    }
                    rescueRecyclerView.adapter = WantedAdapter(wantedArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showInfoDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_wanted_info_dialog_title))
            .setMessage(resources.getString(R.string.fragment_wanted_info_dialog_content))
            .setNeutralButton(resources.getString(R.string.fragment_wanted_info_dialog_action)) { _, _ ->
            }
            .show()
    }

}