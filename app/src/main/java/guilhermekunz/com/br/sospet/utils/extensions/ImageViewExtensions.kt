package guilhermekunz.com.br.sospet.utils.extensions

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.IntegerRes
import guilhermekunz.com.br.sospet.R

fun ImageView.setImageFromBase64(base64: String?, @IntegerRes resourceDefault: Int) {
    if (base64 != null) {
        val imageBase64 = base64.replace("data:image/jpeg;base64,","")
        val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        this.setImageBitmap(decodedImage)
    } else {
        this.setImageResource(resourceDefault)
    }
}