package cl.test.feature.searchproducts.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.ui.theme.Blue

@Composable
fun InitialEmptyScreenComponent(onRetryEvent: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_36))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_results_found),
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = stringResource(id = R.string.please_try_gain),
                style = MaterialTheme.typography.body1,
            )
        }
        Text(
            text = stringResource(id = R.string.retry),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Blue,
            modifier = Modifier
                .clickable {
                    onRetryEvent.invoke()
                }
                .align(Alignment.BottomCenter)
        )
    }
}
