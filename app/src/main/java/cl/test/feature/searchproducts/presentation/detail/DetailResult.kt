package cl.test.feature.searchproducts.presentation.detail

import cl.test.feature.searchproducts.presentation.detail.model.Picture

internal sealed class DetailResult {
    sealed class GetDetailResult : DetailResult() {
        object InProgress : GetDetailResult()
        data class Success(
            val description: String = "",
            val pictures: List<Picture>,
        ) : GetDetailResult()

        object Error : GetDetailResult()
    }
}