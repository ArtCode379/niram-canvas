package niram.artmaterials.niramcanvas.di

import niram.artmaterials.niramcanvas.ui.viewmodel.AppViewModel
import niram.artmaterials.niramcanvas.ui.viewmodel.CartViewModel
import niram.artmaterials.niramcanvas.ui.viewmodel.CheckoutViewModel
import niram.artmaterials.niramcanvas.ui.viewmodel.IWURVOnboardingVM
import niram.artmaterials.niramcanvas.ui.viewmodel.OrderViewModel
import niram.artmaterials.niramcanvas.ui.viewmodel.ProductDetailsViewModel
import niram.artmaterials.niramcanvas.ui.viewmodel.ProductViewModel
import niram.artmaterials.niramcanvas.ui.viewmodel.IWURVSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        IWURVSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        IWURVOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}