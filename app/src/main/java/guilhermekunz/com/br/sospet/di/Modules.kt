package guilhermekunz.com.br.sospet.di


import guilhermekunz.com.br.sospet.ui.authentication.signin.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module(override = true) {
    viewModel { SignInViewModel() }
}