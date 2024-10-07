package com.example.devicesmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.devicesmanager.composables.DMTextButtonBorderless
import com.example.devicesmanager.helpers.PreviewWrapper
import com.example.devicesmanager.models.Destination
import com.example.devicesmanager.screens.ApparatusesScreen
import com.example.devicesmanager.screens.DevicesScreen
import com.example.devicesmanager.screens.HomeScreen
import com.example.devicesmanager.screens.MessagesScreen
import com.example.devicesmanager.screens.SettingsScreen
import com.example.devicesmanager.screens.StatisticsScreen
import com.example.devicesmanager.ui.theme.DMTheme
import com.example.devicesmanager.ui.theme.DevicesManagerTheme
import com.example.devicesmanager.viewmodels.ActivityViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            DevicesManagerTheme {
                ActivityScreen()
            }
        }
    }
}

@Composable
fun ActivityScreen(viewModel: ActivityViewModel = koinViewModel<ActivityViewModel>()) {
    val state by viewModel.collectAsState()

    ActivityContent(
        currentScreenId = state.currentScreenId,
        onScreenIdChange = { viewModel.changeCurrentScreen(it) },
        destinationList = viewModel.destinations
    )
}

@Composable
private fun ActivityContent(
    currentScreenId: Int,
    onScreenIdChange: (Int) -> Unit,
    destinationList: List<Destination>
) {
    val destNames = stringArrayResource(R.array.screen_dest_names).toList()
    val screenTitles = stringArrayResource(R.array.screen_titles).toList()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(screenTitles[currentScreenId]) },
        bottomBar = {
            BottomBar(
                destinationsNames = destNames,
                currentDestinationId = currentScreenId,
                itemOnClick = { onScreenIdChange(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            HorizontalDivider()
            Box(
                modifier = Modifier.weight(1f)
            ) {
                when (destinationList[currentScreenId]) {
                    Destination.Apparatuses -> ApparatusesScreen()
                    Destination.Devices -> DevicesScreen()
                    Destination.Home -> HomeScreen()
                    Destination.Messages -> MessagesScreen()
                    Destination.Setting -> SettingsScreen()
                    Destination.Statistics -> StatisticsScreen()
                }
            }
            HorizontalDivider()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String = ""
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = DMTheme.typography.headlineLarge
            )
        },
        navigationIcon = { },
        actions = { },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DMTheme.colorScheme.surfaceContainerLow
        ),
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

@Composable
private fun BottomBar(
    destinationsNames: List<String>,
    currentDestinationId: Int,
    itemOnClick: (Int) -> Unit = {}
) {
    LazyRow {
        destinationsNames.forEachIndexed { index, name ->
            item {
                DMTextButtonBorderless(
                    text = name,
                    selected = index == currentDestinationId,
                    onClick = { itemOnClick(index) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewWrapper {
        ActivityScreen()
    }
}