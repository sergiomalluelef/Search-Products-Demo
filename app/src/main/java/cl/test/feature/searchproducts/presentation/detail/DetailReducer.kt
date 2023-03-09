package cl.test.feature.searchproducts.presentation.detail

import cl.test.feature.searchproducts.presentation.detail.DetailResult.GetDetailResult
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.DisplayDetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.LoadingUiState
import cl.test.feature.searchproducts.presentation.detail.model.DetailState
import javax.inject.Inject

internal class DetailReducer @Inject constructor() {

    infix fun DetailUiState.reduceWith(result: DetailResult): DetailUiState {
        return when (val previousState = this) {
            is DisplayDetailUiState -> previousState reduceWith result
            is ErrorUiState -> previousState reduceWith result
            is LoadingUiState -> previousState reduceWith result
        }
    }

    private infix fun LoadingUiState.reduceWith(result: DetailResult): DetailUiState {
        return when (result) {
            is GetDetailResult.InProgress -> LoadingUiState
            is GetDetailResult.Error -> ErrorUiState
            is GetDetailResult.Success -> DisplayDetailUiState(
                detailState = DetailState(
                    description = result.description,
                    pictures = result.pictures
                )
            )
        }
    }

    private infix fun DisplayDetailUiState.reduceWith(result: DetailResult): DetailUiState =
        when (result) {
            is GetDetailResult.InProgress -> LoadingUiState
            else -> throw RuntimeException(result.toString())
        }

    private infix fun ErrorUiState.reduceWith(result: DetailResult): DetailUiState =
        when (result) {
            is GetDetailResult.InProgress -> LoadingUiState
            else -> throw RuntimeException(result.toString())
        }
}