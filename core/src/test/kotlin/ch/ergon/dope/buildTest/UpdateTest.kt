package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.meta.meta
import ch.ergon.dope.resolvable.expression.unaliased.type.relational.isEqualTo
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateTest : ParameterDependentTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support simple update clause`() {
        val expected = "UPDATE `someBucket`"

        val actual = create
            .update(
                someBucket(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with use keys`() {
        val expected = "UPDATE `someBucket` USE KEYS \"someString\""

        val actual = create
            .update(
                someBucket(),
            ).useKeys(
                someString().toDopeType(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with set`() {
        val expected = "UPDATE `someBucket` SET META().`expiration` = 10, `stringField` = \"test\""

        val actual = create
            .update(
                someBucket(),
            ).set(
                meta().expiration,
                10.toDopeType(),
            )
            .set(
                someStringField(),
                "test".toDopeType(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with unset`() {
        val expected = "UPDATE `someBucket` UNSET `stringField`"

        val actual = create
            .update(
                someBucket(),
            ).unset(
                someStringField(),
            )
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with where`() {
        val expected = "UPDATE `someBucket` WHERE (1 + 2) = 3"

        val actual = create
            .update(
                someBucket(),
            ).where(
                1.toDopeType().add(2).isEqualTo(3),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with limit`() {
        val expected = "UPDATE `someBucket` LIMIT 1"

        val actual = create
            .update(
                someBucket(),
            ).limit(
                1.toDopeType(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause with returning`() {
        val expected = "UPDATE `someBucket` RETURNING `stringField`"

        val actual = create
            .update(
                someBucket(),
            ).returning(
                someStringField(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update clause as a complex query`() {
        val expected = "UPDATE `someBucket` AS `sb` " +
            "USE KEYS \"keyString\" " +
            "SET `setThisNumberField` = 1, META(`sb`).`expiration` = 3600 " +
            "UNSET `unsetThisStringField` " +
            "WHERE `booleanField` = FALSE " +
            "LIMIT 1 " +
            "RETURNING `setThisNumberField`"

        val actual = create
            .update(
                someBucket().alias("sb"),
            ).useKeys(
                "keyString".toDopeType(),
            ).set(
                someNumberField("setThisNumberField"),
                1.toDopeType(),
            ).set(
                meta(someBucket().alias("sb")).expiration,
                3600.toDopeType(),
            ).unset(
                someStringField("unsetThisStringField"),
            ).where(
                someBooleanField().isEqualTo(false),
            ).limit(
                1.toDopeType(),
            ).returning(
                someNumberField("setThisNumberField"),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
