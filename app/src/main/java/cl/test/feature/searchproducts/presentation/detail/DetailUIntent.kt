package cl.test.feature.searchproducts.presentation.detail

internal sealed class DetailUIntent {
    data class InitialUIntent(val id: String) : DetailUIntent()
}