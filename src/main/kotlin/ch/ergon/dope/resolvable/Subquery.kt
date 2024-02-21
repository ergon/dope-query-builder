package ch.ergon.dope.resolvable

// TODO: DOPE-170
// class Subquery(val name: String, val query: Query) {
//     private val fields: MutableList<Resolvable> = mutableListOf()
//     fun printIt() {
//         query[0].let { clause ->
//             run {
//                 when (clause) {
//                     is ClauseWithMultipleExpressions -> clause.expressions.forEach { validType: Resolvable ->
//                         fields.add(validType)
//                     }
//
//                     is ClauseWithOneResolvable -> fields.add(clause.resolvable)
//                     else -> error("could not be assigned to clause")
//                 }
//             }
//         }
//     }
// }
