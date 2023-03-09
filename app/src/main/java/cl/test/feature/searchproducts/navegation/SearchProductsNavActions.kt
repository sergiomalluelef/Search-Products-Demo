package cl.test.feature.searchproducts.navegation

import androidx.navigation.NavHostController
import cl.test.feature.searchproducts.presentation.search.model.Item

class SearchProductsNavActions(navController: NavHostController) {

    val detail: (Item) -> Unit = {
        navController.currentBackStackEntry?.savedStateHandle?.set("item", it)
        navController.navigate(SearchProductsRoutes.Detail.path)
    }

    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}