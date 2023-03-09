package cl.test.feature.searchproducts.ui.search

import cl.test.feature.searchproducts.presentation.search.SearchUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.InitialUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.PressingItemUIntent
import cl.test.feature.searchproducts.presentation.search.SearchUIntent.PressingSearchBarUIntent
import cl.test.feature.searchproducts.presentation.search.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchIntentHandler @Inject constructor() {
    private val userIntents = MutableSharedFlow<SearchUIntent>()
    var coroutineScope: CoroutineScope? = null

    internal fun userIntents(): Flow<SearchUIntent> = userIntents.asSharedFlow()

    internal fun initialUserIntent(category: String) {
        coroutineScope?.launch {
            userIntents.emit(InitialUIntent(category))
        }
    }

    internal fun retryIntent(category: String) {
        coroutineScope?.launch {
            userIntents.emit(InitialUIntent(category))
        }
    }

    internal fun pressingSearchBarUIntent(category: String, search: String) {
        coroutineScope?.launch {
            userIntents.emit(PressingSearchBarUIntent(category, search))
        }
    }

    internal fun pressingItemUIntent(item: Item) {
        coroutineScope?.launch {
            userIntents.emit(PressingItemUIntent(item))
        }
    }
}