package io.github.alextsybin.pacekit.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.alextsybin.pacekit.PaceCalculator
import io.github.alextsybin.pacekit.demo.ui.theme.PaceKitTheme
import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import kotlin.time.Duration.Companion.minutes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val finishTime = PaceCalculator.calculateFinishTime(
            distance = Distance.kilometers(42.195),
            pace = Pace.minutesPerKilometer(4.0)
        )
        Log.d("PaceKitLog", "Finish time: $finishTime")

        setContent {
            PaceKitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaceKitTheme {
        Greeting("Android")
    }
}