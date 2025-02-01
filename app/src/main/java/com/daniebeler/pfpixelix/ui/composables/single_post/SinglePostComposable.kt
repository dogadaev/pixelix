package com.daniebeler.pfpixelix.ui.composables.single_post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daniebeler.pfpixelix.R
import com.daniebeler.pfpixelix.ui.composables.post.PostComposable
import com.daniebeler.pfpixelix.ui.composables.states.ErrorComposable
import com.daniebeler.pfpixelix.ui.composables.states.LoadingComposable
import com.daniebeler.pfpixelix.utils.Navigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePostComposable(
    navController: NavController,
    postId: String,
    refresh: Boolean,
    openReplies: Boolean,
    viewModel: SinglePostViewModel = hiltViewModel(key = "single-post$postId")
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getPost(postId)
    }

    LaunchedEffect(refresh) {
        if (refresh) {
            viewModel.postState = SinglePostState()
            viewModel.getPost(postId)
        }
    }

    Scaffold(contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top), topBar = {
        CenterAlignedTopAppBar(title = {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.post), fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(
                        id = R.string.by, (viewModel.postState.post?.account?.username ?: "")
                    ), fontSize = 12.sp, lineHeight = 6.sp
                )
            }
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.chevron_back_outline), contentDescription = ""
                )
            }
        })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                if (viewModel.postState.post != null) {
                    PostComposable(viewModel.postState.post!!, navController, postGetsDeleted = {
                        Navigate.navigateAndDeleteBackStack("own_profile_screen", navController)
                    },
                        setZindex = { }, openReplies)
                }
            }

            LoadingComposable(isLoading = viewModel.postState.isLoading)
            ErrorComposable(message = viewModel.postState.error)
        }
    }
}