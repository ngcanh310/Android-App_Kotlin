package com.example.mylife.reuse

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

// ô để điền. có thể chọn phương thức điền
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(modifier: Modifier = Modifier,
                    value: String,
                    onValueChange: (String) -> Unit,
                    @StringRes label: Int,
                    keyboardOptions: KeyboardOptions,

) {
    TextField(
        label = { Text(stringResource(label)) },
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().width(150.dp),
        singleLine = true,
        keyboardOptions = keyboardOptions,
    )
}