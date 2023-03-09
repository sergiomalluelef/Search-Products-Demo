package cl.test.feature.searchproducts.ui.search.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.ui.theme.Teal200
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun MarketCardComponent(item: Item, onClickEvent: () -> Unit) {
    Column(
        modifier = Modifier
            .heightIn(min = dimensionResource(id = R.dimen.height_in_8))
            .border(
                width = dimensionResource(id = R.dimen.width_0_1),
                color = Color.LightGray,
            )
            .clickable {
                onClickEvent.invoke()
            }
    ) {
        GlideImage(
            model = item.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_24),
                    start = dimensionResource(id = R.dimen.padding_8),
                    end = dimensionResource(id = R.dimen.padding_8),
                    bottom = dimensionResource(id = R.dimen.padding_8)
                )
                .height(dimensionResource(id = R.dimen.height_90))
                .fillMaxWidth()
        )
        if (item.officialStoreName.isNotEmpty()) {
            Text(
                text = item.officialStoreName,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_4),
                        horizontal = dimensionResource(id = R.dimen.padding_12)
                    )
                    .fillMaxWidth()
            )
        }
        Text(
            text = item.title,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_4),
                    horizontal = dimensionResource(id = R.dimen.padding_12)
                )
                .fillMaxWidth()
        )
        Text(
            text = "$ ${amountFormat(item.price)}",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_4),
                    horizontal = dimensionResource(id = R.dimen.padding_12)
                )
                .fillMaxWidth()
        )
        Text(
            text = "${item.installments.quantity}x $ ${amountFormat(item.installments.amount.toInt())} sin interés",
            style = MaterialTheme.typography.caption,
            color = Teal200,
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_4),
                    horizontal = dimensionResource(id = R.dimen.padding_12)
                )
                .fillMaxWidth()
        )

        if (item.officialStoreName.isNotEmpty()) {
            Text(
                text = "${stringResource(id = R.string.sold_by)} ${item.officialStoreName}",
                style = MaterialTheme.typography.caption,
                color = Color.Gray,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_4),
                        bottom = dimensionResource(id = R.dimen.padding_8),
                        start = dimensionResource(id = R.dimen.padding_12),
                        end = dimensionResource(id = R.dimen.padding_12),
                    )
                    .fillMaxWidth()
            )
        }
    }
}


private fun amountFormat(amount: Int) =
    "%,d".format(amount).replace(",", ".")
