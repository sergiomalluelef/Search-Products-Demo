package cl.test.feature.searchproducts.ui.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.ui.theme.Yellow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopAppBarComponent(search: String, onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf(search) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Yellow)
    ) {

        TextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_16))
                )
            },
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text(text = stringResource(id = R.string.search_in_mercado_libre)) },
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_30))
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.Black,
            ),
            shape = RoundedCornerShape(
                topStart = dimensionResource(id = R.dimen.corner_10),
                topEnd = dimensionResource(id = R.dimen.corner_10),
                bottomStart = dimensionResource(id = R.dimen.corner_10),
                bottomEnd = dimensionResource(id = R.dimen.corner_10)
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearch.invoke(searchText)
                }
            )
        )
    }
}
