package cl.test.feature.searchproducts.ui.detail.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.ui.theme.Blue

@Composable
fun ErrorScreenComponent(onBackEvent: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(36.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.an_unexpected_error_occurred),
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = stringResource(id = R.string.please_try_gain),
                style = MaterialTheme.typography.body1,
            )
        }
        Text(
            text = stringResource(id = R.string.on_back),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Blue,
            modifier = Modifier
                .clickable {
                    onBackEvent.invoke()
                }
                .align(Alignment.BottomCenter)
        )
    }
}
