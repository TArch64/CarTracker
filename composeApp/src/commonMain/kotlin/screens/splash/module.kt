package screens.splash

import org.koin.dsl.module

val splashScreenModule = module {
    single { SplashScreenModel(get()) }
}
