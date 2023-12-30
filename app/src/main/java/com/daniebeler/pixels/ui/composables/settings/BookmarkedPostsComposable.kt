package com.daniebeler.pixels.ui.composables.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daniebeler.pixels.ui.composables.trending.posts.CustomPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkedPostsComposable(navController: NavController, viewModel: BookmarkedPostsViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Bookmarked Posts")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )

        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(viewModel.bookmarkedPosts, key = {
                    it.id
                }) { photo ->
                    CustomPost(post = photo, navController = navController)
                }
            })
    }
}