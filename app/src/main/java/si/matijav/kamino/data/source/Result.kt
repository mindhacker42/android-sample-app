package si.matijav.kamino.data.source

/**
 * A generic class that holds a value with its loading status.
 * Taken from https://github.com/android/architecture-samples/blob/dagger-android/app/src/main/java/com/example/android/architecture/blueprints/todoapp/data/Result.kt
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class SuccessNoData(val data: Nothing? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
            is SuccessNoData -> "Success no data"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded: Boolean
    get() = this is Result.Success && data != null