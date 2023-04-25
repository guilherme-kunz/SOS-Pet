package guilhermekunz.com.br.sospet.ui.authentication.signup

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import guilhermekunz.com.br.sospet.R
import guilhermekunz.com.br.sospet.databinding.FragmentSignUpBinding
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.utils.*
import guilhermekunz.com.br.sospet.utils.bottomsheet.BottomSheet
import guilhermekunz.com.br.sospet.utils.bottomsheet.BottomSheetModel
import guilhermekunz.com.br.sospet.utils.extensions.makeLinks
import guilhermekunz.com.br.sospet.utils.extensions.removeEmojis
import guilhermekunz.com.br.sospet.utils.extensions.rotatedToVertical
import guilhermekunz.com.br.sospet.utils.extensions.toBase64
import guilhermekunz.com.br.sospet.utils.validation.ValidationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SignUpViewModel>()

    private val bottomSheet by lazy { BottomSheet(requireContext()) }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), TOAST_CAMERA_PERMISSION, Toast.LENGTH_LONG)
                    .show()
            }
        }

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
        chooseProfileImage()
    }

    private fun setupGoToSignInButton() {
        binding.fragmentSignUpGoToSignIn.makeLinks(
            Pair(PAIR_SIGN_UP, View.OnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            })
        )
        binding.fragmentSignUpGoToSignIn.highlightColor = Color.TRANSPARENT
    }

    private fun setupInputs() {
        binding.fragmentSignUpName.apply {
            removeEmojis()
            addTextChangedListener {
                viewModel.setFullName(it.toString())
            }
        }
        binding.fragmentSignUpEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.fragmentSignUpCellphone.apply {
            addMask(CELL_PHONE_MASK)
            addTextChangedListener {
                viewModel.setCellPhone(it.toString())
            }
        }
        binding.fragmentSignUpPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
        binding.fragmentSignUpPasswordConfirmation.addTextChangedListener {
            viewModel.setPasswordConfirmation(it.toString())
        }
    }

    private fun setupFormErrors() {
        binding.fragmentSignUpName.doOnTextChanged { text, _, _, _ ->
            if (ValidationUtils.isFullNameValidated(text.toString())) {
                binding.signUpName.error = null
            } else {
                binding.signUpName.error = getString(R.string.fragment_sign_up_name_error_message)
            }
        }
        binding.fragmentSignUpEmail.doOnTextChanged { text, _, _, _ ->
            if (ValidationUtils.isEmailValidated(text.toString())) {
                binding.signUpEmail.error = null
            } else {
                binding.signUpEmail.error = getString(R.string.fragment_sign_up_email_error_message)
            }
        }
        binding.fragmentSignUpCellphone.doOnTextChanged { text, _, _, _ ->
            if (ValidationUtils.isCellPhoneValidated(text.toString())) {
                binding.signUpCellphone.error = null
            } else {
                binding.signUpCellphone.error =
                    getString(R.string.fragment_sign_up_cell_phone_error_message)
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
            KeyboardUtils.hide(requireView())
            viewModel.signUp()
        }
    }

    private fun handleProgressBar(state: LoadingStates) {
        when (state) {
            LoadingStates.LOADING -> binding.fragmentSignUpProgressBar.visibility =
                View.VISIBLE
            LoadingStates.LOADING_FINISHED -> binding.fragmentSignUpProgressBar.visibility =
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

    private fun chooseProfileImage() {
        binding.constraintSignUpAddProfileImage.setOnClickListener {
            bottomSheet.apply {
                setupBottomSheet(
                    BottomSheetModel(
                        camera = {
                            camera()
                            this.dismiss()
                        },
                        gallery = {
                            gallery()
                            this.dismiss()
                        }
                    )
                )
            }.show()
        }
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = GALLERY_IMAGE_TYPE
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun camera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else {
                Toast.makeText(requireContext(), TOAST_CAMERA_PERMISSION, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                val rotatedBitmap = thumbnail.rotatedToVertical()
                binding.motorcycleImage.setImageBitmap(rotatedBitmap)
                viewModel.setImageBase64(rotatedBitmap.toBase64())
            } else if (requestCode == IMAGE_REQUEST_CODE) {
                val inputStream = requireContext().contentResolver.openInputStream(data!!.data!!)
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                viewModel.setImageBase64(bitmap.toBase64())
                binding.motorcycleImage.setImageBitmap(bitmap)
            }
        }
    }


}