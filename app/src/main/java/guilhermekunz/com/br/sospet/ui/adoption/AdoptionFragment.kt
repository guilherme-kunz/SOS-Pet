package guilhermekunz.com.br.sospet.ui.adoption

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentAdoptionBinding
import guilhermekunz.com.br.sospet.model.Adoption
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.ui.adoption.form.MapsActivity
import java.util.*
import kotlin.collections.ArrayList

class AdoptionFragment : Fragment() {

    private var _binding: FragmentAdoptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var adoptionRecyclerView: RecyclerView
    private lateinit var adoptionArrayList: ArrayList<Adoption>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdoptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getRescueData()
        showInfoDialog()
        setupFloatingButton()
        val id: UUID = UUID.randomUUID()
    }

    private fun setupRecyclerView() {
        adoptionRecyclerView = binding.fragmentAdoptionRecyclerView
        adoptionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adoptionRecyclerView.setHasFixedSize(true)
        adoptionArrayList = arrayListOf<Adoption>()
    }

    private fun getRescueData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("rescue")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (rescueSnapshot in snapshot.children) {
                        val adoption = rescueSnapshot.getValue(Adoption::class.java)
                        adoptionArrayList.add(adoption!!)
                    }
                    adoptionRecyclerView.adapter = AdoptionAdapter(adoptionArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showInfoDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_adoption_info_dialog_title))
            .setMessage(resources.getString(R.string.fragment_adoption_info_dialog_content))
            .setNeutralButton(resources.getString(R.string.fragment_adoption_info_dialog_action)) { _, _ ->
            }
            .show()
    }

    private fun setupFloatingButton() {
        binding.fragmentAdoptionFloatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
    }

}