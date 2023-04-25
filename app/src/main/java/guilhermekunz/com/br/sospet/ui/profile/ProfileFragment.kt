package guilhermekunz.com.br.sospet.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentProfileBinding
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.ui.authentication.AuthenticationActivity
import guilhermekunz.com.br.sospet.utils.LoadingStates
import guilhermekunz.com.br.sospet.utils.VERSION
import guilhermekunz.com.br.sospet.utils.VersionUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVersionTextView()
        logoutButton()
        deleteAccountButton()
        initObserver()
    }

    private fun initObserver() {
        viewModel.loadingStateLiveDate.observe(viewLifecycleOwner) {
            it?.let {
                handleProgressBar(it)
            }
        }
        viewModel.deleteAccountResponse.observe(viewLifecycleOwner) {
            goToAuthenticationActivity()
        }
        viewModel.errorDeleteAccount.observe(viewLifecycleOwner) {
            deleteAccountError()
        }
    }

    private fun handleProgressBar(state: LoadingStates) {
        when (state) {
            LoadingStates.LOADING -> binding.fragmentProfileProgressBar.visibility = View.VISIBLE
            LoadingStates.LOADING_FINISHED -> binding.fragmentProfileProgressBar.visibility =
                View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupVersionTextView() {
        binding.profileVersion.text = VERSION + VersionUtils.getAppVersion(requireContext())
    }

    private fun logoutButton() {
        binding.profileLogoutButton.setOnClickListener {
            goToAuthenticationActivity()
        }
    }

    private fun deleteAccountButton() {
        binding.profileDeleteAccount.setOnClickListener {
            deleteAccountDialog()
        }
    }

    private fun deleteAccountDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_profile_dialog_title_delete_account))
            .setPositiveButton(resources.getString(R.string.fragment_profile_dialog_positive_button_delete_account)) { _, _ ->
                viewModel.deleteAccount()
            }
            .setNegativeButton(resources.getString(R.string.fragment_profile_dialog_negative_button_delete_account)) { _, _ ->
            }
            .show()
    }

    private fun goToAuthenticationActivity() {
        val intent = Intent(requireContext(), AuthenticationActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun deleteAccountError() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_sign_up_dialog_error_title))
            .setMessage(resources.getString(R.string.fragment_profile_dialog_delete_account_error_content))
            .setNeutralButton(resources.getString(R.string.fragment_sign_up_dialog_error_button)) { _, _ ->
            }
            .show()
    }


}