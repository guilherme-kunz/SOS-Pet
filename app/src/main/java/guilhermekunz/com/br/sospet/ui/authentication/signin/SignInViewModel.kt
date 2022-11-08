package guilhermekunz.com.br.sospet.ui.authentication.signin

import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    private var email: String? = null
    private var password: String? = null

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

}