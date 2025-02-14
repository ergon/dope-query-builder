package ch.ergon.dope.resolvable

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager

const val ASTERISK_STRING = "*"

class Asterisk(private val bucket: Bucket? = null) : Selectable, Returnable {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val queryString = bucket?.toDopeQuery(manager)?.queryString?.let { "$it.$ASTERISK_STRING" } ?: ASTERISK_STRING
        return DopeQuery(queryString = queryString)
    }
}

@JvmName("asteriskNullableBucketExtension")
fun asterisk(bucket: Bucket? = null) = Asterisk(bucket)
