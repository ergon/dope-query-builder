package ch.ergon.dope.resolvable.clause

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someDeleteClause
import ch.ergon.dope.helper.someSelectClause
import ch.ergon.dope.helper.someString
import ch.ergon.dope.helper.someUpdateClause
import ch.ergon.dope.resolvable.clause.model.DeleteUseKeys.Companion.DeleteUseKeysClause
import ch.ergon.dope.resolvable.clause.model.SelectUseKeys.Companion.SelectUseKeysClause
import ch.ergon.dope.resolvable.clause.model.UpdateUseKeys.Companion.UpdateUseKeysClause
import ch.ergon.dope.resolvable.expression.unaliased.type.asParameter
import ch.ergon.dope.resolvable.expression.unaliased.type.toDopeType
import kotlin.test.Test
import kotlin.test.assertEquals

class UseKeysClauseTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should support select single use keys`() {
        val expected = DopeQuery(
            "SELECT * USE KEYS \"someString\"",
            emptyMap(),
            manager,
        )
        val underTest = SelectUseKeysClause(
            "someString".toDopeType(),
            someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select array use keys`() {
        val expected = DopeQuery(
            "SELECT * USE KEYS [\"someString\", \"anotherString\"]",
            emptyMap(),
            manager,
        )
        val underTest = SelectUseKeysClause(
            listOf("someString".toDopeType(), "anotherString".toDopeType()).toDopeType(),
            someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use keys with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "SELECT * USE KEYS $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = SelectUseKeysClause(
            parameterValue.asParameter(),
            someSelectClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support select use keys extension`() {
        val useKeysString = someString().toDopeType()
        val parentClause = someSelectClause()
        val expected = SelectUseKeysClause(useKeysString, parentClause = parentClause)

        val actual = parentClause.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support delete single use keys`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` USE KEYS \"someString\"",
            emptyMap(),
            manager,
        )
        val underTest = DeleteUseKeysClause(
            "someString".toDopeType(),
            someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete array use keys`() {
        val expected = DopeQuery(
            "DELETE FROM `someBucket` USE KEYS [\"someString\", \"anotherString\"]",
            emptyMap(),
            manager,
        )
        val underTest = DeleteUseKeysClause(
            listOf("someString".toDopeType(), "anotherString".toDopeType()).toDopeType(),
            someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "DELETE FROM `someBucket` USE KEYS $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = DeleteUseKeysClause(
            parameterValue.asParameter(),
            someDeleteClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support delete use keys extension`() {
        val useKeysString = someString().toDopeType()
        val parentClause = someDeleteClause()
        val expected = DeleteUseKeysClause(useKeysString, parentClause = parentClause)

        val actual = parentClause.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should support update single use keys`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` USE KEYS \"someString\"",
            emptyMap(),
            manager,
        )
        val underTest = UpdateUseKeysClause(
            "someString".toDopeType(),
            someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update array use keys`() {
        val expected = DopeQuery(
            "UPDATE `someBucket` USE KEYS [\"someString\", \"anotherString\"]",
            emptyMap(),
            manager,
        )
        val underTest = UpdateUseKeysClause(
            listOf("someString".toDopeType(), "anotherString".toDopeType()).toDopeType(),
            someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys with parameter`() {
        val parameterValue = someString()
        val expected = DopeQuery(
            "UPDATE `someBucket` USE KEYS $1",
            mapOf("$1" to parameterValue),
            manager,
        )
        val underTest = UpdateUseKeysClause(
            parameterValue.asParameter(),
            someUpdateClause(),
        )

        val actual = underTest.toDopeQuery(manager)

        assertEquals(expected, actual)
    }

    @Test
    fun `should support update use keys extension`() {
        val useKeysString = someString().toDopeType()
        val parentClause = someUpdateClause()
        val expected = UpdateUseKeysClause(useKeysString, parentClause = parentClause)

        val actual = parentClause.useKeys(useKeysString)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
