package ua.tarch64.car_tracker

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import storage.database.DatabaseDriverFactory
import storage.database.DatabaseFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(databaseFactory = DatabaseFactory(DatabaseDriverFactory(applicationContext)))
        }
    }
}
