package cl.test.feature.searchproducts.ui.detail


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.presentation.detail.model.DetailState
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.ui.detail.components.ImageCarouselComponent
import cl.test.feature.searchproducts.ui.theme.Teal200

@Composable
internal fun DisplayDetailComponent(
    item: Item,
    paddingValues: PaddingValues,
    detailState: DetailState
) {
    Column(
        modifier = Modifier
            .padding(
                bottom = paddingValues.calculateBottomPadding(),
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_24),
                    end = dimensionResource(id = R.dimen.padding_24),
                    top = dimensionResource(id = R.dimen.padding_24),
                    bottom = dimensionResource(id = R.dimen.padding_4),
                )
                .fillMaxWidth()
        )
        if (item.officialStoreName.isNotEmpty()) {
            Text(
                text = "por ${item.officialStoreName}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_4),
                        bottom = dimensionResource(id = R.dimen.padding_12),
                        start = dimensionResource(id = R.dimen.padding_24),
                        end = dimensionResource(id = R.dimen.padding_24)
                    )
                    .fillMaxWidth()
            )
        }
        ImageCarouselComponent(pictures = detailState.pictures)
        Text(
            text = "$ ${amountFormat(item.price)}",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = dimensionResource(id = R.dimen.padding_4),
                    start = dimensionResource(id = R.dimen.padding_24),
                    end = dimensionResource(id = R.dimen.padding_24)
                )
                .fillMaxWidth()
        )
        Text(
            text = "${item.installments.quantity}x $ ${amountFormat(item.installments.amount.toInt())} sin interés",
            style = MaterialTheme.typography.subtitle1,
            color = Teal200,
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.padding_12),
                    start = dimensionResource(id = R.dimen.padding_24),
                    end = dimensionResource(id = R.dimen.padding_24)
                )
                .fillMaxWidth()
        )
        if (detailState.description.isNotEmpty()) {
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_12),
                        bottom = dimensionResource(id = R.dimen.padding_12),
                        start = dimensionResource(id = R.dimen.padding_24),
                        end = dimensionResource(id = R.dimen.padding_24)
                    )
                    .fillMaxWidth()
            )
            Text(
                text = detailState.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_4),
                        bottom = dimensionResource(id = R.dimen.padding_24),
                        start = dimensionResource(id = R.dimen.padding_24),
                        end = dimensionResource(id = R.dimen.padding_24)
                    )
                    .fillMaxWidth()
            )
        }
    }
}

private fun amountFormat(amount: Int) =
    "%,d".format(amount).replace(",", ".")