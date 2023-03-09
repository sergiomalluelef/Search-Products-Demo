package cl.test.feature.searchproducts.presentation.search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val originalPrice: Int,
    val condition: String,
    val thumbnail: String,
    val installments: Installments,
    val price: Int,
    val id: String,
    val title: String,
    val officialStoreName: String
) : Parcelable