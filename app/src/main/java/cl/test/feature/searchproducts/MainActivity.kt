package cl.test.feature.searchproducts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import cl.test.feature.searchproducts.navegation.SearchProductsNavGraph
import cl.test.feature.searchproducts.ui.theme.SearchProductsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchProductsTheme {
                Surface {
                    SearchProductsNavGraph()
                }
            }
        }
    }
}