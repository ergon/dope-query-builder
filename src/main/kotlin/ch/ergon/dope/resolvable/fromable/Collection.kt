package ch.ergon.dope.resolvable.fromable

open class Collection(override val name: String) : UnaliasedBucket(name) {
    override fun toQueryString(): String = name
}
