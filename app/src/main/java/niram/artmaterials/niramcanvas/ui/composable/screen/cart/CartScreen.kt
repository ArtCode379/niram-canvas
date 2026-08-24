package niram.artmaterials.niramcanvas.ui.composable.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import niram.artmaterials.niramcanvas.ui.composable.shared.IWURVContentWrapper
import niram.artmaterials.niramcanvas.ui.state.CartItemUiState
import niram.artmaterials.niramcanvas.ui.state.DataUiState
import niram.artmaterials.niramcanvas.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val state by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val total by viewModel.totalPrice.collectAsStateWithLifecycle()
    IWURVContentWrapper(
        dataState = state,
        dataPopulated = {
            CartContent(
                items = (state as DataUiState.Populated).data,
                total = total,
                modifier = modifier,
                onPlus = viewModel::incrementProductInCart,
                onMinus = { item ->
                    if (item.quantity == 1) {
                        viewModel.deleteFromCart(item.productId)
                    } else {
                        viewModel.decrementItemInCart(item.productId)
                    }
                },
                onDelete = viewModel::deleteFromCart,
                onCheckout = onNavigateToCheckoutScreen,
            )
        },
        dataEmpty = {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    Icons.Default.RemoveShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text("Your cart is ready for inspiration", style = MaterialTheme.typography.titleLarge)
                Text("Start Shopping", color = MaterialTheme.colorScheme.primary)
            }
        },
    )
}

@Composable
private fun CartContent(
    items: List<CartItemUiState>,
    total: Double,
    modifier: Modifier,
    onPlus: (Int) -> Unit,
    onMinus: (CartItemUiState) -> Unit,
    onDelete: (Int) -> Unit,
    onCheckout: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items, key = { it.productId }) { item ->
                Card(shape = RoundedCornerShape(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        AsyncImage(
                            model = item.productImageUrl,
                            contentDescription = item.productTitle,
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.productTitle, fontWeight = FontWeight.Bold)
                            Text("£%.2f".format(item.productPrice), color = MaterialTheme.colorScheme.primary)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(onClick = { onMinus(item) }) {
                                    Text("−")
                                }
                                Text(item.quantity.toString(), modifier = Modifier.padding(horizontal = 12.dp))
                                OutlinedButton(onClick = { onPlus(item.productId) }) {
                                    Text("+")
                                }
                            }
                        }
                        IconButton(onClick = { onDelete(item.productId) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove ${item.productTitle}")
                        }
                    }
                }
            }
        }
        Text("Subtotal  £%.2f".format(total), modifier = Modifier.fillMaxWidth())
        Text("Total  £%.2f".format(total), style = MaterialTheme.typography.titleLarge)
        Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth()) {
            Text("Proceed to Checkout")
        }
    }
}
