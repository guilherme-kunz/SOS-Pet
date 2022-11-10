package guilhermekunz.com.br.sospet.utils.validation

import android.util.Patterns

object ValidationUtils {

    fun isEmailValidated(email: String?) =
        !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValidated(password: String?) =
        password?.let {
            "^(?=.{8,}\$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*\$".toRegex().matches(
                it
            )
        }

    fun isPasswordConfirmationValidated(password: String?, passwordConfirmation: String?) =
        isPasswordValidated(password) == true && (passwordConfirmation == password)

}