package cl.test.feature.searchproducts.navegation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import cl.test.feature.searchproducts.presentation.DetailViewModel
import cl.test.feature.searchproducts.presentation.SearchViewModel
import cl.test.feature.searchproducts.presentation.search.mapper.Constants.CATEGORY
import cl.test.feature.searchproducts.presentation.search.model.Item
import cl.test.feature.searchproducts.ui.detail.DetailIntentHandler
import cl.test.feature.searchproducts.ui.detail.DetailScreen
import cl.test.feature.searchproducts.ui.search.SearchIntentHandler
import cl.test.feature.searchproducts.ui.search.SearchScreen

internal fun NavGraphBuilder.searchNav(
    intentHandler: SearchIntentHandler,
    viewModel: SearchViewModel
) = composable(
    route = SearchProductsRoutes.Searcher.path
) {
    val uiState = remember {
        viewModel.uiState()
    }.collectAsState(initial = viewModel.loadingUiState)

    val emitInitialIntent = remember {
        mutableStateOf(true)
    }

    if (emitInitialIntent.value && viewModel.isGoToDetail.not()) {
        intentHandler.initialUserIntent(CATEGORY)
        emitInitialIntent.value = false
    }

    SearchScreen(intentHandler = intentHandler, uiState = uiState, viewModel = viewModel)

}

internal fun NavGraphBuilder.detailNav(
    intentHandler: DetailIntentHandler,
    viewModel: DetailViewModel,
    navController: NavHostController,
    onBackEvent: () -> Unit
) = composable(
    route = SearchProductsRoutes.Detail.path
) {
    val item = navController.previousBackStackEntry?.savedStateHandle?.get<Item>("item")
    val uiState = remember {
        viewModel.uiState()
    }.collectAsState(initial = viewModel.loadingUiState)

    item?.apply {
        val emitInitialIntent = remember {
            mutableStateOf(true)
        }

        if (emitInitialIntent.value) {
            intentHandler.initialUserIntent(item.id)
            emitInitialIntent.value = false
        }
        DetailScreen(
            uiState = uiState,
            item = item,
            onBackEvent = onBackEvent
        )
    }
}