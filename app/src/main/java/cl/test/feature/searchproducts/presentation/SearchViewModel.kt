package cl.test.feature.searchproducts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.test.feature.searchproducts.navegation.SearchProductsNavActions
import cl.test.feature.searchproducts.presentation.search.SearchAction
import cl.test.feature.searchproducts.presentation.search.SearchAction.DisplayItemAction
import cl.test.feature.searchproducts.presentation.search.SearchAction.GetItemsAction
import cl.test.feature.searchproducts.presentation.search.SearchAction.SearchItemsAction
import cl.test.feature.searchproducts.presentation.search.SearchProcessor
import cl.test.feature.searchproducts.presentation.search.SearchReducer
import cl.test.feature.searchproducts.presentation.search.SearchResult
import cl.test.feature.searchproducts.presentation.search.SearchResult.DisplayItemResult
import cl.test.feature.searchproducts.presentation.search.SearchUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.InitialUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.PressingItemUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.PressingSearchBarUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUiState
import cl.test.feature.searchproducts.presentation.search.SearchUiState.LoadingUiState
import cl.test.feature.searchproducts.presentation.search.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val reducer: SearchReducer,
    private val processor: SearchProcessor
) : ViewModel() {
    val loadingUiState: SearchUiState = LoadingUiState
    private val uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(loadingUiState)
    var navActions: SearchProductsNavActions? = null
    var isGoToDetail: Boolean = false
    var searchText by mutableStateOf("")

    fun processUserIntentsAndObserveUiStates(
        userIntents: Flow<SearchUIntent>,
        coroutineScope: CoroutineScope = viewModelScope
    ) {
        userIntents.buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .checkResultForNav()
            .scan(loadingUiState) { previousState, result ->
                with(reducer) { previousState reduceWith result }
            }.onEach { uiState.value = it }
            .launchIn(coroutineScope)
    }

    private fun SearchUIntent.toAction(): SearchAction = when (this) {
        is InitialUIntent -> GetItemsAction(category)
        is PressingSearchBarUIntent -> SearchItemsAction(category, search)
        is PressingItemUIntent -> DisplayItemAction(item)
    }

    private fun Flow<SearchResult>.checkResultForNav(): Flow<SearchResult> =
        onEach { result ->
            when (result) {
                is DisplayItemResult.Success -> goToDetail(result.item)
                else -> return@onEach
            }
        }

    private fun goToDetail(item: Item) {
        navActions?.detail?.invoke(item)
        isGoToDetail = true
    }

    fun uiState(): StateFlow<SearchUiState> = uiState

    fun onSearchTextChanged(newText: String) {
        searchText = newText
    }
}