package ch.ergon.dope

data class DopeQueryManager(
    val parameterManager: ParameterManager = ParameterManager(),
    val iteratorManager: IteratorManager = IteratorManager(),
)

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
