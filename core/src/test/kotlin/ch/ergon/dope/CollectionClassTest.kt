package ch.ergon.dope

import ch.ergon.dope.service.CollectionService
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionClassTest {

    private lateinit var collectionService: CollectionService

    @BeforeTest
    fun setup() {
        collectionService = CollectionService()
    }

    @Test
    fun `member should be in table1`() {
        val expected = true

        val actual: Boolean = collectionService.memberIsInCollection("fname", TestBucket.Person)

        assertEquals(expected, actual)
    }

    @Test
    fun `member should be in table2`() {
        val expected = true

        val actual: Boolean = collectionService.memberIsInCollection("age", TestBucket.Person)

        assertEquals(expected, actual)
    }

    @Test
    fun `member should not be in table`() {
        val expected = false

        val actual: Boolean = collectionService.memberIsInCollection("notintable", TestBucket.Person)

        assertEquals(expected, actual)
    }
}
