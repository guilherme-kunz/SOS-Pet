package guilhermekunz.com.br.sospet.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentProfileBinding
import guilhermekunz.com.br.sospet.ui.authentication.AuthenticationActivity
import guilhermekunz.com.br.sospet.utils.LoadingStates
import guilhermekunz.com.br.sospet.utils.VERSION
import guilhermekunz.com.br.sospet.utils.VersionUtils
import guilhermekunz.com.br.sospet.utils.extensions.setImageFromBase64
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ProfileViewModel>()

    private lateinit var database: DatabaseReference
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

//    private val dialogLayout = layoutInflater.inflate(R.layout.reset_password_dialog_layout, null)
//    private val resetPasswordEditTextView =
//        dialogLayout.findViewById<EditText>(R.id.layout_reset_password_dialog_input)

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
        getProfile()
        setupOnBackPressed()
//        setupResetPassword()
//        setupInputs()
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
        viewModel.resetPasswordResponse.observe(viewLifecycleOwner) {
            dialogResetPasswordSuccess()
        }
        viewModel.errorResetPassword.observe(viewLifecycleOwner) {
            dialogResetPasswordError()
        }
    }

    private fun dialogResetPasswordError() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_profile_dialog_reset_password_error_title))
            .setNeutralButton(resources.getString(R.string.fragment_sign_up_dialog_error_button)) { _, _ ->
            }
            .show()
    }

    private fun dialogResetPasswordSuccess() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.fragment_profile_dialog_reset_password_success_title))
            .setNeutralButton(resources.getString(R.string.fragment_sign_up_dialog_error_button)) { _, _ ->
            }
            .show()
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

    @SuppressLint("SuspiciousIndentation")
    private fun getProfile() {
        database = FirebaseDatabase.getInstance().getReference("users")
            database.child(firebaseAuth.currentUser!!.uid).get().addOnSuccessListener {
                if (it.exists()) {
                    binding.fragmentProfileName.setText(it.child("name").value.toString())
                    binding.fragmentProfileCellphone.setText(it.child("phone").value.toString())
                    binding.profileImage.setImageFromBase64(it.child("profileImage").value.toString(), R.drawable.ic_add_profile_image)
                } else {
//                    _errorGetProfile.value = Unit
                }
            }.addOnFailureListener {
//                _errorGetProfile.value = Unit
            }
    }

    private fun setupOnBackPressed() {
        binding.imgArrowBackProfile.setOnClickListener {
            activity?.onBackPressed()
        }
    }

//    fun getProfile() {
//        viewModelScope.launch {
//            loadingStateLiveDate.value = LoadingStates.LOADING
//            database = FirebaseDatabase.getInstance().getReference("users")
//            database.child(firebaseAuth.currentUser!!.uid).get().addOnSuccessListener {
//                if (it.exists()) {
//                    name = it.child("name").value.toString()
//                    email = it.child("email").value.toString()
//                    phone = it.child("phone").value.toString()
//                    uid = it.child("uid").value.toString()
//                    profileImage = it.child("profileImage").value.toString()
//                } else {
//                    _errorGetProfile.value = Unit
//                }
//            }.addOnFailureListener {
//                _errorGetProfile.value = Unit
//            }
//            loadingStateLiveDate.value = LoadingStates.LOADING_FINISHED
//        }
//    }


//    private fun setupResetPassword() {
//        binding.profileResetPassword.setOnClickListener {
//            resetPasswordDialog()
//        }
//    }

//    private fun resetPasswordDialog() {
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle(resources.getString(R.string.fragment_profile_dialog_reset_password_title))
//            .setNeutralButton(resources.getString(R.string.fragment_profile_dialog_reset_password_ok)) { _, _ ->
//                viewModel.resetPassword()
//            }
//            .setView(dialogLayout)
//            .show()
//    }

//    private fun setupInputs() {
//        resetPasswordEditTextView.addTextChangedListener {
//            viewModel.setResetPassword(it.toString())
//        }
//    }


}