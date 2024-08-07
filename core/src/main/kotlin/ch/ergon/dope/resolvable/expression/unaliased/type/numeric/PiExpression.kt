package ch.ergon.dope.resolvable.expression.unaliased.type.numeric

class PiExpression : NumberFunctionExpression("PI")

fun pi() = PiExpression()
