package nz.co.example.app

import android.app.Application
import nz.co.example.app.di.appModule
import nz.co.example.coremodule.di.coreModule
import nz.co.example.rickandmortymodule.di.rickAndMortyModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ExampleApplication)
            modules(appModule, rickAndMortyModule, coreModule)
        }
    }

}