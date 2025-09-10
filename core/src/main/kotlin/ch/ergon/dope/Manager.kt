package ch.ergon.dope

import ch.ergon.dope.build.QueryResolver

data class DopeQueryManager<T : DopeQuery>(
    val resolver: QueryResolver<T>,
) {
    val parameterManager = ParameterManager()
    val iteratorManager = IteratorManager()
}

class ParameterManager {
    var count: Int = 1
        get() = field++
        private set
}

class IteratorManager {
    private var count: Int = 1
        get() = field++

    fun getIteratorName() = "iterator$count"
}
