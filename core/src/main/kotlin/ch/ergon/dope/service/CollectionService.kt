package ch.ergon.dope.service

import ch.ergon.dope.resolvable.fromable.Collection

class CollectionService {
    fun memberIsInCollection(memberName: String, collectionName: Collection): Boolean {
        val members = getMembersOfCollection(collectionName)

        return if (members.isNotEmpty()) {
            memberName in members
        } else {
            false
        }
    }

    private fun getMembersOfCollection(collection: Collection): List<String> = collection::class.members.map { it.name }
}
