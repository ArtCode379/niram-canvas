package niram.artmaterials.niramcanvas.ui.composable.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import niram.artmaterials.niramcanvas.R
import niram.artmaterials.niramcanvas.data.model.Product
import niram.artmaterials.niramcanvas.data.model.ProductCategory
import niram.artmaterials.niramcanvas.ui.composable.shared.IWURVContentWrapper
import niram.artmaterials.niramcanvas.ui.composable.shared.IWURVEmptyView
import niram.artmaterials.niramcanvas.ui.state.DataUiState
import niram.artmaterials.niramcanvas.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()
    IWURVContentWrapper(
        dataState = productsState,
        dataPopulated = {
            ProductCatalog(
                products = (productsState as DataUiState.Populated).data,
                modifier = modifier,
                onProductClick = onNavigateToProductDetails,
                onAddToCart = viewModel::addToCart,
            )
        },
        dataEmpty = {
            IWURVEmptyView(
                primaryText = stringResource(R.string.iwurv_products_state_empty_primary_text),
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ProductCatalog(
    products: List<Product>,
    modifier: Modifier,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Int) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
    var searchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val filtered = products.filter { product ->
        (selectedCategory == null || product.category == selectedCategory) &&
            product.title.contains(searchQuery, ignoreCase = true)
    }
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Niram Canvas", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Tools for every creative idea", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = { searchVisible = !searchVisible }) {
                Icon(Icons.Default.Search, contentDescription = "Search products")
            }
        }
        if (searchVisible) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.padding(16.dp),
                    singleLine = true,
                    decorationBox = { inner ->
                        if (searchQuery.isEmpty()) {
                            Text("Search paint, paper, brushes…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        inner()
                    },
                )
            }
        }
        FeaturedProducts(products.take(4), onProductClick)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                AssistChip(
                    onClick = { selectedCategory = null },
                    label = { Text("All") },
                    colors = categoryColors(selectedCategory == null),
                )
            }
            items(ProductCategory.entries) { category ->
                AssistChip(
                    onClick = { selectedCategory = category },
                    label = { Text(stringResource(category.titleRes)) },
                    colors = categoryColors(selectedCategory == category),
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(filtered, key = { it.id }) { product ->
                ProductCard(product, onProductClick, onAddToCart)
            }
        }
    }
}

@Composable
private fun FeaturedProducts(products: List<Product>, onProductClick: (Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { products.size })
    LaunchedEffect(pagerState.currentPage, products.size) {
        delay(4_000)
        if (products.isNotEmpty()) {
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % products.size)
        }
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HorizontalPager(state = pagerState) { page ->
            val product = products[page]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onProductClick(product.id) },
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                Surface(
                    modifier = Modifier.align(Alignment.BottomStart).padding(12.dp),
                    color = Color.Black.copy(alpha = 0.72f),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                        Text(product.title, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("£%.2f".format(product.price), color = Color.White)
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            products.indices.forEach { index ->
                Text(
                    text = "●",
                    color = if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 3.dp),
                )
            }
        }
    }
}

@Composable
private fun ProductCard(product: Product, onProductClick: (Int) -> Unit, onAddToCart: (Int) -> Unit) {
    Card(
        modifier = Modifier.clickable { onProductClick(product.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            modifier = Modifier.fillMaxWidth().height(120.dp),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.padding(10.dp)) {
            Text(product.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, maxLines = 2)
            Text(
                stringResource(product.category.titleRes),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "£%.2f".format(product.price),
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = { onAddToCart(product.id) }) {
                    Icon(
                        Icons.Default.AddShoppingCart,
                        contentDescription = "Add ${product.title} to cart",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun categoryColors(selected: Boolean) = AssistChipDefaults.assistChipColors(
    containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
    labelColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
)
