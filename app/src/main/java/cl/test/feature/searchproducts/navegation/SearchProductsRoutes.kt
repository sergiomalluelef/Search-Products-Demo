package cl.test.feature.searchproducts.navegation

sealed class SearchProductsRoutes(val path: String) {
    object Searcher : SearchProductsRoutes(path = "Searcher")
    object Detail : SearchProductsRoutes(path = "Detail")
}