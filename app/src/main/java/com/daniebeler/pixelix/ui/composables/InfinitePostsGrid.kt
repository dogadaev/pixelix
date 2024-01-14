package com.daniebeler.pixelix.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daniebeler.pixelix.domain.model.Post
import com.daniebeler.pixelix.ui.composables.post.PostComposable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfinitePostsGrid(
    items: List<Post>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    navController: NavController,
    getItemsPaginated: () -> Unit,
    before: @Composable () -> Unit) {

    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        //modifier = Modifier.pullRefresh(pullRefreshState),
        state = lazyGridState,
        columns = GridCells.Fixed(3)
    ) {
        item (span = { GridItemSpan(3) }) {
            before()
        }

        items(items, key = {
            it.id
        }) { photo ->
            CustomPost(post = photo, navController = navController)
        }

        if (items.isNotEmpty() && isLoading && isRefreshing) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .wrapContentSize(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }

    InfiniteGridHandler(lazyGridState = lazyGridState) {
        getItemsPaginated()
    }
}