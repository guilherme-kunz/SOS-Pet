package guilhermekunz.com.br.sospet.launcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LauncherViewModel : ViewModel() {

    var launcherStateLiveData = MutableLiveData<LauncherState>()

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun verifyUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            launcherStateLiveData.value = LauncherState.NOT_AUTHENTICATED
        } else {
            launcherStateLiveData.value = LauncherState.AUTHENTICATED
        }
    }

    enum class LauncherState {
        NOT_AUTHENTICATED, AUTHENTICATED
    }

}