package guilhermekunz.com.br.sospet.utils.extensions

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import guilhermekunz.com.br.sospet.R

fun ImageView.setImageFromBase64(base64: String?) {
    if (base64 != null) {
        val imageBytes = Base64.decode(base64, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        this.setImageBitmap(decodedImage)
    } else {
        this.setImageResource(R.drawable.ic_add_profile_image)
    }
}