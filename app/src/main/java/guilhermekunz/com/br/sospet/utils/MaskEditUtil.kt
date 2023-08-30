package guilhermekunz.com.br.sospet.utils

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addMask(mask: String) {
    this.addTextChangedListener(object : TextWatcher {
        var isUpdating = false
        var oldString = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val str = s.toString()
            if (isUpdating || str == oldString) {
                return
            }
            val onlyDigits = str.replace("[^\\d]".toRegex(), "")
            if (onlyDigits.length > 10) {
                isUpdating = true
                setText(oldString)
                setSelection(oldString.length)
                return
            }
            var formatted = ""
            var index = 0
            for (i in 0 until onlyDigits.length) {
                val c = onlyDigits[i]
                if (index >= mask.length) {
                    break
                }
                while (mask[index] != '#' && index < mask.length) {
                    formatted += mask[index]
                    index++
                }
                formatted += c
                index++
            }
            isUpdating = true
            setText(formatted)
            setSelection(formatted.length)
            oldString = formatted
            isUpdating = false
        }
    })
}

fun setMaxLength(maxLength: Int) = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

//    companion object {
//
//        fun clear(maskedText: String): String = maskedText.replace(Regex("[^a-zA-Z0-9]*"), "")
//
//    }

