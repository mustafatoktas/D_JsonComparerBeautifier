@file:OptIn(DelicateCoroutinesApi::class)

package presentation.main.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode


class MainViewModel {

    private val _state = mutableStateOf(MainState())
    val state : State<MainState> = _state

    private val _uiEventFlow = MutableSharedFlow<MainUiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()


    fun onEvent (event : MainEvent) {
        when (event) {
            is MainEvent.YazilanJson1 -> {
                _state.value = state.value.copy(
                    json1 = event.deger
                )
            }
            is MainEvent.YazilanJson2 -> {
                _state.value = state.value.copy(
                    json2 = event.deger
                )
            }
            is MainEvent.onClickCompareButton -> {
                _state.value = state.value.copy(
                    result = compareJSON(state.value.json1,state.value.json2)
                )
            }

            is MainEvent.onClickBeautifyButton -> {
                GlobalScope.launch {
                    if (isJSON(state.value.json1)) {
                        _state.value = state.value.copy(
                            json2 = beautifyJSON(state.value.json1)
                        )
                    } else {
                        _uiEventFlow.emit(
                            MainUiEvent.SnackBarGoster(message = "❌ Format Error")
                        )
                        _state.value = state.value.copy(
                            json2 = ""
                        )
                    }
                }

            }

        }
    }

    private fun compareJSON(
        json1: String,
        json2: String
    ): String {
        return try {
            val comparison = JSONCompare.compareJSON(json1, json2, JSONCompareMode.STRICT)
            if (comparison.passed()) "✅ Same JSON" else "❗ Differences :\n ${comparison.message}"
        } catch (e: JSONException) {
            "❌ Format Error"
        }
    }

    private fun beautifyJSON(jsonString: String): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = JsonParser.parseString(jsonString)
        return gson.toJson(jsonElement)
    }

    private fun isJSON(input: String): Boolean {
        return try {
            val json = JsonParser().parse(input)
            json is JsonObject || json is JsonArray
        } catch (e: Exception) {
            false
        }
    }

}