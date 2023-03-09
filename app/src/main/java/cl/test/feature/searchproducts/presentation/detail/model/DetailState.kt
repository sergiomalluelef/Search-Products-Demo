package cl.test.feature.searchproducts.presentation.detail.model

internal data class DetailState(
    val description: String = "",
    val pictures: List<Picture> = emptyList(),
)
