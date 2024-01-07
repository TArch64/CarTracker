package screens.events

import org.koin.dsl.module
import screens.events.list.eventsListModule

val eventsModule = module {
    includes(eventsListModule)
}
