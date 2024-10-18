package ch.ergon.dope

data class DopeQuery(
    val queryString: String,
    val parameters: DopeParameters = DopeParameters(),
)
