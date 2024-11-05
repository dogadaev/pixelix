package com.daniebeler.pfpixelix.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.daniebeler.pfpixelix.domain.model.Post
import com.daniebeler.pfpixelix.utils.BlurHashDecoder
import com.daniebeler.pfpixelix.utils.Navigate

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CustomPost(post: Post, isFullQuality: Boolean = false, navController: NavController, customModifier: Modifier = Modifier) {

    val blurHashAsDrawable = BlurHashDecoder.blurHashBitmap(
        LocalContext.current.resources,
        if (post.mediaAttachments.isNotEmpty()) {
            post.mediaAttachments[0].blurHash ?: "LEHLk~WB2yk8pyo0adR*.7kCMdnj"
        } else {
            "LEHLk~WB2yk8pyo0adR*.7kCMdnj"
        },
    )

    if (blurHashAsDrawable.bitmap != null) {
        Image(
            blurHashAsDrawable.bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = customModifier.aspectRatio(1f)
        )
    }

    if (post.sensitive) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .aspectRatio(1f)
                .clickable(onClick = {
                    Navigate.navigate("single_post_screen/" + post.id, navController)
                }),
        ) {


            Icon(
                imageVector = Icons.Outlined.VisibilityOff,
                contentDescription = null,
                Modifier.size(50.dp)
            )
        }
    } else {
        Box(customModifier.clickable(onClick = {
            Navigate.navigate("single_post_screen/" + post.id, navController)
        })) {

            if (post.mediaAttachments.isNotEmpty()) {
                if (post.mediaAttachments[0].url?.takeLast(4) == ".gif") {
                    GlideImage(
                        model = post.mediaAttachments[0].url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(
                                1f
                            )
                    )
                } else {
                    AsyncImage(
                        model = if (isFullQuality) {
                            post.mediaAttachments[0].url
                        } else {
                            post.mediaAttachments[0].previewUrl
                        },
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(1f)
                    )
                }
            }

            if (post.mediaAttachments.size > 1) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Collections,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}