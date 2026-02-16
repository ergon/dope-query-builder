package ch.ergon.dope.couchbase.resolvable.keyspace

import ch.ergon.dope.resolvable.bucket.BucketScope
import ch.ergon.dope.resolvable.bucket.Definable
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

abstract class SystemKeyspace(
    keyspaceName: String,
) : Definable {
    override val name: String = "system:$keyspaceName"
    override val alias = keyspaceName
    override val scope: BucketScope? = null

    protected fun stringField(name: String): IField<StringType> = Field(name, this)
    protected fun numberField(name: String): IField<NumberType> = Field(name, this)
    protected fun booleanField(name: String): IField<BooleanType> = Field(name, this)
    protected fun objectField(name: String): IField<ObjectType> = Field(name, this)

    protected fun stringArrayField(name: String): IField<ArrayType<StringType>> = Field(name, this)
    protected fun objectArrayField(name: String): IField<ArrayType<ObjectType>> = Field(name, this)
}

class DatastoresKeyspace internal constructor() : SystemKeyspace("datastores") {
    val id: IField<StringType> = stringField("id")
    val url: IField<StringType> = stringField("url")
}

class NamespacesKeyspace internal constructor() : SystemKeyspace("namespaces") {
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val datastoreId: IField<StringType> = stringField("datastore_id")
}

class BucketsKeyspace internal constructor() : SystemKeyspace("buckets") {
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
}

class ScopesKeyspace internal constructor() : SystemKeyspace("scopes") {
    val bucketName: IField<StringType> = stringField("bucket")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
}

class KeyspacesKeyspace internal constructor() : SystemKeyspace("keyspaces") {
    val bucketName: IField<StringType> = stringField("bucket")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
    val scopeField: IField<StringType> = stringField("scope")
}

class IndexesKeyspace internal constructor() : SystemKeyspace("indexes") {
    val bucketId: IField<StringType> = stringField("bucket_id")
    val condition: IField<StringType> = stringField("condition")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val id: IField<StringType> = stringField("id")
    val indexKey: IField<ArrayType<StringType>> = stringArrayField("index_key")
    val isPrimary: IField<BooleanType> = booleanField("is_primary")
    val keyspaceId: IField<StringType> = stringField("keyspace_id")
    val nameField: IField<StringType> = stringField("name")
    val metadata: IField<ObjectType> = objectField("metadata")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val state: IField<StringType> = stringField("state")
    val using: IField<StringType> = stringField("using")
}

