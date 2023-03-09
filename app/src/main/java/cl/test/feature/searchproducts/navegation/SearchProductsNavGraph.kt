package cl.test.feature.searchproducts.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cl.test.feature.searchproducts.presentation.DetailViewModel
import cl.test.feature.searchproducts.presentation.SearchViewModel
import cl.test.feature.searchproducts.ui.detail.DetailIntentHandler
import cl.test.feature.searchproducts.ui.search.SearchIntentHandler


@Composable
fun SearchProductsNavGraph() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val navActions = remember(navController) { SearchProductsNavActions(navController) }

    val searchIntentHandler = remember(navController) { SearchIntentHandler() }.apply {
        this.coroutineScope = coroutineScope
    }

    val detailIntentHandler = remember(navController) { DetailIntentHandler() }.apply {
        this.coroutineScope = coroutineScope
    }

    val searchViewModel = hiltViewModel<SearchViewModel>().apply {
        this.navActions = navActions
        this.processUserIntentsAndObserveUiStates(searchIntentHandler.userIntents())
    }

    val detailViewModel = hiltViewModel<DetailViewModel>().apply {
        this.processUserIntentsAndObserveUiStates(detailIntentHandler.userIntents())
    }


    NavHost(navController = navController, startDestination = SearchProductsRoutes.Searcher.path) {
        searchNav(
            intentHandler = searchIntentHandler,
            viewModel = searchViewModel
        )
        detailNav(
            intentHandler = detailIntentHandler,
            viewModel = detailViewModel,
            navController = navController,
            onBackEvent = {
                navActions.upPress.invoke()
            }
        )
    }
}