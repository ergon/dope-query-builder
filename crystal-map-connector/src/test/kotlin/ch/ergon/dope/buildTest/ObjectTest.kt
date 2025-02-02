package ch.ergon.dope.buildTest

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.extension.select
import ch.ergon.dope.extension.type.access.get
import ch.ergon.dope.extension.type.getField
import ch.ergon.dope.extension.type.relational.isEqualTo
import ch.ergon.dope.extension.type.relational.isGreaterOrEqualThan
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBucket
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.case
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.condition
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.resultsIn
import ch.ergon.dope.resolvable.expression.unaliased.type.getString
import ch.ergon.dope.toDopeType
import com.schwarz.crystalapi.schema.CMJsonField
import com.schwarz.crystalapi.schema.CMObjectField
import com.schwarz.crystalapi.schema.CMObjectList
import com.schwarz.crystalapi.schema.Schema
import kotlin.test.Test
import kotlin.test.assertEquals

class ObjectTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    class Person(path: String = "") : Schema {
        val hobbies: CMObjectList<Hobby> = CMObjectList(Hobby(path), "hobbies", path)
        val primaryHobby: CMObjectField<Hobby> = CMObjectField(Hobby(path), "primaryHobby", path)
    }

    class Hobby(path: String = "") : Schema {
        val name: CMJsonField<String> = CMJsonField("name", path)
        val preferredDestination: CMObjectField<Destination> = CMObjectField(Destination(path), "preferredDestination", path)
    }

    class Destination(path: String = "") : Schema {
        val groupSize: CMJsonField<Number> = CMJsonField("groupSize", path)
    }

    @Test
    fun `should support selecting object`() {
        val bucket = someBucket().alias("p")
        val schema = Person("p")
        val expected = "SELECT `p`.`hobbies` FROM `someBucket` AS `p`"

        val actual = QueryBuilder()
            .select(schema.hobbies)
            .from(bucket)
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting object from list`() {
        val bucket = someBucket().alias("p")
        val schema = Person("p")
        val expected = "SELECT * FROM `someBucket` AS `p` WHERE `p`.`hobbies`[0] = `p`.`primaryHobby`"

        val actual = QueryBuilder()
            .selectAsterisk()
            .from(bucket)
            .where(schema.hobbies.get(0).isEqualTo(schema.primaryHobby))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting nested object with number`() {
        val bucket = someBucket().alias("p")
        val schema = Person("p")
        val expected = "SELECT * FROM `someBucket` AS `p` WHERE `p`.`primaryHobby`.`preferredDestination`.`groupSize` >= 5"

        val actual = QueryBuilder()
            .selectAsterisk()
            .from(bucket)
            .where(schema.primaryHobby.getField(Hobby::preferredDestination).getField(Destination::groupSize).isGreaterOrEqualThan(5))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting object from list with string`() {
        val bucket = someBucket().alias("p")
        val schema = Person("p")
        val expected = "SELECT * FROM `someBucket` AS `p` WHERE `p`.`hobbies`[0].`name` = `p`.`primaryHobby`.`name`"

        val actual = QueryBuilder()
            .selectAsterisk()
            .from(bucket)
            .where(schema.hobbies.get(0).getString("name").isEqualTo(schema.primaryHobby.getField(Hobby::name)))
            .build().queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should support selecting object Field`() {
        val bucket = someBucket().alias("p")
        val schema = Person("p")
        val objectField = schema.primaryHobby.toDopeType()
        val expected = "SELECT `p`.`primaryHobby`, CASE `p`.`primaryHobby` WHEN `p`.`hobbies`[0] THEN \"isPrimary\" END " +
            "FROM `someBucket` AS `p` " +
            "WHERE `p`.`primaryHobby`.`name` = \"Football\""

        val actual = QueryBuilder()
            .select(objectField, case(objectField).condition(schema.hobbies.get(0).resultsIn("isPrimary")))
            .from(bucket)
            .where(objectField.getField(Hobby::name).isEqualTo("Football"))
            .build().queryString

        assertEquals(expected, actual)
    }
}
