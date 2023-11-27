package ua.tarch64.car_tracker

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.dsl.module
import storage.database.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(platformModule = module {
                single { DatabaseDriverFactory(applicationContext) }
            })
        }
    }
}
