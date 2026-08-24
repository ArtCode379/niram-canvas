package niram.artmaterials.niramcanvas.di

import niram.artmaterials.niramcanvas.data.repository.CartRepository
import niram.artmaterials.niramcanvas.data.repository.IWURVOnboardingRepo
import niram.artmaterials.niramcanvas.data.repository.OrderRepository
import niram.artmaterials.niramcanvas.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        IWURVOnboardingRepo(
            iwurvOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}