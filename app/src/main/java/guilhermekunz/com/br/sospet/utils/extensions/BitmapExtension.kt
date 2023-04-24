package guilhermekunz.com.br.sospet.utils.extensions

import android.graphics.Bitmap
import android.util.Base64
import guilhermekunz.com.br.sospet.utils.BASE_64_ENCODED
import guilhermekunz.com.br.sospet.utils.ImageRotationUtility
import java.io.ByteArrayOutputStream

fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
    return BASE_64_ENCODED + encoded
}

fun Bitmap.rotatedToVertical(): Bitmap {
    return ImageRotationUtility.rotateBitmap(
        this,
        if (this.width > this.height) 90f else 0f
    )
}