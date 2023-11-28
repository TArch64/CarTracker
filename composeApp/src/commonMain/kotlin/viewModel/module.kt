package viewModel

import org.koin.dsl.module

val viewModelModule = module {
    single { CarViewModel(get()) }
}
