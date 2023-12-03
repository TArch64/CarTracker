package screens.splash

import org.koin.dsl.module

val splashScreenModule = module {
    factory { SplashScreenModel(get()) }
}
