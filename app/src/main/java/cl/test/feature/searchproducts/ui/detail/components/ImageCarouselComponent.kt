package cl.test.feature.searchproducts.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import cl.test.feature.searchproducts.R
import cl.test.feature.searchproducts.presentation.detail.model.Picture
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
internal fun ImageCarouselComponent(pictures: List<Picture>) {
    val pagerState = rememberPagerState()

    Box(Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            count = pictures.size,
            modifier = Modifier.heightIn(max = dimensionResource(id = R.dimen.height_in_300))
        ) { page ->
            GlideImage(
                model = pictures[page].secureUrl,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_24),
            )
        ) {
            if (pictures.isNotEmpty()) {
                ("${pagerState.currentPage + INITIAL_VALUE}/${pictures.size}").also {
                    Text(
                        text = it,
                        color = Color.Black,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_16))
                            )
                            .padding(
                                vertical = dimensionResource(id = R.dimen.padding_2),
                                horizontal = dimensionResource(id = R.dimen.padding_8)
                            )
                    )
                }
            }
        }
    }
}

private const val INITIAL_VALUE = 1