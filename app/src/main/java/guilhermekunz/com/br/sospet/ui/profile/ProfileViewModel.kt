package guilhermekunz.com.br.sospet.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import guilhermekunz.com.br.sospet.utils.LoadingStates
import guilhermekunz.com.br.sospet.utils.validation.ValidationUtils
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private var newPassword: String? = null

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    private val _errorDeleteAccount = MutableLiveData<Unit>()
    val errorDeleteAccount: LiveData<Unit> = _errorDeleteAccount

    private val _errorGetProfile = MutableLiveData<Unit>()
    val errorGetProfile: LiveData<Unit> = _errorGetProfile

    private val _deleteAccountResponse = MutableLiveData<Unit>()
    val deleteAccountResponse: LiveData<Unit> = _deleteAccountResponse

    var loadingStateLiveDate = MutableLiveData<LoadingStates>()

    private val _validData = MutableLiveData(false)

    private val _errorResetPassword = MutableLiveData<Unit>()
    val errorResetPassword: LiveData<Unit> = _errorResetPassword

    private val _resetPasswordResponse = MutableLiveData<Unit>()
    val resetPasswordResponse: LiveData<Unit> = _resetPasswordResponse

    fun setResetPassword(newPassword: String) {
        this.newPassword = newPassword
        validateData()
    }

    fun deleteAccount() {
        viewModelScope.launch {
            loadingStateLiveDate.value = LoadingStates.LOADING
            val user = firebaseAuth.currentUser!!
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _deleteAccountResponse.value = Unit
                    } else {
                        _errorDeleteAccount.value = Unit
                    }
                }
            loadingStateLiveDate.value = LoadingStates.LOADING_FINISHED
        }
    }

    fun resetPassword() {
        viewModelScope.takeIf { _validData.value == true }?.launch {
            loadingStateLiveDate.value = LoadingStates.LOADING
            val user = firebaseAuth.currentUser
            user!!.updatePassword(newPassword.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _resetPasswordResponse.value = Unit
                } else {
                    _errorResetPassword.value = Unit
                }
            }
            loadingStateLiveDate.value = LoadingStates.LOADING_FINISHED
        }
    }


    private fun validateData() {
        _validData.value = ValidationUtils.isPasswordValidated(newPassword) == true
    }

}