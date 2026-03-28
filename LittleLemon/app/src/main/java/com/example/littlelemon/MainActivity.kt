package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.composable.NavigationComposable
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val lldb by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "llBase").build()
    }

    val sharedPreferences by lazy { getSharedPreferences("LittleLemon", MODE_PRIVATE) }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {

                val navController = rememberNavController()
                Scaffold(
                ) { innerPading ->
                    NavigationComposable(navController, database = lldb, modifier = Modifier.padding(innerPading))
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (lldb.menuDao().isEmpty()) {
                val menuItemsNetwork = fetchMenu()
                saveMenuToDatabase(menuItemsNetwork)
            }
        }


    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
    val urlMenu = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response = httpClient
            .get(urlString = urlMenu)
            .body<String>()
        val menuObject = Json.decodeFromString<MenuNetworkdata>(response)
        return menuObject.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        lldb.menuDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}
