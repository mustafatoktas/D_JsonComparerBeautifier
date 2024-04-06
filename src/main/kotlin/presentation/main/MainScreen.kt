package presentation.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import presentation.main.viewmodel.MainEvent
import presentation.main.viewmodel.MainUiEvent
import presentation.main.viewmodel.MainViewModel

@Composable
@Preview
fun MainScreen(
    viewModel : MainViewModel
) {

    val state = viewModel.state.value
    val padding = 16.dp
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collectLatest { event ->
            when(event) {
                is MainUiEvent.SnackBarGoster -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }


    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ){

        }
        Column(
            modifier = Modifier
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(padding),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                OutlinedTextField(
                    value = state.json1,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.YazilanJson1(it))
                    },
                    label = { Text("JSON 1") },
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .fillMaxHeight(0.75F),
                )
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                OutlinedTextField(
                    value = state.json2,
                    onValueChange = {
                        viewModel.onEvent(MainEvent.YazilanJson2(it))
                    },
                    label = { Text("JSON 2") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.75F)
                )
            }
            Row (
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier
                        .padding(end = 14.dp),
                    onClick = {
                        viewModel.onEvent(MainEvent.onClickCompareButton)
                    },
                ) {
                    Text("Compare")
                }
                Button(
                    onClick = {
                         viewModel.onEvent(MainEvent.onClickBeautifyButton)
                    },
                ) {
                    Text("Beautify")
                }
            }
            if (state.result.isNotBlank())
                OutlinedTextField(
                    value = state.result,
                    readOnly = true,
                    onValueChange = {},
                    label = {
                        Text("Result")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
        }
    }
}

