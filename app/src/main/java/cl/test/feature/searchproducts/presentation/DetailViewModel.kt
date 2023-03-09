package cl.test.feature.searchproducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.test.feature.searchproducts.presentation.detail.DetailAction
import cl.test.feature.searchproducts.presentation.detail.DetailAction.GetDetailAction
import cl.test.feature.searchproducts.presentation.detail.DetailProcessor
import cl.test.feature.searchproducts.presentation.detail.DetailReducer
import cl.test.feature.searchproducts.presentation.detail.DetailUIntent
import cl.test.feature.searchproducts.presentation.detail.DetailUIntent.InitialUIntent
import cl.test.feature.searchproducts.presentation.detail.DetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.LoadingUiState
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
internal class DetailViewModel @Inject constructor(
    private val reducer: DetailReducer,
    private val processor: DetailProcessor
) : ViewModel() {
    val loadingUiState: DetailUiState = LoadingUiState
    private val uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(loadingUiState)

    fun processUserIntentsAndObserveUiStates(
        userIntents: Flow<DetailUIntent>,
        coroutineScope: CoroutineScope = viewModelScope
    ) {
        userIntents.buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }.scan(loadingUiState) { previousState, result ->
                with(reducer) { previousState reduceWith result }
            }.onEach { uiState.value = it }
            .launchIn(coroutineScope)
    }

    private fun DetailUIntent.toAction(): DetailAction = when (this) {
        is InitialUIntent -> GetDetailAction(id)
    }

    fun uiState(): StateFlow<DetailUiState> = uiState
}