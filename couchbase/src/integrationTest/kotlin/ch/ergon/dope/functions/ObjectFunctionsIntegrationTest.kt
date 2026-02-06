package ch.ergon.dope.functions

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.expression.type.meta
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import ch.ergon.dope.integrationTest.BUCKET
import ch.ergon.dope.integrationTest.BaseIntegrationTest
import ch.ergon.dope.integrationTest.TestCouchbaseDatabase.detailsField
import ch.ergon.dope.integrationTest.toMapValues
import ch.ergon.dope.resolvable.bucket.CollectionBucket
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.TRUE
import ch.ergon.dope.resolvable.expression.type.alias
import ch.ergon.dope.resolvable.expression.type.collection.inArray
import ch.ergon.dope.resolvable.expression.type.function.objects.addAttribute
import ch.ergon.dope.resolvable.expression.type.function.objects.concat
import ch.ergon.dope.resolvable.expression.type.function.objects.getInnerPairs
import ch.ergon.dope.resolvable.expression.type.function.objects.getLength
import ch.ergon.dope.resolvable.expression.type.function.objects.removeAttribute
import ch.ergon.dope.resolvable.expression.type.logic.and
import ch.ergon.dope.resolvable.expression.type.relational.isGreaterThan
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ObjectType
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectFunctionsIntegrationTest : BaseIntegrationTest() {
    @Test
    fun `use nested object functions`() {
        val testBucket = CollectionBucket(BUCKET, "_default", "_default")
        val detailsField = Field<ObjectType>(detailsField.name, testBucket)
        val dopeQuery = QueryBuilder
            .selectRaw(
                detailsField.concat(mapOf("someField" to 4).toDopeType()).addAttribute("otherField", TRUE).removeAttribute("department")
                    .alias("result"),
            )
            .from(testBucket)
            .where(
                detailsField.getLength().isGreaterThan(2)
                    .and(mapOf("name" to "email", "val" to "employee1@company.com").toDopeType().inArray(detailsField.getInnerPairs())),
            ).orderBy(
                meta().id,
            )
            .build(CouchbaseResolver())

        val queryResult = queryWithoutParameters(dopeQuery)

        assertEquals(
            mapOf(
                "position" to "Engineer",
                "email" to "employee1@company.com",
                "someField" to 4,
                "otherField" to true,
            ),
            queryResult.toMapValues(),
        )
    }
}
