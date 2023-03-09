package cl.test.feature.searchproducts.presentation.detail

import cl.test.feature.searchproducts.presentation.detail.DetailResult.GetDetailResult
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.DisplayDetailUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.ErrorUiState
import cl.test.feature.searchproducts.presentation.detail.DetailUiState.LoadingUiState
import cl.test.feature.searchproducts.presentation.detail.model.DetailState
import io.mockk.justRun
import org.junit.Test
import kotlin.RuntimeException
import kotlin.random.Random

internal class DetailReducerTest {
    private val reducer = DetailReducer()

    @Test
    fun `given LoadingUiState, with Result InProgress, when reduce return LoadingUiState`() {
        val previousUiState = LoadingUiState
        val result = GetDetailResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test
    fun `given LoadingUiState, with Result Error, when reduce return ErrorUiState`() {
        val previousUiState = LoadingUiState
        val result = GetDetailResult.Error

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is ErrorUiState)
    }

    @Test
    fun `given LoadingUiState, with Result Success, when reduce return DisplayDetailUiState`() {
        val previousUiState = LoadingUiState
        val result = GetDetailResult.Success(
            description = Random.toString(),
            pictures = listOf()
        )

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is DisplayDetailUiState)
    }

    @Test
    fun `given DisplayDetailUiState, with Result InProgress, when reduce return LoadingUiState`() {
        val previousUiState = DisplayDetailUiState(
            detailState = DetailState(
                description = Random.toString(),
                pictures = listOf()
            )
        )
        val result = GetDetailResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test(expected = RuntimeException::class)
    fun `given DisplayDetailUiState, with any result, when throw exception`() {
        val previousUiState = DisplayDetailUiState(
            detailState = DetailState(
                description = Random.toString(),
                pictures = listOf()
            )
        )
        val result = GetDetailResult.Error
        stubThrowRuntimeException(result.toString())
        with(reducer) { previousUiState reduceWith result }
    }

    @Test
    fun `given ErrorUiState, with Result InProgress, when reduce return LoadingUiState`() {
        val previousUiState = ErrorUiState
        val result = GetDetailResult.InProgress

        val newState = with(reducer) { previousUiState reduceWith result }

        assert(newState is LoadingUiState)
    }

    @Test(expected = RuntimeException::class)
    fun `given ErrorUiState, with any result, when throw exception`() {
        val previousUiState = ErrorUiState
        val result = GetDetailResult.Error
        stubThrowRuntimeException(result.toString())
        with(reducer) { previousUiState reduceWith result }
    }

    private fun stubThrowRuntimeException(error: String) {
        justRun { throw RuntimeException(error) }
    }
}