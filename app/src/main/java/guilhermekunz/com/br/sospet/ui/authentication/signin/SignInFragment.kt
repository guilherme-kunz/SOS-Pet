package guilhermekunz.com.br.sospet.ui.authentication.signin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignInBinding
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.utils.PAIR_SIGN_IN
import guilhermekunz.com.br.sospet.utils.dialog.ButtonDialogOne
import guilhermekunz.com.br.sospet.utils.dialog.DialogGenericOneButton
import guilhermekunz.com.br.sospet.utils.dialog.DialogOneButtonModel
import guilhermekunz.com.br.sospet.utils.makeLinks
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SignInViewModel>()

    private val dialogDialogGenericOneButton by lazy { DialogGenericOneButton(requireContext()) }

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
    }

    private fun setupSignInButton() {
        binding.signInButton.setOnClickListener {
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

}