package ch.ergon.dope.extensions.type.conditional

import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.extension.type.conditional.case
import ch.ergon.dope.extension.type.conditional.otherwise
import ch.ergon.dope.helper.ManagerDependentTest
import ch.ergon.dope.helper.someBooleanField
import ch.ergon.dope.helper.someCMBooleanField
import ch.ergon.dope.helper.someCMBooleanList
import ch.ergon.dope.helper.someCMNumberField
import ch.ergon.dope.helper.someCMNumberList
import ch.ergon.dope.helper.someCMStringField
import ch.ergon.dope.helper.someCMStringList
import ch.ergon.dope.helper.someCaseClass
import ch.ergon.dope.helper.someNumberField
import ch.ergon.dope.helper.someStringField
import ch.ergon.dope.resolvable.expression.TypeExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CaseClass
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.CaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.conditional.ElseCaseExpression
import ch.ergon.dope.resolvable.expression.unaliased.type.function.conditional.SearchResult
import ch.ergon.dope.toDopeType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType
import ch.ergon.dope.validtype.ValidType
import kotlin.test.Test
import kotlin.test.assertEquals

class CaseExpressionTest : ManagerDependentTest {
    override lateinit var manager: DopeQueryManager

    @Test
    fun `should create CaseClass from CMNumberField`() {
        val expression = someCMNumberField()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should create CaseClass from CMStringField`() {
        val expression = someCMStringField()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should create CaseClass from CMBooleanField`() {
        val expression = someCMBooleanField()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should create CaseClass from CMJsonList of Number`() {
        val expression = someCMNumberList()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should create CaseClass from CMJsonList of String`() {
        val expression = someCMStringList()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should create CaseClass from CMJsonList of Boolean`() {
        val expression = someCMBooleanList()
        val expected = CaseClass(expression.toDopeType())

        val actual = case(expression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMNumber to SimpleCaseExpression with generic`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = CaseExpression(case, SearchResult(someNumberField(), someNumberField()))
        val elseExpression = someCMNumberField()
        val expected = ElseCaseExpression(
            case,
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<NumberType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMNumber to SimpleCaseExpression without generic`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = CaseExpression(case, SearchResult(someNumberField(), someStringField()))
        val elseExpression = someCMNumberField()
        val expected = ElseCaseExpression(
            case,
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<ValidType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMString to SimpleCaseExpression with generic`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = CaseExpression(case, SearchResult(someNumberField(), someStringField()))
        val elseExpression = someCMStringField()
        val expected = ElseCaseExpression(
            case,
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<StringType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMString to SimpleCaseExpression without generic`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = CaseExpression(case, SearchResult(someNumberField(), someBooleanField()))
        val elseExpression = someCMStringField()
        val expected = ElseCaseExpression(
            case,
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<ValidType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMBoolean to SimpleCaseExpression with generic`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = CaseExpression(case, SearchResult(someNumberField(), someBooleanField()))
        val elseExpression = someCMBooleanField()
        val expected = ElseCaseExpression(
            case,
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<BooleanType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMBoolean to SimpleCaseExpression without generic`() {
        val case = someCaseClass(someNumberField())
        val initialExpression = CaseExpression(case, SearchResult(someNumberField(), someNumberField()))
        val elseExpression = someCMBooleanField()
        val expected = ElseCaseExpression(
            case,
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<ValidType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMNumber to SearchedCaseExpression with generic`() {
        val initialExpression = CaseExpression(CaseClass(), SearchResult(someBooleanField(), someNumberField()))
        val elseExpression = someCMNumberField()
        val expected = ElseCaseExpression(
            CaseClass(),
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<NumberType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMNumber to SearchedCaseExpression without generic`() {
        val initialExpression = CaseExpression(CaseClass(), SearchResult(someBooleanField(), someStringField()))
        val elseExpression = someCMNumberField()
        val expected = ElseCaseExpression(
            CaseClass(),
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<ValidType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMString to SearchedCaseExpression with generic`() {
        val initialExpression = CaseExpression(CaseClass(), SearchResult(someBooleanField(), someStringField()))
        val elseExpression = someCMStringField()
        val expected = ElseCaseExpression(
            CaseClass(),
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<StringType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMString to SearchedCaseExpression without generic`() {
        val initialExpression = CaseExpression(CaseClass(), SearchResult(someBooleanField(), someBooleanField()))
        val elseExpression = someCMStringField()
        val expected = ElseCaseExpression(
            CaseClass(),
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<ValidType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMBoolean to SearchedCaseExpression with generic`() {
        val initialExpression = CaseExpression(CaseClass(), SearchResult(someBooleanField(), someBooleanField()))
        val elseExpression = someCMBooleanField()
        val expected = ElseCaseExpression(
            CaseClass(),
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<BooleanType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }

    @Test
    fun `should add else condition with CMBoolean to SearchedCaseExpression without generic`() {
        val initialExpression = CaseExpression(CaseClass(), SearchResult(someBooleanField(), someNumberField()))
        val elseExpression = someCMBooleanField()
        val expected = ElseCaseExpression(
            CaseClass(),
            initialExpression.firstSearchResult,
            *initialExpression.additionalSearchResult,
            elseCase = elseExpression.toDopeType(),
        )

        val actual: TypeExpression<ValidType> = initialExpression.otherwise(elseExpression)

        assertEquals(expected.toDopeQuery(manager), actual.toDopeQuery(manager))
    }
}
