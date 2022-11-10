package guilhermekunz.com.br.sospet.utils.dialog

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import guilhermekunz.com.br.sospet.R

data class DialogOneButtonModel(
    var title: String,
    var content: String,
    var button: ButtonDialogOne
)

data class ButtonDialogOne(
    var titleButton: String,
    var action: () -> Unit
)

class DialogGenericOneButton(context: Context) : Dialog(context, R.style.RoundedCornersDialog) {

    init {
        setContentView(R.layout.dialog_generic_one_button)
    }

    fun setupDialog(model: DialogOneButtonModel) {
        findViewById<TextView>(R.id.dialog_generic_one_button_title).text = model.title
        findViewById<TextView>(R.id.dialog_generic_one_button_content).text = model.content
        findViewById<MaterialButton>(R.id.dialog_generic_one_button_button).apply {
            text = model.button.titleButton
            setOnClickListener {
                model.button.action.invoke()
            }
        }
    }

}