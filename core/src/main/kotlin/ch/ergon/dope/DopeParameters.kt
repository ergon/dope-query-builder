package ch.ergon.dope

/**
 * DopeParameters contain the two different parameter types for DopeQueries.
 * It is assumed that [positionalParameters] are sorted, provided that
 * [merge] is always called upon the first member in the sequence of the query itself.
 *
 * @property namedParameters A map containing the name of parameters (key) and their respective values (value)
 * @property positionalParameters A sorted list containing all values of positional (unnamed) parameters
 */
data class DopeParameters(
    val namedParameters: Map<String, Any> = emptyMap(),
    val positionalParameters: List<Any> = emptyList(),
) {
    /**
     * Merges multiple instances of DopeParameters, so that the receiver instance of [DopeParameters]
     * is the first in the order of merging.
     */
    fun merge(vararg otherParameters: DopeParameters?) = this.copy(
        namedParameters = otherParameters.filterNotNull()
            .fold(this.namedParameters) { namedParams, additionalNamedParams ->
                namedParams + additionalNamedParams.namedParameters
            },
        positionalParameters = otherParameters.filterNotNull()
            .fold(this.positionalParameters) { positionalParams, additionalPositionalParams ->
                positionalParams + additionalPositionalParams.positionalParameters
            },
    )
}

fun List<DopeParameters>.merge(vararg otherParameters: DopeParameters?): DopeParameters =
    this.fold(DopeParameters()) { parameters, additionalParameters ->
        parameters.merge(additionalParameters)
    }.merge(*otherParameters)

fun DopeParameters?.orEmpty(): DopeParameters = this ?: DopeParameters()
