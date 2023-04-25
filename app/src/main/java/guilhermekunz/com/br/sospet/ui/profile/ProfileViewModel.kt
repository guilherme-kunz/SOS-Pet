package guilhermekunz.com.br.sospet.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import guilhermekunz.com.br.sospet.utils.LoadingStates
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val _errorDeleteAccount = MutableLiveData<Unit>()
    val errorDeleteAccount: LiveData<Unit> = _errorDeleteAccount

    private val _deleteAccountResponse = MutableLiveData<Unit>()
    val deleteAccountResponse: LiveData<Unit> = _deleteAccountResponse

    var loadingStateLiveDate = MutableLiveData<LoadingStates>()

    //Loading Progress Bar
    //Validate Statues

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

}