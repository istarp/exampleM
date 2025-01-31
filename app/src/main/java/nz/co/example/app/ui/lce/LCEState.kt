package nz.co.example.app.ui.lce

sealed interface LCEState<out T> {
    class Loading<T> : LCEState<T>

    data class Content<T>(val value: T) : LCEState<T>

    data class Error<T>(val error: String) : LCEState<T>
}