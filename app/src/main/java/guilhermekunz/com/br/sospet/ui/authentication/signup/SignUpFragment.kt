package guilhermekunz.com.br.sospet.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignUpBinding
import guilhermekunz.com.br.sospet.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        setupGoToSignInButton()
        setupInputs()
        initObserver()
        setupSignUpButton()
    }

    private fun setupGoToSignInButton() {
        binding.fragmentSignUpGoToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun setupInputs() {
        binding.fragmentSignUpEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.fragmentSignUpPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
        binding.fragmentSignUpPasswordConfirmation.addTextChangedListener {
            viewModel.setPasswordConfirmation(it.toString())
        }
    }

    private fun initObserver() {
        viewModel.validData.observe(viewLifecycleOwner) {
            binding.signUpButton.isEnabled = it
        }
        viewModel.signUpResponse.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            viewModel.signUp()
        }
    }

}