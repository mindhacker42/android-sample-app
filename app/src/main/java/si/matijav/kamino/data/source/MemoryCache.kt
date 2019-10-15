package si.matijav.kamino.data.source

/**
 * TODO: add a header comment
 */
interface MemoryCache<T> {

    fun add(value: T, id: Int)

    fun get(id: Int): T?
}