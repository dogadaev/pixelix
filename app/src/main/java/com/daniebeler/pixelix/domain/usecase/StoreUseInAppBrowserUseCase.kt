package com.daniebeler.pixelix.domain.usecase

import com.daniebeler.pixelix.domain.repository.StorageRepository

class StoreUseInAppBrowserUseCase(
    private val storageRepository: StorageRepository
) {

    suspend operator fun invoke(useInAppBrowser: Boolean) {
        return storageRepository.storeUseInAppBrowser(useInAppBrowser)
    }
}