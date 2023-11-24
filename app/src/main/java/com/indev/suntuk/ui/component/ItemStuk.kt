package com.indev.suntuk.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indev.suntuk.R

@Composable
fun ItemStuk(modifier: Modifier = Modifier) {
    Column {
        BaseContent(
            modifier = modifier,
            action = {
                ActionsStuk(modifier = Modifier.fillMaxWidth())
            },
            content = {
                // ImageContent(modifier = Modifier.fillMaxWidth())
                VoiceContent(modifier = Modifier.fillMaxWidth())
            },
            quote = {
                BaseContent(modifier = Modifier.padding(start = 50.dp))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ItemThread(modifier: Modifier = Modifier, isHeader: Boolean = false) {
    Column{
        if (isHeader) {
            BaseContent(
                modifier = modifier,
                action = {
                    ActionsStukThread(modifier = Modifier.fillMaxWidth())
                },
                content = {
                    // ImageContent(modifier = Modifier.fillMaxWidth())
                    // VoiceContent(modifier = Modifier.fillMaxWidth())
                }
            )
        } else {
            BaseThread(
                modifier = modifier,
                action = {
                    ActionsStukThread(modifier = Modifier.fillMaxWidth())
                },
                content = {
                    // ImageContent(modifier = Modifier.fillMaxWidth())
                    // VoiceContent(modifier = Modifier.fillMaxWidth())
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun BaseContent(
    modifier: Modifier = Modifier,
    action: (@Composable ()->Unit)? = null,
    content:  (@Composable ()->Unit)? = null,
    quote: (@Composable ()->Unit)? = null,
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Visual Studio Code", style = MaterialTheme.typography.titleSmall)
                Text(text = " - ", style = MaterialTheme.typography.bodyMedium)
                Text(text = "7 h", style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(id = R.string.lorem_ipsum), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            content?.invoke()
            action?.invoke()
        }
        quote?.invoke()
    }
}

@Composable
private fun BaseThread(
    modifier: Modifier = Modifier,
    action: (@Composable ()->Unit)? = null,
    content:  (@Composable ()->Unit)? = null,
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(id = R.string.lorem_ipsum), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            content?.invoke()
            action?.invoke()
        }
    }
}

@Composable
private fun ActionsStuk(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Show Threads")
            }
            Spacer(modifier = Modifier.weight(1f, true))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_comment), contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_repost), contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_not_favorite), contentDescription = null)
            }
        }
        Text(text = "1.2K Likes - 12 Comments", style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun ActionsStukThread(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_comment), contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_repost), contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_not_favorite), contentDescription = null)
            }
        }
        Text(text = "1.2K Likes - 12 Comments", style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun ImageContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_multiple_image),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                    .padding(4.dp),
                tint = MaterialTheme.colorScheme.background
            )
            Text(
                text = "1/10",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun VoiceContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_voice),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f, true)
                .height(ButtonDefaults.MinHeight),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Text(
                text = "12:02 - 12mb",
                style = MaterialTheme.typography.labelSmall
                    .copy(color = MaterialTheme.colorScheme.onPrimary),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VoiceContentPreview() {
    VoiceContent(modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun ImageContentPreview() {
    ImageContent(modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun ItemStukPreview() {
    ItemStuk(modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun ItemThreadPreview() {
    ItemThread(modifier = Modifier.fillMaxWidth())
}