package cl.test.feature.searchproducts.presentation.detail

import cl.test.feature.searchproducts.presentation.detail.model.DetailState

internal sealed class DetailUiState {
    object LoadingUiState : DetailUiState()
    data class DisplayDetailUiState(val detailState: DetailState) : DetailUiState()
    object ErrorUiState : DetailUiState()
}