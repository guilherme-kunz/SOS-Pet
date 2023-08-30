package guilhermekunz.com.br.sospet.ui.authentication.signin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignInBinding
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.utils.KeyboardUtils
import guilhermekunz.com.br.sospet.utils.LoadingStates
import guilhermekunz.com.br.sospet.utils.PAIR_SIGN_IN
import guilhermekunz.com.br.sospet.utils.dialog.ButtonDialogOne
import guilhermekunz.com.br.sospet.utils.dialog.DialogGenericOneButton
import guilhermekunz.com.br.sospet.utils.dialog.DialogOneButtonModel
import guilhermekunz.com.br.sospet.utils.extensions.makeLinks
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SignInViewModel>()

    private val dialogDialogGenericOneButton by lazy { DialogGenericOneButton(requireContext()) }

//    private val dialogLayout = layoutInflater.inflate(R.layout.reset_password_dialog_layout_sign_in, null)
//    private val resetPasswordEditTextView =
//        dialogLayout.findViewById<EditText>(R.id.layout_reset_password_dialog_input_sign_in)

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
        initObserver()
        setupSignInButton()
//        setupForgotPassword()
    }

    private fun setupGoToSignUpButton() {
        binding.fragmentSignInGoToSignUp.makeLinks(
            Pair(PAIR_SIGN_IN, View.OnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            })
        )
        binding.fragmentSignInGoToSignUp.highlightColor = Color.TRANSPARENT
    }

    private fun setupInputs() {
        binding.fragmentSignInEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.fragmentSignInPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
//        resetPasswordEditTextView.addTextChangedListener {
//            viewModel.setResetPassword(it.toString())
//        }
    }

    private fun handleProgressBar(state: LoadingStates) {
        when (state) {
            LoadingStates.LOADING -> binding.fragmentSignInProgressBar.visibility = View.VISIBLE
            LoadingStates.LOADING_FINISHED -> binding.fragmentSignInProgressBar.visibility =
                View.GONE
        }
    }

    private fun initObserver() {
        viewModel.validData.observe(viewLifecycleOwner) {
            binding.signInButton.isEnabled = it
        }
        viewModel.signInResponse.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        viewModel.errorSignIn.observe(viewLifecycleOwner) {
            signInError()
        }
        viewModel.loadingStateLiveDate.observe(viewLifecycleOwner) {
            it?.let {
                handleProgressBar(it)
            }
        }
    }

    private fun setupSignInButton() {
        binding.signInButton.setOnClickListener {
            KeyboardUtils.hide(requireView())
            viewModel.signIn()
        }
    }

    private fun signInError() {
        dialogDialogGenericOneButton.apply {
            setupDialog(
                DialogOneButtonModel(
                    title = getString(R.string.fragment_sign_in_dialog_error_title),
                    content = getString(R.string.fragment_sign_in_dialog_error_content),
                    button = ButtonDialogOne(
                        titleButton = getString(R.string.fragment_sign_in_dialog_error_button),
                        action = { this.dismiss() }
                    )
                )
            )
        }.show()
    }

//    private fun setupForgotPassword() {
//        binding.signIngForgotPassword.setOnClickListener {
//            dialogForgotPassword()
//        }
//    }

//    private fun dialogForgotPassword() {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle(resources.getString(R.string.fragment_profile_dialog_reset_password_title))
//            .setNeutralButton(resources.getString(R.string.fragment_profile_dialog_reset_password_ok)) { _, _ ->
//                viewModel.resetPassword()
//            }
//            .setView(dialogLayout)
//            .show()
//    }

}