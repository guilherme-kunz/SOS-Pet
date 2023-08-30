package guilhermekunz.com.br.sospet.utils.validation

import android.util.Patterns

object ValidationUtils {

    const val MINIMUM_LENGTH_CELL_PHONE = 14

    fun isFullNameValidated(fullName: String): Boolean {
        val nameList = fullName.trimEnd().split(" ")
        return isFullNameSizeValid(nameList) && isFullNameComplete(nameList)
    }

    private fun isFullNameSizeValid(nameList: List<String>) = nameList.size > 1

    private fun isFullNameComplete(nameList: List<String>): Boolean {
        nameList.onEach { name ->
            if (name.isBlank()) return false
        }
        return true
    }

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

    fun isCellPhoneValidated(cellPhone: String?) =
        !cellPhone.isNullOrBlank() && cellPhone.length >= MINIMUM_LENGTH_CELL_PHONE

}