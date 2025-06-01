package presentation.main.viewmodel

sealed class MainUiEvent {
    data class SnackBarGoster (val message : String) : MainUiEvent()
}