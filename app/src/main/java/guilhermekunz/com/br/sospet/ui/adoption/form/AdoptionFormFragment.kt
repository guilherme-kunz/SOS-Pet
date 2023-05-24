package guilhermekunz.com.br.sospet.ui.adoption.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentAdoptionFormBinding
import guilhermekunz.com.br.sospet.utils.FEMALE_SEX
import guilhermekunz.com.br.sospet.utils.MALE_SEX


class AdoptionFormFragment : Fragment() {

    private var _binding: FragmentAdoptionFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdoptionFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSexSpinner()
    }

    private fun setupSexSpinner() {
        val spinner = listOf(
            FEMALE_SEX,
            MALE_SEX
        )
        binding.fragmentAdoptionFormSexSpinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            spinner
        )
        binding.fragmentAdoptionFormTexSpinner.visibility = View.GONE
    }


}