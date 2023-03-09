package cl.test.feature.searchproducts.ui.detail

import cl.test.feature.searchproducts.presentation.detail.DetailUIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailIntentHandler @Inject constructor() {
    private val userIntents = MutableSharedFlow<DetailUIntent>()
    var coroutineScope: CoroutineScope? = null

    internal fun userIntents(): Flow<DetailUIntent> = userIntents.asSharedFlow()

    internal fun initialUserIntent(id: String) {
        coroutineScope?.launch {
            userIntents.emit(DetailUIntent.InitialUIntent(id))
        }
    }
}