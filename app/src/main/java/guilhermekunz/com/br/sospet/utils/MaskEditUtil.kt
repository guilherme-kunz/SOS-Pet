package guilhermekunz.com.br.sospet.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference

open class GenericMask(view: EditText, private val mask: String) : TextWatcher {

    private val reference: WeakReference<EditText> = WeakReference(view)

    private var isUpdating = false
    private var oldText = ""
    private var maskedText = StringBuilder()

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        reference.get().let {
            maskedText.delete(0, maskedText.length)

            val cleanText = clear(reference.get()?.text.toString())
            if (isUpdating) {
                oldText = cleanText
                isUpdating = false
                return
            }

            apply(cleanText)

            isUpdating = true

            reference.get()?.setText(maskedText.toString())
            reference.get()?.setSelection(reference.get()?.length() ?: 0)
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    private fun apply(cleanText: String) {
        var i = 0
        mask.forEach { m ->
            if ((m != '#' && cleanText.length > oldText.length) || (m != '#' && cleanText.length < oldText.length && cleanText.length != i)) {
                maskedText.append(m)
                return@forEach
            }

            try {
                maskedText.append(cleanText[i])
            } catch (e: Exception) {
                return
            }

            i++
        }
    }

    companion object {

        fun clear(maskedText: String): String = maskedText.replace(Regex("[^a-zA-Z0-9]*"), "")

    }

}