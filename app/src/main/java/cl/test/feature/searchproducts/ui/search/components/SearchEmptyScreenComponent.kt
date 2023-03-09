package cl.test.feature.searchproducts.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cl.test.feature.searchproducts.R

@Composable
fun SearchEmptyScreenComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.no_results_found),
            style = MaterialTheme.typography.h6,
        )
        Text(
            text = stringResource(id = R.string.please_try_looking_for_another_product),
            style = MaterialTheme.typography.body1,
        )
    }
}
