package guilhermekunz.com.br.sospet.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            koin.loadModules(listOf(
                viewModelModule,
            ))
        }
    }

}