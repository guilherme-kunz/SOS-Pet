package guilhermekunz.com.br.sospet.ui.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import guilhermekunz.com.br.sospet.utils.ValidationUtils
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private var email: String? = null
    private var password: String? = null

    private val _validData = MutableLiveData(false)
    val validData: LiveData<Boolean> = _validData

    private val _errorSignIn = MutableLiveData<Unit>()
    val errorSignIn: LiveData<Unit> = _errorSignIn

    private val _signInResponse = MutableLiveData<Unit>()
    val signInResponse: LiveData<Unit> = _signInResponse

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun setEmail(email: String) {
        this.email = email
        validateData()
    }

    fun setPassword(password: String) {
        this.password = password
        validateData()
    }

    private fun validateData() {
        _validData.value = ValidationUtils.isEmailValidated(email)
                && ValidationUtils.isPasswordValidated(password) == true
    }

    fun signIn() {
        viewModelScope.takeIf { _validData.value == true }?.launch {
            firebaseAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    _signInResponse.value = Unit
                } else {
                    _errorSignIn.value = Unit
                }
            }
        }
    }


}