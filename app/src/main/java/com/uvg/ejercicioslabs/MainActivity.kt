package com.uvg.ejercicioslabs

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.uvg.ejercicioslabs.ejercicios.ktor.presentation.MonsterNavHost
import com.uvg.ejercicioslabs.ui.theme.EjerciciosLabsTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si quieren usar el splash screen asociado al view model
//        installSplashScreen().setKeepOnScreenCondition {
//            authViewModel.authStatus.value is DSAuthStatus.Loading
//        }

        installSplashScreen()

        setContent {
            EjerciciosLabsTheme {
                Surface {
                    MonsterNavHost()
                }
            }
        }
    }
}

