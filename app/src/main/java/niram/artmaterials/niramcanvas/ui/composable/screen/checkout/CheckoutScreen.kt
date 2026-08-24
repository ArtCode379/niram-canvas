package niram.artmaterials.niramcanvas.ui.composable.screen.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import niram.artmaterials.niramcanvas.ui.state.DataUiState
import niram.artmaterials.niramcanvas.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    val enabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotBlank() &&
                viewModel.customerLastName.isNotBlank() &&
                viewModel.customerEmail.isNotBlank()
        }
    }
    if (orderState is DataUiState.Populated) {
        CheckoutDialog(onConfirm = onNavigateToOrdersScreen)
    }
    CheckoutContent(
        firstName = viewModel.customerFirstName,
        lastName = viewModel.customerLastName,
        email = viewModel.customerEmail,
        emailInvalid = emailInvalid,
        modifier = modifier,
        focusManager = focusManager,
        enabled = enabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPlaceOrder = viewModel::placeOrder,
    )
}

@Composable
private fun CheckoutContent(
    firstName: String,
    lastName: String,
    email: String,
    emailInvalid: Boolean,
    modifier: Modifier,
    focusManager: FocusManager,
    enabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPlaceOrder: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Reserve your order", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Tell us who will collect the order. We will hold it at the store for 24 hours after confirmation.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        CheckoutTextField(
            input = firstName,
            onInputChange = onFirstNameChanged,
            labelText = "First name",
            modifier = Modifier.fillMaxWidth(),
        )
        CheckoutTextField(
            input = lastName,
            onInputChange = onLastNameChanged,
            labelText = "Last name",
            modifier = Modifier.fillMaxWidth(),
        )
        CheckoutTextField(
            input = email,
            onInputChange = onEmailChanged,
            labelText = "Email",
            modifier = Modifier.fillMaxWidth(),
            isError = emailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
        if (emailInvalid) {
            Text("Enter a valid email address.", color = MaterialTheme.colorScheme.error)
        }
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Collection summary", style = MaterialTheme.typography.titleLarge)
                Text("Your selected items will be reserved together.")
                Text("Collection window: 24 hours", color = MaterialTheme.colorScheme.primary)
            }
        }
        Button(
            onClick = onPlaceOrder,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Place Reservation")
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = { Text(labelText) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
    )
}
