package ch.ergon.dope

class DopeQueryManager {
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
