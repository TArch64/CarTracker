package screens.events.list

import org.koin.dsl.module

val eventsListModule = module {
    factory { params -> EventsListModel(params.get()) }
}
