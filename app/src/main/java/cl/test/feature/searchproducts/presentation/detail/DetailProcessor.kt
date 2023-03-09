package cl.test.feature.searchproducts.presentation.detail

import cl.test.feature.searchproducts.data.SearchProductsDataRepository
import cl.test.feature.searchproducts.presentation.detail.DetailAction.GetDetailAction
import cl.test.feature.searchproducts.presentation.detail.DetailResult.GetDetailResult
import cl.test.feature.searchproducts.presentation.detail.DetailResult.GetDetailResult.Success
import cl.test.feature.searchproducts.presentation.detail.mapper.DetailMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class DetailProcessor @Inject constructor(
    private val repository: SearchProductsDataRepository,
    private val mapper: DetailMapper
) {
    fun actionProcessor(actions: DetailAction): Flow<DetailResult> =
        when (actions) {
            is GetDetailAction -> getDetailProcessor(actions.id)
        }

    private fun getDetailProcessor(id: String) =
        flow<GetDetailResult> {
            val picturesResponse = repository.getPictures(id).first()
            val descriptionResponse = repository.getItemDescription(id).first()
            val itemDescription = with(mapper) { descriptionResponse.toDescription() }.plainText
            val itemPictures = with(mapper) { picturesResponse.toPictures() }.pictures
            emit(
                Success(
                    description = itemDescription,
                    pictures = itemPictures
                )
            )
        }.onStart {
            emit(GetDetailResult.InProgress)
        }.catch {
            emit(GetDetailResult.Error)
        }.flowOn(Dispatchers.IO)
}