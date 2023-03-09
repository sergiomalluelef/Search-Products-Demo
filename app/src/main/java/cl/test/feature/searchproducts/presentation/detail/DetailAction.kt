package cl.test.feature.searchproducts.presentation.detail

internal sealed class DetailAction {
    data class GetDetailAction(val id: String) : DetailAction()
}