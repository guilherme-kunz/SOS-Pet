package guilhermekunz.com.br.sospet.ui.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import guilhermekunz.com.br.sospet.utils.LoadingStates
import guilhermekunz.com.br.sospet.utils.validation.ValidationUtils
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private var email: String? = null
    private var password: String? = null
    private var newPassword: String? = null

    private val _validData = MutableLiveData(false)
    val validData: LiveData<Boolean> = _validData

    private val _validPassword = MutableLiveData(false)
    val validPassword: LiveData<Boolean> = _validPassword

    private val _errorResetPassword = MutableLiveData<Unit>()
    val errorResetPassword: LiveData<Unit> = _errorResetPassword

    private val _resetPasswordResponse = MutableLiveData<Unit>()
    val resetPasswordResponse: LiveData<Unit> = _resetPasswordResponse

    private val _errorSignIn = MutableLiveData<Unit>()
    val errorSignIn: LiveData<Unit> = _errorSignIn

    private val _signInResponse = MutableLiveData<Unit>()
    val signInResponse: LiveData<Unit> = _signInResponse

    var loadingStateLiveDate = MutableLiveData<LoadingStates>()

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun setEmail(email: String) {
        this.email = email
        validateData()
    }

    fun setPassword(password: String) {
        this.password = password
        validateData()
    }

    fun setResetPassword(newPassword: String) {
        this.newPassword = newPassword
        validatePassword()
    }

    private fun validatePassword() {
        _validPassword.value = ValidationUtils.isPasswordValidated(newPassword) == true
    }

    private fun validateData() {
        _validData.value = ValidationUtils.isEmailValidated(email)
                && ValidationUtils.isPasswordValidated(password) == true
    }

    fun signIn() {
        viewModelScope.takeIf { _validData.value == true }?.launch {
            loadingStateLiveDate.value = LoadingStates.LOADING
            firebaseAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    _signInResponse.value = Unit
                } else {
                    _errorSignIn.value = Unit
                }
            }
            loadingStateLiveDate.value = LoadingStates.LOADING_FINISHED
        }
    }


    fun resetPassword() {
        viewModelScope.takeIf { _validPassword.value == true }?.launch {
            loadingStateLiveDate.value = LoadingStates.LOADING
            val user = firebaseAuth.currentUser
            user!!.updatePassword(newPassword.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _resetPasswordResponse.value = Unit
                    } else {
                        _errorResetPassword.value = Unit
                    }
                }
            loadingStateLiveDate.value = LoadingStates.LOADING_FINISHED
        }
    }


}