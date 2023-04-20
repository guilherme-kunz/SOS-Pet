package guilhermekunz.com.br.sospet.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import guilhermekunz.com.br.sospet.utils.validation.ValidationUtils
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private var fullName: String? = null
    private var email: String? = null
    private var cellPhone: String? = null
    var password: String? = null
    private var passwordConfirmation: String? = null

    var loadingStateLiveDate = MutableLiveData<State>()

    private val _validData = MutableLiveData(false)
    val validData: LiveData<Boolean> = _validData

    private val _errorSignUp = MutableLiveData<Unit>()
    val errorSignUp: LiveData<Unit> = _errorSignUp

    private val _signUpResponse = MutableLiveData<Unit>()
    val signUpResponse: LiveData<Unit> = _signUpResponse

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun setFullName(fullName: String) {
        this.fullName = fullName
        validateData()
    }

    fun setEmail(email: String) {
        this.email = email
        validateData()
    }

    fun setCellPhone(cellPhone: String){
        this.cellPhone = cellPhone
        validateData()
    }

    @JvmName("setPassword1")
    fun setPassword(password: String) {
        this.password = password
        validateData()
    }

    fun setPasswordConfirmation(passwordConfirmation: String) {
        this.passwordConfirmation = passwordConfirmation
        validateData()
    }

    private fun validateData() {
        _validData.value = ValidationUtils.isEmailValidated(email)
                && ValidationUtils.isPasswordValidated(password) == true
                && ValidationUtils.isPasswordConfirmationValidated(password, passwordConfirmation)
    }

    fun signUp() {
        viewModelScope.takeIf { _validData.value == true }?.launch {
            loadingStateLiveDate.value = State.LOADING
            firebaseAuth.createUserWithEmailAndPassword(email!!, passwordConfirmation!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        _signUpResponse.value = Unit
                    } else {
                        _errorSignUp.value = Unit
                    }
                }
            loadingStateLiveDate.value = State.LOADING_FINISHED
        }
    }

    enum class State {
        LOADING, LOADING_FINISHED
    }

}



