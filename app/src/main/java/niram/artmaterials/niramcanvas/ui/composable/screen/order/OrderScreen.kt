package niram.artmaterials.niramcanvas.ui.composable.screen.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import niram.artmaterials.niramcanvas.data.entity.OrderEntity
import niram.artmaterials.niramcanvas.ui.composable.shared.IWURVContentWrapper
import niram.artmaterials.niramcanvas.ui.state.DataUiState
import niram.artmaterials.niramcanvas.ui.theme.Success
import niram.artmaterials.niramcanvas.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val state by viewModel.ordersState.collectAsState()
    IWURVContentWrapper(
        dataState = state,
        dataPopulated = {
            val orders = (state as DataUiState.Populated).data.sortedByDescending { it.timestamp }
            LazyColumn(
                modifier = modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(orders, key = { it.orderNumber }) { order ->
                    OrderCard(order)
                }
            }
        },
        dataEmpty = {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("No orders yet", style = MaterialTheme.typography.titleLarge)
                Text("Your reservations will appear here.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}

@Composable
private fun OrderCard(order: OrderEntity) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Order #${order.orderNumber}", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Surface(color = Success.copy(alpha = 0.14f), shape = RoundedCornerShape(50)) {
                    Text("Completed", color = Success, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
            }
            Text(order.timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")))
            Text(order.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("£%.2f".format(order.price), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text("Reserved for collection for 24 hours", style = MaterialTheme.typography.labelSmall)
        }
    }
}
