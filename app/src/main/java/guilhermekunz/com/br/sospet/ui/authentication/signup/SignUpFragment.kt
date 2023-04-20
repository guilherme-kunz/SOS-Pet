package guilhermekunz.com.br.sospet.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignUpBinding
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.utils.dialog.DialogGenericOneButton
import guilhermekunz.com.br.sospet.utils.validation.ValidationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SignUpViewModel>()

    private val dialogDialogGenericOneButton by lazy { DialogGenericOneButton(requireContext()) }

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
        setupFormErrors()
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

    private fun setupFormErrors() {
        binding.fragmentSignUpEmail.doOnTextChanged { text, _, _, _ ->
            if (ValidationUtils.isEmailValidated(text.toString())) {
                binding.signUpEmail.error = null
            } else {
                binding.signUpEmail.error = getString(R.string.fragment_sign_up_email_error_message)
            }
        }
        binding.fragmentSignUpPassword.doOnTextChanged { text, _, _, _ ->
            if (ValidationUtils.isPasswordValidated(text.toString()) == true) {
                binding.signUpPassword.error = null
            } else {
                binding.signUpPassword.error =
                    getString(R.string.fragment_sign_up_password_error_message)
            }
        }
        binding.fragmentSignUpPasswordConfirmation.doOnTextChanged { text, _, _, _ ->
            val password = binding.fragmentSignUpPassword.text.toString()
            if (ValidationUtils.isPasswordConfirmationValidated(password, text.toString())) {
                binding.signUpConfirmPassword.error = null
            } else {
                binding.signUpConfirmPassword.error =
                    getString(R.string.fragment_sign_up_confirm_password_error_message)
            }
        }
    }

    private fun initObserver() {
        viewModel.validData.observe(viewLifecycleOwner) {
            binding.signUpButton.isEnabled = it
        }
        viewModel.signUpResponse.observe(viewLifecycleOwner) {
            dialogSignUpSuccess()
        }
        viewModel.errorSignUp.observe(viewLifecycleOwner) {
            signUpError()
        }
        viewModel.loadingStateLiveDate.observe(viewLifecycleOwner) {
            it?.let {
                handleProgressBar(it)
            }
        }
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            viewModel.signUp()
        }
    }

    private fun handleProgressBar(state: SignUpViewModel.State) {
        when (state) {
            SignUpViewModel.State.LOADING -> binding.fragmentSignUpProgressBar.visibility =
                View.VISIBLE
            SignUpViewModel.State.LOADING_FINISHED -> binding.fragmentSignUpProgressBar.visibility =
                View.GONE
        }
    }

    private fun signUpError() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_sign_up_dialog_error_title))
            .setMessage(resources.getString(R.string.fragment_sign_up_dialog_error_content))
            .setNeutralButton(resources.getString(R.string.fragment_sign_up_dialog_error_button)) { _, _ ->
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            .show()
    }

    private fun dialogSignUpSuccess() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_sign_up_dialog_success_title))
            .setMessage(resources.getString(R.string.fragment_sign_up_dialog_success_content))
            .setNeutralButton(resources.getString(R.string.fragment_sign_up_dialog_success_button)) { _, _ ->
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            .show()
    }

}