package si.matijav.kamino.data.source

class DefaultMemoryCache<T>: MemoryCache<T> {

    private val cache = LinkedHashMap<Int, T>(5)

    override fun get(id: Int): T? {
        return cache.get(id)
    }

    override fun add(value: T, id: Int) {
        cache.put(id, value)
    }
}