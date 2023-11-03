package com.example.homework2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.homework2.ui.theme.Homework2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
@Composable
fun WelcomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the City App!", fontSize = 25.sp, fontWeight = FontWeight.Bold)

        Button(
            onClick = { navController.navigate("listScreen") },
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "More About Cities")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController) {
    val cities = listOf("Yerevan", "Washington", "Madrid", "Moscow", "Amsterdam") // Add more cities as needed

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = { Text(text = "Cities") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back"
                    )
                }
            }
        )

        LazyColumn {
            items(cities) { city ->
                Text(
                    text = city,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable {
                            navController.navigate("secondScreen/$city")
                        }
                )
            }
        }
    }
}

@Composable
fun SecondScreen(cityName: String) {
   val cities =  mapOf("Yerevan" to "Yerevan is the capital and largest city of Armenia." +
            " It is one of the world's oldest continuously inhabited cities, known" +
            " for its rich history and unique blend of Eastern and Western influences.",
        "Washington" to "Washington, D.C., is the capital of the United States. It is known " +
                "for its iconic monuments, including the Washington Monument, Lincoln Memorial," +
                " and the U.S. Capitol. The city is also home to many museums, cultural " +
                "institutions," + " and government buildings.", "Madrid" to "Madrid is the capital " +
                "and largest" + " city of Spain. Known for its vibrant culture, lively atmosphere," +
                " and world-class museums like the Prado, Madrid is a hub of art, history, and culinary" +
                " delights.", "Moscow" to "Moscow is the capital and largest city of Russia. It is known" +
                " for its stunning architecture, including the Kremlin and Red Square. Moscow is a cultural" +
                " and political center, with a rich history that spans over 800 years.","Amsterdam" to
                "Amsterdam is the capital and largest city of the Netherlands. It is famous for its picturesque " +
                "canals, historic architecture, and vibrant cultural scene. Amsterdam is also known for its" +
                " tolerant and progressive social policies.")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Information about $cityName", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        val information = when(cityName){
        "Yerevan" -> cities["Yerevan"]
        "Washington" -> cities["Washington"]
        "Madrid" -> cities["Madrid"]
        "Moscow" -> cities["Moscow"]
        "Amsterdam" -> cities["Amsterdam"]
        else -> "No Information available"
    }
        if (information != null) {
            Text(text = information, fontSize = 18.sp)
        }

        val imageResId = when (cityName) {
            "Yerevan" -> R.drawable.yerevan
            "Washington" -> R.drawable.washington
            "Madrid" -> R.drawable.madrid
            "Moscow" -> R.drawable.moscow
            "Amsterdam" -> R.drawable.amsterdam
            else -> R.drawable.nature
        }

        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "City Image",
            modifier = Modifier.size(300.dp)
        )
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    Homework2Theme {
        NavHost(navController, startDestination = "welcomeScreen") {
            composable("welcomeScreen") {
                WelcomeScreen(navController)
            }
            composable("listScreen") {
                ListScreen(navController)
            }
            composable(
                route = "secondScreen/{cityName}",
                arguments = listOf(navArgument("cityName") { type = NavType.StringType })
            ) { backStackEntry ->
                val cityName = backStackEntry.arguments?.getString("cityName")
                cityName?.let {
                    SecondScreen(it)
                }
            }
        }
    }
}


