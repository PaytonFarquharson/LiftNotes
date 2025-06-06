package com.example.liftnotes.component


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NumberTextField(
    value: String,
    label: String,
    onValueChangeEvent: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorMessage : String? = null
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        enabled = enabled,
        onValueChange = {
            if (it.matches(Regex("^\\d{0,4}$"))) {
                onValueChangeEvent(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(text = label) },
        textStyle = TextStyle(textAlign = TextAlign.Center),
        isError = errorMessage != null,
        supportingText = {
            errorMessage?.let {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        leadingIcon = {
            IconButton(
                onClick = {
                    onValueChangeEvent(((value.toIntOrNull() ?: 0) - 1).toString())
                    focusManager.clearFocus()
                },
                enabled = (value.toIntOrNull() ?: 0) > 0 && enabled
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrement")
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onValueChangeEvent(((value.toIntOrNull() ?: 0) + 1).toString())
                    focusManager.clearFocus()
                },
                enabled = (value.toIntOrNull() ?: 0) < 9999 && enabled
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increment")
            }
        },
        singleLine = true,
        modifier = modifier
    )
}