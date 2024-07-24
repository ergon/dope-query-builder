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
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isArray
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isAtom
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isBoolean
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isNumber
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.isString
import ch.ergon.dope.resolvable.expression.unaliased.type.typefunction.toArray
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
                isArray(someNumberArrayField()),
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
                isAtom(someNumberField()),
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
                isBoolean(someBooleanField()),
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
                isNumber(someNumberField()),
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
                isString(someStringField()),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TOARRAY from atom to array`() {
        val expected = "SELECT (TOARRAY(1)[0] + 1)"

        val actual = create
            .select(
                toArray(someNumber(1).toDopeType()).get(0).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TOARRAY from array to array`() {
        val expected = "SELECT (TOARRAY([1, 2, 3])[0] + 1)"

        val actual = create
            .select(
                toArray(
                    listOf(
                        1.toDopeType(),
                        2.toDopeType(),
                        3.toDopeType(),
                    ).toDopeType(),
                ).get(0).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support TOARRAY with nested array`() {
        val expected = "SELECT (TOARRAY([[1], [2], [3]])[0][0] + 1)"

        val actual = create
            .select(
                toArray(
                    listOf(
                        listOf(1.toDopeType()).toDopeType(),
                        listOf(2.toDopeType()).toDopeType(),
                        listOf(3.toDopeType()).toDopeType(),
                    ).toDopeType(),
                ).get(0).get(0).add(1),
            ).build().queryString

        assertEquals(expected, actual)
    }
}
