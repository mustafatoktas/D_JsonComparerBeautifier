import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import common.Constants
import presentation.main.MainScreen
import presentation.main.viewmodel.MainViewModel

fun main() {
    val mainViewModel = MainViewModel()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition(
                    alignment = Alignment.Center,
                ),
            ),
            title = Constants.appName,
        ) {
            MainScreen(mainViewModel)
        }
    }
}

