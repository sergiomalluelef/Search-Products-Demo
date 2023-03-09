package cl.test.feature.searchproducts.presentation

import cl.test.feature.searchproducts.factory.DetailFactory.makeDetailState
import cl.test.feature.searchproducts.presentation.detail.DetailAction
import cl.test.feature.searchproducts.presentation.detail.DetailProcessor
import cl.test.feature.searchproducts.presentation.detail.DetailReducer
import cl.test.feature.searchproducts.presentation.detail.DetailResult
import cl.test.feature.searchproducts.presentation.detail.DetailResult.GetDetailResult
import cl.test.feature.searchproducts.presentation.detail.DetailUIntent.InitialUIntent
import cl.test.feature.searchproducts.presentation.detail.DetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.DisplayDetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.LoadingUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

internal class DetailViewModelTest {
    private val processor = mockk<DetailProcessor>()
    private val reducer = mockk<DetailReducer>()
    private val viewModel = DetailViewModel(reducer, processor)

    @Test
    fun `given InitialUIntent, when call InProgress, then result LoadingUiState`() = runBlocking {
        val result = GetDetailResult.InProgress
        val id = Random.toString()
        stubProcessorAction(
            action = DetailAction.GetDetailAction(id),
            result = result
        )
        stubReducerReduceWith(
            previousState = LoadingUiState,
            result = result,
            nexUiState = LoadingUiState
        )

        val stateFlow = viewModel.uiState()
        viewModel.processUserIntentsAndObserveUiStates(
            userIntents = flow { emit(InitialUIntent(id)) },
            coroutineScope = this
        )

        val resultUiState = stateFlow.first()
        assertEquals(resultUiState, LoadingUiState)
    }

    @Test
    fun `given InitialUIntent, when call InProgress, then result DisplayDetailUiState`() =
        runBlocking {

            val result = GetDetailResult.Success(
                description = Random.toString(),
                pictures = listOf()
            )
            val id = Random.toString()
            val detailState = makeDetailState()
            stubProcessorAction(
                action = DetailAction.GetDetailAction(id),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = DisplayDetailUiState(detailState)
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(InitialUIntent(id)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), DisplayDetailUiState(detailState))
        }

    @Test
    fun `given InitialUIntent, when call Error, then result ErrorUiState`() =
        runBlocking {
            val result = GetDetailResult.Error
            val id = Random.toString()
            stubProcessorAction(
                action = DetailAction.GetDetailAction(id),
                result = result
            )
            stubReducerReduceWith(
                previousState = LoadingUiState,
                result = result,
                nexUiState = ErrorUiState
            )

            val stateFlow = viewModel.uiState()
            viewModel.processUserIntentsAndObserveUiStates(
                userIntents = flow { emit(InitialUIntent(id)) },
                coroutineScope = this
            )

            val resultUiState = stateFlow.take(2).toList()
            assertEquals(resultUiState.last(), ErrorUiState)
        }

    private fun stubProcessorAction(
        action: DetailAction,
        result: DetailResult
    ) {
        coEvery { processor.actionProcessor(action) } returns flow { emit(result) }
    }

    private fun stubReducerReduceWith(
        previousState: DetailUiState,
        result: DetailResult,
        nexUiState: DetailUiState
    ) {
        with(reducer) {
            every { previousState reduceWith result } returns nexUiState
        }
    }
}