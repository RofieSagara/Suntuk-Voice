package com.indev.suntuk.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.indev.suntuk.R

@Composable
fun CommentContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
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
        ActionsComment(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun ActionsComment(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "1.2K Likes - 12 Replies", style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.weight(1f, true))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ActionsReply(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "1.2K Likes", style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.weight(1f, true))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        }
    }
}

@Composable
private fun ReplyContent(modifier: Modifier = Modifier) {
    Row(modifier = modifier.height(IntrinsicSize.Max)) {
        Box(
            modifier = Modifier
                .width(21.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.primary)
            )
        }
        Column(modifier = Modifier) {
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
            ActionsReply(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentContentPreview() {
    CommentContent(modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun ReplyContentPreview() {
    ReplyContent(modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun PreviewItems() {
    LazyColumn {
        item {
            CommentContent(modifier = Modifier.padding(horizontal = 16.dp))
        }
        (1..5).forEach { _ ->
            item {
                ReplyContent(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}