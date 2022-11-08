package guilhermekunz.com.br.sospet.ui.authentication.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGoToSignUpButton()
        setupInputs()
    }

    private fun setupGoToSignUpButton() {
        binding.fragmentSignInGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun setupInputs() {
        binding.fragmentSignInEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.fragmentSignInPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
    }

}