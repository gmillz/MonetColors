package dev.gmillz.monetcolorsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.gmillz.monetcolorsample.ui.theme.MonetColorSampleTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonetColorSampleTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Monet Colors") },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { contentPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(contentPadding)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                            for (color in colors) {
                                if (color.header) {
                                    item(span = { GridItemSpan(4) }) {
                                        Text(
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .background(MaterialTheme.colorScheme.primaryContainer),
                                            text = color.palette,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontSize = 20.sp
                                        )
                                    }
                                } else {
                                    item {
                                        val resId =
                                            resources.getIdentifier(color.res, "color", "android")
                                        val colorInt = getColor(resId)
                                        val textColor = if (checkBackgroundColor(colorInt)) {
                                            Color.White
                                        } else {
                                            Color.Black
                                        }
                                        if (resId != 0) {
                                            Card(
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .size(80.dp),
                                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = Color(colorInt)
                                                )
                                            ) {
                                                Box(
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Text(
                                                        modifier = Modifier
                                                            .align(Alignment.TopCenter)
                                                            .padding(top = 4.dp),
                                                        text = color.shade.toString(),
                                                        color = textColor
                                                    )
                                                    Text(
                                                        modifier = Modifier.align(Alignment.Center),
                                                        text = "#${
                                                            Integer.toHexString(colorInt)
                                                                .substring(2)
                                                        }",
                                                        color = textColor
                                                    )
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun checkBackgroundColor(color: Int): Boolean {
        val darkness = 1 - sqrt(0.299 * android.graphics.Color.red(color) * android.graphics.Color.red(color)
                + 0.587 * android.graphics.Color.green(color) * android.graphics.Color.green(color) + 0.114 * android.graphics.Color.blue(color) * android.graphics.Color.blue(color)) / 255
        return darkness > 0.65
    }

    private class ColorInfo(val palette: String, val shade: Int, val res: String, val header: Boolean)

    companion object {
        private val choices = arrayOf("accent1", "accent2", "accent3", "neutral1", "neutral2")
        private val shades = arrayOf(0, 10, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000)

        private val colors = arrayListOf<ColorInfo>()
        init {
            for (choice in choices) {
                colors.add(ColorInfo(choice, -1, choice, true))
                for (shade in shades) {
                    colors.add(ColorInfo(choice, shade, "system_${choice}_$shade", false))
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
    MonetColorSampleTheme {
        Greeting("Android")
    }
}