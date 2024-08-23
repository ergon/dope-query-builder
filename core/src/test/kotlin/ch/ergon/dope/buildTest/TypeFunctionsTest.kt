package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.helper.ParameterDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.helper.someNumber
import ch.ergon.dope.helper.someNumberArrayField
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.unaliased.type.access.get
import ch.ergon.dope.resolvable.expression.unaliased.type.arithmetic.add
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isArray
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isAtom
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isBoolean
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.isString
import ch.ergon.dope.resolvable.expression.unaliased.type.function.typefunction.toArray
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeFunctionsTest : ParameterDependentTest {
    private lateinit var create: QueryBuilder

    @BeforeTest
    fun setup() {
        create = QueryBuilder()
    }

    @Test
    fun `should support ISARRAY as boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ISARRAY(`numberArrayField`)"

        val actual = create
            .selectFrom(
                someBucket(),
            )
            .where(
                someNumberArrayField().isArray(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ISATOM as boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ISATOM(`numberField`)"

        val actual = create
            .selectFrom(
                someBucket(),
            )
            .where(
                someNumberField().isAtom(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ISBOOLEAN as boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ISBOOLEAN(`booleanField`)"

        val actual = create
            .selectFrom(
                someBucket(),
            )
            .where(
                someBooleanField().isBoolean(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ISNUMBER as boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ISNUMBER(`numberField`)"

        val actual = create
            .selectFrom(
                someBucket(),
            )
            .where(
                someNumberField().isNumber(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support ISSTRING as boolean comparison`() {
        val expected = "SELECT * FROM `someBucket` WHERE ISSTRING(`stringField`)"

        val actual = create
            .selectFrom(
                someBucket(),
            )
            .where(
                someStringField().isString(),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TOARRAY from atom to array`() {
        val expected = "SELECT (TOARRAY(1)[0] + 1)"

        val actual = create
            .select(
                someNumber(1).toDopeType().toArray().get(0).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
