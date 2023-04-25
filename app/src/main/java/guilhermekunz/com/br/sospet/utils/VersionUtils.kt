package guilhermekunz.com.br.sospet.utils

import android.content.Context
import android.content.pm.PackageManager

object VersionUtils {

    fun getAppVersion(context: Context): String {
        var result = ""
        try {
            result = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            result = result.replace("[a-zA-Z]|-".toRegex(), "")
        } catch (_: PackageManager.NameNotFoundException) { }
        return result
    }

}