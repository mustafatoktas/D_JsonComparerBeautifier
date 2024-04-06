package presentation.main.viewmodel

sealed class MainEvent {
    data class YazilanJson1 (val deger : String) : MainEvent()
    data class YazilanJson2 (val deger: String) : MainEvent()
    object onClickCompareButton : MainEvent()
    object onClickBeautifyButton : MainEvent()
}