package ch.ergon.dope.couchbase.resolvable.keyspace

import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.resolvable.keyspace.DefinableKeyspace
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

abstract class SystemKeyspace(
    keyspaceName: String,
) : DefinableKeyspace("system:$keyspaceName", keyspaceName) {
    protected fun string(name: String): IField<StringType> = Field(name, this)
    protected fun number(name: String): IField<NumberType> = Field(name, this)
    protected fun bool(name: String): IField<BooleanType> = Field(name, this)
    protected fun obj(name: String): IField<ObjectType> = Field(name, this)

    protected fun stringArray(name: String): IField<ArrayType<StringType>> = Field(name, this)
    protected fun objectArray(name: String): IField<ArrayType<ObjectType>> = Field(name, this)
}

class DatastoresKeyspace internal constructor() : SystemKeyspace("datastores") {
    val id: IField<StringType> = string("id")
    val url: IField<StringType> = string("url")
}

class NamespacesKeyspace internal constructor() : SystemKeyspace("namespaces") {
    val id: IField<StringType> = string("id")
    val name: IField<StringType> = string("name")
    val datastoreId: IField<StringType> = string("datastore_id")
}

class BucketsKeyspace internal constructor() : SystemKeyspace("buckets") {
    val datastoreId: IField<StringType> = string("datastore_id")
    val name: IField<StringType> = string("name")
    val namespace: IField<StringType> = string("namespace")
    val namespaceId: IField<StringType> = string("namespace_id")
    val path: IField<StringType> = string("path")
}

class ScopesKeyspace internal constructor() : SystemKeyspace("scopes") {
    val bucketName: IField<StringType> = string("bucket")
    val datastoreId: IField<StringType> = string("datastore_id")
    val name: IField<StringType> = string("name")
    val namespace: IField<StringType> = string("namespace")
    val namespaceId: IField<StringType> = string("namespace_id")
    val path: IField<StringType> = string("path")
}

class KeyspacesKeyspace internal constructor() : SystemKeyspace("keyspaces") {
    val bucketName: IField<StringType> = string("bucket")
    val datastoreId: IField<StringType> = string("datastore_id")
    val id: IField<StringType> = string("id")
    val name: IField<StringType> = string("name")
    val namespace: IField<StringType> = string("namespace")
    val namespaceId: IField<StringType> = string("namespace_id")
    val path: IField<StringType> = string("path")
    val scopeName: IField<StringType> = string("scope")
}

class IndexesKeyspace internal constructor() : SystemKeyspace("indexes") {
    val bucketId: IField<StringType> = string("bucket_id")
    val condition: IField<StringType> = string("condition")
    val datastoreId: IField<StringType> = string("datastore_id")
    val id: IField<StringType> = string("id")
    val indexKey: IField<ArrayType<StringType>> = stringArray("index_key")
    val isPrimary: IField<BooleanType> = bool("is_primary")
    val keyspaceId: IField<StringType> = string("keyspace_id")
    val name: IField<StringType> = string("name")
    val metadata: IField<ObjectType> = obj("metadata")
    val namespaceId: IField<StringType> = string("namespace_id")
    val state: IField<StringType> = string("state")
    val using: IField<StringType> = string("using")
}

class GroupInfoKeyspace internal constructor() : SystemKeyspace("group_info") {
    val description: IField<StringType> = string("description")
    val id: IField<StringType> = string("id")
    val ldapGroupRef: IField<StringType> = string("ldap_group_ref")
    val roles: IField<ArrayType<ObjectType>> = objectArray("roles")
}

class BucketInfoKeyspace internal constructor() : SystemKeyspace("bucket_info")

class DualKeyspace internal constructor() : SystemKeyspace("dual")

class VitalsKeyspace internal constructor() : SystemKeyspace("vitals")

class ActiveRequestsKeyspace internal constructor() : SystemKeyspace("active_requests")

class PreparedsKeyspace internal constructor() : SystemKeyspace("prepareds")

class CompletedRequestsKeyspace internal constructor() : SystemKeyspace("completed_requests")

class CompletedRequestsHistoryKeyspace internal constructor() : SystemKeyspace("completed_requests_history")

class AwrKeyspace internal constructor() : SystemKeyspace("awr")

