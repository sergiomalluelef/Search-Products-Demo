package cl.test.feature.searchproducts.ui.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import cl.test.feature.searchproducts.presentation.detail.DetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.DisplayDetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.LoadingUiState
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.ui.detail.components.ErrorScreenComponent
import cl.test.feature.searchproducts.ui.detail.components.LoadingComponent
import cl.test.feature.searchproducts.ui.detail.components.TopAppBarComponent

@Composable
internal fun DetailScreen(
    uiState: State<DetailUiState>,
    item: Item,
    onBackEvent: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarComponent {
                onBackEvent.invoke()
            }
        }
    ) {
        DetailContent(
            item = item,
            uiState = uiState,
            paddingValues = it,
            onBackEvent = onBackEvent
        )
    }
}

@Composable
private fun DetailContent(
    uiState: State<DetailUiState>,
    paddingValues: PaddingValues,
    item: Item,
    onBackEvent: () -> Unit
) {
    when (val currentState = uiState.value) {
        is DisplayDetailUiState -> DisplayDetailComponent(
            item = item,
            paddingValues = paddingValues,
            detailState = currentState.detailState
        )
        LoadingUiState -> LoadingComponent()
        ErrorUiState -> ErrorScreenComponent {
            onBackEvent.invoke()
        }
    }
}