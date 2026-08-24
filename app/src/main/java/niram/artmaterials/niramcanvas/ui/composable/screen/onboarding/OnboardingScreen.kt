package niram.artmaterials.niramcanvas.ui.composable.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import niram.artmaterials.niramcanvas.R
import niram.artmaterials.niramcanvas.ui.viewmodel.IWURVOnboardingVM
import org.koin.androidx.compose.koinViewModel

private data class OnboardingPage(val title: String, val description: String, val image: String)

private val pages = listOf(
    OnboardingPage(
        "Everything for your studio",
        "Explore paints, brushes, canvas, paper, drawing tools, and craft materials selected for makers.",
        "https://images.unsplash.com/photo-1513364776144-60967b0f800f?w=1200",
    ),
    OnboardingPage(
        "Find the right material",
        "Browse clear categories and detailed product notes, whether you are starting out or refining your practice.",
        "https://images.unsplash.com/photo-1561839561-b13bcfe95249?w=1200",
    ),
    OnboardingPage(
        "Reserve, then collect",
        "Place a reservation in minutes. Your order will be held at the store for 24 hours.",
        "https://images.unsplash.com/photo-1549490349-8643362247b5?w=1200",
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: IWURVOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSet by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    LaunchedEffect(onboardingSet) {
        if (onboardingSet) {
            onNavigateToHomeScreen()
        }
    }
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = listOf(Icons.Default.Brush, Icons.Default.LocalMall, Icons.Default.Storefront)[index],
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(20.dp))
                Text(page.title, style = MaterialTheme.typography.titleLarge, fontSize = 22.sp)
                Spacer(Modifier.height(10.dp))
                Text(page.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(28.dp))
                AsyncImage(
                    model = page.image,
                    contentDescription = null,
                    modifier = Modifier.size(180.dp, 120.dp).clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            pages.indices.forEach { index ->
                Text(
                    text = "●",
                    color = if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(4.dp),
                )
            }
        }
        if (pagerState.currentPage == pages.lastIndex) {
            Button(onClick = viewModel::setOnboarded, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.iwurv_start_button_title))
            }
        }
    }
}
