package niram.artmaterials.niramcanvas.di

import niram.artmaterials.niramcanvas.data.datastore.IWURVOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { IWURVOnboardingPrefs(androidContext()) }
}