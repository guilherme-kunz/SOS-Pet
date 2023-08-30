package guilhermekunz.com.br.sospet.utils.bottomsheet

import android.content.Context
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import guilhermekunz.com.br.sospet.R

data class BottomSheetModel(
    var camera: () -> Unit,
    var gallery: () -> Unit
)

class BottomSheet(context: Context) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme){

    init {
        setContentView(R.layout.bottom_sheet)
    }

    fun setupBottomSheet(bottomSheetModel: BottomSheetModel) {
        findViewById<TextView>(R.id.txv_camera)?.setOnClickListener {
            bottomSheetModel.camera.invoke()
        }
        findViewById<TextView>(R.id.txv_gallery)?.setOnClickListener {
            bottomSheetModel.gallery.invoke()
        }
    }

}