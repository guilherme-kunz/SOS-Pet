package guilhermekunz.com.br.sospet.launcher

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import guilhermekunz.com.br.sospet.ui.MainActivity
import guilhermekunz.com.br.sospet.ui.authentication.AuthenticationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : AppCompatActivity() {

    private val viewModel by viewModel<LauncherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        viewModel.verifyUser()
        observer()
    }

    private fun observer() {
        viewModel.launcherStateLiveData.observe(this) {
            handleLauncherState(it)
        }
    }

    private fun handleLauncherState(state: LauncherViewModel.LauncherState?) {
        when (state) {
            LauncherViewModel.LauncherState.AUTHENTICATED -> goToHome()
            LauncherViewModel.LauncherState.NOT_AUTHENTICATED -> goToAuthentication()
            else -> {}
        }
    }

    private fun goToAuthentication() {
        startActivity(Intent(this, AuthenticationActivity::class.java))
        finish()
    }

    private fun goToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}