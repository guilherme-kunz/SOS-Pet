package guilhermekunz.com.br.sospet.ui.authentication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignInButton()
        setupSignUpButton()
    }

    private fun setupSignInButton() {
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {

        }
    }

}