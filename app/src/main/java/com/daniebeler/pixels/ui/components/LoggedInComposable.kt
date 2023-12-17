package com.daniebeler.pixels.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daniebeler.pixels.MainViewModel
import com.daniebeler.pixels.models.api.AccessToken
import com.daniebeler.pixels.models.api.Application
import com.daniebeler.pixels.models.api.CountryRepository
import com.daniebeler.pixels.models.api.CountryRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoggedInComposable(viewModel: MainViewModel, navController: NavController, code: String) {

    val repository: CountryRepository = CountryRepositoryImpl()

    var token: AccessToken? by remember {
        mutableStateOf(null)
    }

    println("ausgabe")
    println(code)
    println(viewModel._authApplication)

    if (viewModel._authApplication != null) {
        println("auth not null")
        CoroutineScope(Dispatchers.Default).launch {
            token = repository.obtainToken(viewModel._authApplication!!.clientId, viewModel._authApplication!!.clientSecret, code)
        }
    }


    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("Login")
                }
            )

        }
    ) {paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            if (token != null) {
                Text(text = token!!.accessToken)
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}