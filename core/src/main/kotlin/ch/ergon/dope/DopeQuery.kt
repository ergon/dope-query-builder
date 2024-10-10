package ch.ergon.dope

data class DopeQuery(
    val queryString: String,
    val parameters: DopeParameters = DopeParameters(),
)

data class DopeParameters(
    val namedParameters: Map<String, Any> = emptyMap(),
    val positionalParameters: List<Any> = emptyList(),
) {
    fun merge(vararg otherParameters: DopeParameters?) = this.copy(
        namedParameters = otherParameters.filterNotNull().fold(this.namedParameters) {
                namedParams, additionalNamedParams ->
            namedParams + additionalNamedParams.namedParameters
        },
        positionalParameters = otherParameters.filterNotNull().fold(this.positionalParameters) {
                positionalParams, additionalPositionalParams ->
            positionalParams + additionalPositionalParams.positionalParameters
        },
    )
}

fun List<DopeParameters>.merge(vararg otherParameters: DopeParameters): DopeParameters {
    return this.fold(DopeParameters()) {
            parameters, additionalParameters ->
        parameters.merge(additionalParameters)
    }.merge(*otherParameters)
}
