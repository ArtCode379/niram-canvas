package niram.artmaterials.niramcanvas.data.repository

import niram.artmaterials.niramcanvas.data.datastore.IWURVOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class IWURVOnboardingRepo(
    private val iwurvOnboardingStoreManager: IWURVOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return iwurvOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            iwurvOnboardingStoreManager.setOnboardedState(state)
        }
    }
}