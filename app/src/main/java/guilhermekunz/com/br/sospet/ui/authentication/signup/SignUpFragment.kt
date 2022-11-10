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
import guilhermekunz.com.br.sospet.utils.dialog.ButtonDialogOne
import guilhermekunz.com.br.sospet.utils.dialog.DialogGenericOneButton
import guilhermekunz.com.br.sospet.utils.dialog.DialogOneButtonModel
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
        dialogDialogGenericOneButton.apply {
            setupDialog(
                DialogOneButtonModel(
                    title = getString(R.string.fragment_sign_up_dialog_error_title),
                    content = getString(R.string.fragment_sign_up_dialog_error_content),
                    button = ButtonDialogOne(
                        titleButton = getString(R.string.fragment_sign_up_dialog_error_button),
                        action = { this.dismiss() }
                    )
                )
            )
        }
    }

    private fun dialogSignUpSuccess() {
        dialogDialogGenericOneButton.apply {
            setupDialog(
                DialogOneButtonModel(
                    title = getString(R.string.fragment_sign_up_dialog_success_title),
                    content = getString(R.string.fragment_sign_up_dialog_success_content),
                    button = ButtonDialogOne(
                        titleButton = getString(R.string.fragment_sign_up_dialog_success_button),
                        action = {
                            this.dismiss()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }
                    )
                )
            )
        }
    }
}