package cl.test.feature.searchproducts.presentation.search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Installments(
    val amount: Double,
    val quantity: Int,
    val rate: Int,
    val currencyId: String
) : Parcelable

fun Installments?.orEmpty(): Installments = this ?: Installments(
    amount = 0.0,
    quantity = 0,
    rate = 0,
    currencyId = ""
)