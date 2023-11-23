package com.example.mylife.reuse
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylife.R

// nút check box (chưa check được)
@Composable
fun CheckBox(modifier: Modifier = Modifier) {
    Row(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(text = stringResource(R.string.accept))
        Spacer(modifier = Modifier.width(40.dp))
        Checkbox(checked = false, onCheckedChange = null)
    }
}