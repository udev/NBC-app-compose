package com.nbc.uitest.feature

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nbc.uitest.R
import com.nbc.uitest.feature.home.HomeScreenViewmodel
import com.nbc.uitest.feature.home.UiState
import com.nbc.uitest.model.NbcShowType
import com.nbc.uitest.model.Shelf
import com.nbc.uitest.model.Show
import com.nbc.uitest.ui.theme.GradientBottom
import com.nbc.uitest.ui.theme.GradientTop
import com.nbc.uitest.ui.theme.NbcandroiduiinterviewTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NbcandroiduiinterviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun HomePage(
    viewmodel: HomeScreenViewmodel = viewModel()
) {
    val activity = LocalContext.current as Activity
    val state = viewmodel.getShows(activity, R.raw.homepage_more_shelves)
        .collectAsState(initial = UiState.Loading)
    var showError by remember { mutableStateOf(false) }

    Box {
        HomeBackground()
        Shelves(state) { showError = true }
        HomeBottomNavigation()
    }

    if (showError) {
        Dialog(onDismissRequest = {
            showError = false
            viewmodel.getShows(activity, R.raw.homepage_more_shelves)
        }) {
            Column(
                modifier = Modifier.wrapContentHeight()
            ) {
                Text(stringResource(R.string.uh_oh))
                Text(stringResource(R.string.alert_dialog_error_generic))
                Button(onClick = { showError = false }) {
                    Text(stringResource(R.string.ok))
                }
            }
        }
    }
}

@Composable
fun HomeBottomNavigation() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .background(color = GradientBottom)
        ) {
            IconButton(onClick = { /* doSomething() */ }) {
                Column {
                    Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.content_description_bottom_nav_home))
                    Text(text = stringResource(R.string.home))
                }
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Column {
                    Icon(Icons.Filled.List, contentDescription = stringResource(R.string.content_description_bottom_nav_list))
                    Text(text = stringResource(R.string.shows))
                }
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Column {
                    Icon(
                        Icons.Filled.Info,
                        contentDescription = stringResource(R.string.content_description_bottom_nav_live)
                    )
                    Text(text = stringResource(R.string.live))
                }
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Column {
                    Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.content_description_bottom_nav_search))
                    Text(text = stringResource(R.string.search))
                }
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Column {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.content_description_bottom_nav_more)
                    )
                    Text(text = stringResource(R.string.more))
                }
            }
        }
    }
}

@Composable
fun Shelves(
    state: State<UiState>,
    onError: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .semantics { testTag = "shelfList" }
    ) {
        when (val s = state.value) {
            is UiState.Loading -> {
                LinearProgressIndicator()
            }

            is UiState.Success -> {
                s.page.shelves.forEach {
                    Shelf(shelf = it)
                }
                Spacer(modifier = Modifier.height(150.dp))
            }

            is UiState.Error -> onError()
        }
    }
}

@Composable
fun HomeBackground() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GradientTop,
                        GradientBottom
                    )
                )
            )
    ) {
    }
}

@Composable
fun Shelf(
    shelf: Shelf,
) {
    Spacer(modifier = Modifier.height(40.dp))
    Column(
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Text(
            text = shelf.title,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            shelf.items.forEach {
                ShelfItem(it)
            }
        }
    }
}

@Composable
fun ShelfItem(show: Show) {
    // TODO: see com.nbc.uitest.model.Show
    // converter needed to make this clean
    val itemContainerHeight by derivedStateOf {
        when (show.type) {
            NbcShowType.Live.type,
            NbcShowType.Episode.type -> 83.dp
            else -> 200.dp
        }
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .requiredWidth(150.dp)
            .wrapContentHeight()
            .clickable { /* TODO: perform click action */ }
            .semantics { testTag = "shelListItem" }
    ) {
        Box {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(show.image)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.sample_cover),
                    contentDescription = stringResource(R.string.thumbnail_image_for_show, show.title),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clip(RectangleShape)
                        .fillMaxWidth()
                        .height(itemContainerHeight)
                )
                if (show.type == NbcShowType.Live.type) {
                    LinearProgressIndicator(
                        progress = Random.nextDouble(0.3, 0.8).toFloat(),
                        color = Red
                    )
                }
            }
            show.labelBadge?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Black,
                    modifier = Modifier
                        .background(White)
                        .padding(4.dp)
                )
            }
            if (show.type == NbcShowType.Live.type) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .height(itemContainerHeight)
                        .padding(start = 4.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.badge_live),
                        color = White,
                        modifier = Modifier
                            .background(
                                color = Red,
                                shape = RoundedCornerShape(5.dp)
                            )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = SpaceBetween,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Text(
                text = show.title,
                softWrap = true,
                maxLines = 2,
                modifier = Modifier.weight(3f)
            )
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = stringResource(R.string.content_description_nbc_logo),
                modifier = Modifier
                    .size(20.dp)
                    .weight(1f)
            )
        }
        show.subtitle?.let { subtitle ->
            Row {
                Text(subtitle)
            }
        }
    }
    Spacer(modifier = Modifier.width(16.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NbcandroiduiinterviewTheme {
        HomePage()
    }
}