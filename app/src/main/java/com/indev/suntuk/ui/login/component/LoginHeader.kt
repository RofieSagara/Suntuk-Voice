package com.indev.suntuk.ui.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indev.suntuk.R

@Composable
fun LoginHeader(modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
        )
        Text(text = "Welcome to Suntuk", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Place to find anonymous friend", style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginHeaderPreview() {
    LoginHeader(modifier = Modifier.fillMaxWidth())
}