package com.daniebeler.pixelix.ui.composables.trending.trending_posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daniebeler.pixelix.ui.composables.CustomPost
import com.daniebeler.pixelix.ui.composables.CustomPullRefreshIndicator
import com.daniebeler.pixelix.ui.composables.ErrorComposable
import com.daniebeler.pixelix.ui.composables.InfinitePostsGrid
import com.daniebeler.pixelix.ui.composables.profile.ProfileTopSection

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrendingPostsComposable(
    range: String,
    navController: NavController,
    viewModel: TrendingPostsViewModel = hiltViewModel()
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.trendingState.isLoading,
        onRefresh = { viewModel.getTrendingPosts(range) }
    )

    DisposableEffect(range) {
        viewModel.getTrendingPosts(range)
        onDispose {}
    }

    Box(modifier = Modifier.fillMaxSize()) {
        InfinitePostsGrid(
            items = viewModel.trendingState.trendingPosts,
            isLoading = viewModel.trendingState.isLoading,
            isRefreshing = false,
            navController = navController,
            getItemsPaginated = { },
            before = { })

        CustomPullRefreshIndicator(
            viewModel.trendingState.isLoading,
            pullRefreshState,
        )

        ErrorComposable(message = viewModel.trendingState.error)
    }
}

@Composable
fun Heading(text: String) {
    Text(text, fontSize = 32.sp, modifier = Modifier.padding(top = 24.dp, bottom = 6.dp))
}
