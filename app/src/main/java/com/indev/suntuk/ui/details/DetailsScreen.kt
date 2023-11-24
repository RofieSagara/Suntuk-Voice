package com.indev.suntuk.ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indev.suntuk.R
import com.indev.suntuk.ui.component.CommentContent
import com.indev.suntuk.ui.component.ItemThread
import com.indev.suntuk.ui.component.ReplyContent
import com.indev.suntuk.ui.component.ReplyLoadMore

@Composable
fun DetailsScreen() {

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun DetailsScreenContent(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Threads")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                         Icon(
                             imageVector = Icons.Default.ArrowBack,
                             contentDescription = null,
                         )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                ) {
                    ItemThread(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        isHeader = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            item {
                CommentContent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 16.dp)
                )
            }
            items(5) {
                ReplyContent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 16.dp)
                )
            }
            item {
                ReplyLoadMore(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 16.dp)
                )
            }
            item {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Load more comments")
                }
            }
            stickyHeader {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                ) {
                    ItemThread(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            item {
                CommentContent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 16.dp)
                )
            }
            items(5) {
                ReplyContent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 16.dp)
                )
            }
            item {
                ReplyLoadMore(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(start = 16.dp)
                )
            }
            item {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Load more comments")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL)
@Composable
private fun DetailsScreenContentPreview() {
    DetailsScreenContent(modifier = Modifier.fillMaxWidth())
}