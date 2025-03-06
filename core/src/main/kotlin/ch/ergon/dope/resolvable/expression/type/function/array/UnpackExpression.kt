package ch.ergon.dope.resolvable.expression.type.function.array

import ch.ergon.dope.DopeQuery
import ch.ergon.dope.DopeQueryManager
import ch.ergon.dope.resolvable.ASTERISK_STRING
import ch.ergon.dope.resolvable.clause.ISelectOffsetClause
import ch.ergon.dope.resolvable.expression.type.TypeExpression
import ch.ergon.dope.resolvable.expression.type.toDopeType
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.ObjectType

class UnpackExpression(
    private val objectArray: TypeExpression<ArrayType<ObjectType>>,
) : TypeExpression<ObjectType> {
    override fun toDopeQuery(manager: DopeQueryManager): DopeQuery {
        val objectArrayDopeQuery = objectArray.toDopeQuery(manager)
        return DopeQuery(
            queryString = "${objectArrayDopeQuery.queryString}[$ASTERISK_STRING]",
            parameters = objectArrayDopeQuery.parameters,
        )
    }
}

fun TypeExpression<ArrayType<ObjectType>>.unpack() = UnpackExpression(this)

fun Collection<TypeExpression<ObjectType>>.unpack() = toDopeType().unpack()

fun ISelectOffsetClause<ObjectType>.unpack() = asExpression().unpack()
