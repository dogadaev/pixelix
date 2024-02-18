package com.daniebeler.pixelix.ui.composables.settings.about_instance

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniebeler.pixelix.common.Resource
import com.daniebeler.pixelix.domain.usecase.GetInstanceUseCase
import com.daniebeler.pixelix.domain.usecase.GetOwnInstanceDomainUseCase
import com.daniebeler.pixelix.domain.usecase.OpenExternalUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutInstanceViewModel @Inject constructor(
    private val getInstanceUseCase: GetInstanceUseCase,
    private val getOwnInstanceDomainUseCase: GetOwnInstanceDomainUseCase,
    private val openExternalUrlUseCase: OpenExternalUrlUseCase
) : ViewModel() {

    var instanceState by mutableStateOf(InstanceState())

    var ownInstanceDomain by mutableStateOf("")

    init {
        getInstance()
        viewModelScope.launch {
            getInstanceDomain()
        }
    }

    private suspend fun getInstanceDomain() {
        getOwnInstanceDomainUseCase().collect { res ->
            ownInstanceDomain = res
        }
    }

    private fun getInstance() {
        getInstanceUseCase().onEach { result ->
            instanceState = when (result) {
                is Resource.Success -> {
                    InstanceState(instance = result.data)
                }

                is Resource.Error -> {
                    InstanceState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    InstanceState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun openUrl(context: Context, url: String) {
        openExternalUrlUseCase(context, url)
    }

}