class MyUserInfoKeyspace internal constructor() : SystemKeyspace("my_user_info") {
    val domain: IField<StringType> = string("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArray("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArray("groups")
    val id: IField<StringType> = string("id")
    val name: IField<StringType> = string("name")
    val passwordChangeDate: IField<StringType> = string("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArray("roles")
}

class UserInfoKeyspace internal constructor() : SystemKeyspace("user_info") {
    val domain: IField<StringType> = string("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArray("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArray("groups")
    val id: IField<StringType> = string("id")
    val name: IField<StringType> = string("name")
    val passwordChangeDate: IField<StringType> = string("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArray("roles")
}

class NodesKeyspace internal constructor() : SystemKeyspace("nodes") {
    val name: IField<StringType> = string("name")
    val ports: IField<ObjectType> = obj("ports")
    val services: IField<ArrayType<StringType>> = stringArray("services")
}

class ApplicableRolesKeyspace internal constructor() : SystemKeyspace("applicable_roles") {
    val bucketName: IField<StringType> = string("bucket_name")
    val scopeName: IField<StringType> = string("scope_name")
    val collectionName: IField<StringType> = string("collection_name")
    val grantee: IField<StringType> = string("grantee")
    val role: IField<StringType> = string("role")
}

class DictionaryKeyspace internal constructor() : SystemKeyspace("dictionary") {
    val avgDocKeySize: IField<NumberType> = number("avgDocKeySize")
    val avgDocSize: IField<NumberType> = number("avgDocSize")
    val bucketName: IField<StringType> = string("bucket")
    val distributionKeys: IField<ArrayType<StringType>> = stringArray("distributionKeys")
    val docCount: IField<NumberType> = number("docCount")
    val indexes: IField<ArrayType<ObjectType>> = objectArray("indexes")
    val keyspaceName: IField<StringType> = string("keyspace")
    val namespace: IField<StringType> = string("namespace")
    val node: IField<StringType> = string("node")
    val scopeName: IField<StringType> = string("scope")
}

class DictionaryCacheKeyspace internal constructor() : SystemKeyspace("dictionary_cache") {
    val avgDocKeySize: IField<NumberType> = number("avgDocKeySize")
    val avgDocSize: IField<NumberType> = number("avgDocSize")
    val bucketName: IField<StringType> = string("bucket")
    val distributionKeys: IField<ArrayType<StringType>> = stringArray("distributionKeys")
    val docCount: IField<NumberType> = number("docCount")
    val indexes: IField<ArrayType<ObjectType>> = objectArray("indexes")
    val keyspaceName: IField<StringType> = string("keyspace")
    val namespace: IField<StringType> = string("namespace")
    val node: IField<StringType> = string("node")
    val scopeName: IField<StringType> = string("scope")
}

class FunctionsKeyspace internal constructor() : SystemKeyspace("functions") {
    val definition: IField<ObjectType> = obj("definition")
    val identity: IField<ObjectType> = obj("identity")
}

class FunctionsCacheKeyspace internal constructor() : SystemKeyspace("functions_cache") {
    val language: IField<StringType> = string("#language")
    val avgServiceTime: IField<StringType> = string("avgServiceTime")
    val lastUse: IField<StringType> = string("lastUse")
    val maxServiceTime: IField<StringType> = string("maxServiceTime")
    val minServiceTime: IField<StringType> = string("minServiceTime")
    val name: IField<StringType> = string("name")
    val namespace: IField<StringType> = string("namespace")
    val node: IField<StringType> = string("node")
    val parameters: IField<ArrayType<StringType>> = stringArray("parameters")
    val type: IField<StringType> = string("type")
    val scopeName: IField<StringType> = string("scope")
    val expression: IField<StringType> = string("expression")
    val text: IField<StringType> = string("text")
    val library: IField<StringType> = string("library")
    val objectName: IField<StringType> = string("object")
    val undefinedFunction: IField<BooleanType> = bool("undefined_function")
    val uses: IField<NumberType> = number("uses")
}

class TasksCacheKeyspace internal constructor() : SystemKeyspace("tasks_cache") {
    val taskClass: IField<StringType> = string("class")
    val delay: IField<StringType> = string("delay")
    val id: IField<StringType> = string("id")
    val name: IField<StringType> = string("name")
    val node: IField<StringType> = string("node")
    val state: IField<StringType> = string("state")
    val subClass: IField<StringType> = string("subClass")
    val submitTime: IField<StringType> = string("submitTime")
    val results: IField<ArrayType<ObjectType>> = objectArray("results")
    val startTime: IField<StringType> = string("startTime")
    val stopTime: IField<StringType> = string("stopTime")
}

class TransactionsKeyspace internal constructor() : SystemKeyspace("transactions") {
    val durabilityLevel: IField<StringType> = string("durabilityLevel")
    val durabilityTimeout: IField<StringType> = string("durabilityTimeout")
    val expiryTime: IField<StringType> = string("expiryTime")
    val id: IField<StringType> = string("id")
    val isolationLevel: IField<StringType> = string("isolationLevel")
    val lastUse: IField<StringType> = string("lastUse")
    val node: IField<StringType> = string("node")
    val numAtrs: IField<NumberType> = number("numAtrs")
    val scanConsistency: IField<StringType> = string("scanConsistency")
    val status: IField<NumberType> = number("status")
    val timeout: IField<StringType> = string("timeout")
    val usedMemory: IField<NumberType> = number("usedMemory")
    val uses: IField<NumberType> = number("uses")
}

class SequencesKeyspace internal constructor() : SystemKeyspace("sequences") {
    val bucketName: IField<StringType> = string("bucket")
    val cache: IField<NumberType> = number("cache")
    val cycle: IField<BooleanType> = bool("cycle")
    val increment: IField<NumberType> = number("increment")
    val max: IField<NumberType> = number("max")
    val min: IField<NumberType> = number("min")
    val name: IField<StringType> = string("name")
    val namespace: IField<StringType> = string("namespace")
    val namespaceId: IField<StringType> = string("namespace_id")
    val path: IField<StringType> = string("path")
    val scopeId: IField<StringType> = string("scope_id")
    val value: IField<ObjectType> = obj("value")
}

class AllSequencesKeyspace internal constructor() : SystemKeyspace("all_sequences") {
    val bucketName: IField<StringType> = string("bucket")
    val cache: IField<NumberType> = number("cache")
    val cycle: IField<BooleanType> = bool("cycle")
    val increment: IField<NumberType> = number("increment")
    val max: IField<NumberType> = number("max")
    val min: IField<NumberType> = number("min")
    val name: IField<StringType> = string("name")
    val namespace: IField<StringType> = string("namespace")
    val namespaceId: IField<StringType> = string("namespace_id")
    val path: IField<StringType> = string("path")
    val scopeId: IField<StringType> = string("scope_id")
    val value: IField<ObjectType> = obj("value")
}

class AusKeyspace internal constructor() : SystemKeyspace("aus")

class AusSettingsKeyspace internal constructor() : SystemKeyspace("aus_settings")