class GroupInfoKeyspace internal constructor() : SystemKeyspace("group_info") {
    val description: IField<StringType> = stringField("description")
    val id: IField<StringType> = stringField("id")
    val ldapGroupRef: IField<StringType> = stringField("ldap_group_ref")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")
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
    val domain: IField<StringType> = stringField("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArrayField("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArrayField("groups")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val passwordChangeDate: IField<StringType> = stringField("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")
}

class UserInfoKeyspace internal constructor() : SystemKeyspace("user_info") {
    val domain: IField<StringType> = stringField("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArrayField("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArrayField("groups")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val passwordChangeDate: IField<StringType> = stringField("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")
}

class NodesKeyspace internal constructor() : SystemKeyspace("nodes") {
    val nameField: IField<StringType> = stringField("name")
    val ports: IField<ObjectType> = objectField("ports")
    val services: IField<ArrayType<StringType>> = stringArrayField("services")
}

class ApplicableRolesKeyspace internal constructor() : SystemKeyspace("applicable_roles") {
    val bucketName: IField<StringType> = stringField("bucket_name")
    val scopeField: IField<StringType> = stringField("scope_name")
    val collectionField: IField<StringType> = stringField("collection_name")
    val grantee: IField<StringType> = stringField("grantee")
    val role: IField<StringType> = stringField("role")
}

class DictionaryKeyspace internal constructor() : SystemKeyspace("dictionary") {
    val avgDocKeySize: IField<NumberType> = numberField("avgDocKeySize")
    val avgDocSize: IField<NumberType> = numberField("avgDocSize")
    val bucketName: IField<StringType> = stringField("bucket")
    val distributionKeys: IField<ArrayType<StringType>> = stringArrayField("distributionKeys")
    val docCount: IField<NumberType> = numberField("docCount")
    val indexes: IField<ArrayType<ObjectType>> = objectArrayField("indexes")
    val keyspaceName: IField<StringType> = stringField("keyspace")
    val namespace: IField<StringType> = stringField("namespace")
    val node: IField<StringType> = stringField("node")
    val scopeField: IField<StringType> = stringField("scope")
}

class DictionaryCacheKeyspace internal constructor() : SystemKeyspace("dictionary_cache") {
    val avgDocKeySize: IField<NumberType> = numberField("avgDocKeySize")
    val avgDocSize: IField<NumberType> = numberField("avgDocSize")
    val bucketName: IField<StringType> = stringField("bucket")
    val distributionKeys: IField<ArrayType<StringType>> = stringArrayField("distributionKeys")
    val docCount: IField<NumberType> = numberField("docCount")
    val indexes: IField<ArrayType<ObjectType>> = objectArrayField("indexes")
    val keyspaceName: IField<StringType> = stringField("keyspace")
    val namespace: IField<StringType> = stringField("namespace")
    val node: IField<StringType> = stringField("node")
    val scopeField: IField<StringType> = stringField("scope")
}

class FunctionsKeyspace internal constructor() : SystemKeyspace("functions") {
    val definition: IField<ObjectType> = objectField("definition")
    val identity: IField<ObjectType> = objectField("identity")
}

class FunctionsCacheKeyspace internal constructor() : SystemKeyspace("functions_cache") {
    val language: IField<StringType> = stringField("#language")
    val avgServiceTime: IField<StringType> = stringField("avgServiceTime")
    val lastUse: IField<StringType> = stringField("lastUse")
    val maxServiceTime: IField<StringType> = stringField("maxServiceTime")
    val minServiceTime: IField<StringType> = stringField("minServiceTime")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val node: IField<StringType> = stringField("node")
    val parameters: IField<ArrayType<StringType>> = stringArrayField("parameters")
    val type: IField<StringType> = stringField("type")
    val scopeField: IField<StringType> = stringField("scope")
    val expression: IField<StringType> = stringField("expression")
    val text: IField<StringType> = stringField("text")
    val library: IField<StringType> = stringField("library")
    val objectName: IField<StringType> = stringField("object")
    val undefinedFunction: IField<BooleanType> = booleanField("undefined_function")
    val uses: IField<NumberType> = numberField("uses")
}

class TasksCacheKeyspace internal constructor() : SystemKeyspace("tasks_cache") {
    val taskClass: IField<StringType> = stringField("class")
    val delay: IField<StringType> = stringField("delay")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val node: IField<StringType> = stringField("node")
    val state: IField<StringType> = stringField("state")
    val subClass: IField<StringType> = stringField("subClass")
    val submitTime: IField<StringType> = stringField("submitTime")
    val results: IField<ArrayType<ObjectType>> = objectArrayField("results")
    val startTime: IField<StringType> = stringField("startTime")
    val stopTime: IField<StringType> = stringField("stopTime")
}

class TransactionsKeyspace internal constructor() : SystemKeyspace("transactions") {
    val durabilityLevel: IField<StringType> = stringField("durabilityLevel")
    val durabilityTimeout: IField<StringType> = stringField("durabilityTimeout")
    val expiryTime: IField<StringType> = stringField("expiryTime")
    val id: IField<StringType> = stringField("id")
    val isolationLevel: IField<StringType> = stringField("isolationLevel")
    val lastUse: IField<StringType> = stringField("lastUse")
    val node: IField<StringType> = stringField("node")
    val numAtrs: IField<NumberType> = numberField("numAtrs")
    val scanConsistency: IField<StringType> = stringField("scanConsistency")
    val status: IField<NumberType> = numberField("status")
    val timeout: IField<StringType> = stringField("timeout")
    val usedMemory: IField<NumberType> = numberField("usedMemory")
    val uses: IField<NumberType> = numberField("uses")
}

class SequencesKeyspace internal constructor() : SystemKeyspace("sequences") {
    val bucketName: IField<StringType> = stringField("bucket")
    val cache: IField<NumberType> = numberField("cache")
    val cycle: IField<BooleanType> = booleanField("cycle")
    val increment: IField<NumberType> = numberField("increment")
    val max: IField<NumberType> = numberField("max")
    val min: IField<NumberType> = numberField("min")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
    val scopeId: IField<StringType> = stringField("scope_id")
    val value: IField<ObjectType> = objectField("value")
}

class AllSequencesKeyspace internal constructor() : SystemKeyspace("all_sequences") {
    val bucketName: IField<StringType> = stringField("bucket")
    val cache: IField<NumberType> = numberField("cache")
    val cycle: IField<BooleanType> = booleanField("cycle")
    val increment: IField<NumberType> = numberField("increment")
    val max: IField<NumberType> = numberField("max")
    val min: IField<NumberType> = numberField("min")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
    val scopeId: IField<StringType> = stringField("scope_id")
    val value: IField<ObjectType> = objectField("value")
}

class AusKeyspace internal constructor() : SystemKeyspace("aus")

class AusSettingsKeyspace internal constructor() : SystemKeyspace("aus_settings")
