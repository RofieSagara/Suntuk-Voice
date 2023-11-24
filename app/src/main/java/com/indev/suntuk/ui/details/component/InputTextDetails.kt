package com.indev.suntuk.ui.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indev.suntuk.R

@Composable
fun InputTextDetails(
    modifier: Modifier = Modifier,
    replyToNickname: String,
    isAnonymous :Boolean,
    onAnonymousChange: (Boolean) -> Unit,
    onSubmit: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    val submitHandler by rememberUpdatedState(newValue = {
        onSubmit.invoke(text)
        text = ""
    })

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (replyToNickname.isNotBlank()) {
                Text(
                    text = "Reply to @$replyToNickname",
                    style = MaterialTheme.typography.bodyMedium
                        .copy(color = MaterialTheme.colorScheme.onSurface)
                )
            }
            Spacer(modifier = Modifier.weight(1f, true))
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = isAnonymous, onCheckedChange = onAnonymousChange, thumbContent = {
                Icon(
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                    painter = painterResource(id = R.drawable.ic_anonymouse),
                    contentDescription = null
                )
            })
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f, true),
                value = text,
                onValueChange = { text = it },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                label = { Text(text = "Type your message") },
                maxLines = 3
            )
            IconButton(onClick = submitHandler) {
                Icon(painter = painterResource(id = R.drawable.ic_send), contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
private fun InputTextDetailsPreview() {
    var isAnonymous by remember { mutableStateOf(false) }

    InputTextDetails(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        replyToNickname = "Visual Studio Code",
        isAnonymous = isAnonymous,
        onAnonymousChange = { isAnonymous = it },
        onSubmit = {}
    )
}