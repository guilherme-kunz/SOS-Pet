package guilhermekunz.com.br.sospet.di


import guilhermekunz.com.br.sospet.launcher.LauncherViewModel
import guilhermekunz.com.br.sospet.ui.authentication.signin.SignInViewModel
import guilhermekunz.com.br.sospet.ui.authentication.signup.SignUpViewModel
import guilhermekunz.com.br.sospet.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module(override = true) {
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { LauncherViewModel() }
    viewModel { ProfileViewModel() }
}