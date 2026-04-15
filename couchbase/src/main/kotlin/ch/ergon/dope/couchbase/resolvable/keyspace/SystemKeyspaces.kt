package ch.ergon.dope.couchbase.resolvable.keyspace

import ch.ergon.dope.resolvable.Resolvable
import ch.ergon.dope.resolvable.bucket.AliasedBucket
import ch.ergon.dope.resolvable.bucket.UnaliasedBucket
import ch.ergon.dope.resolvable.expression.type.Field
import ch.ergon.dope.resolvable.expression.type.IField
import ch.ergon.dope.validtype.ArrayType
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.ObjectType
import ch.ergon.dope.validtype.StringType

abstract class SystemKeyspace(
    val keyspace: String,
    private val keyspaceAlias: String? = null,
) : AliasedBucket("system:$keyspace", keyspaceAlias ?: keyspace) {
    override fun asBucketDefinition(): Resolvable = if (keyspaceAlias != null) {
        super.asBucketDefinition()
    } else {
        UnaliasedBucket(name)
    }

    protected fun stringField(name: String): IField<StringType> = Field(name, this)
    protected fun numberField(name: String): IField<NumberType> = Field(name, this)
    protected fun booleanField(name: String): IField<BooleanType> = Field(name, this)
    protected fun objectField(name: String): IField<ObjectType> = Field(name, this)

    protected fun stringArrayField(name: String): IField<ArrayType<StringType>> = Field(name, this)
    protected fun objectArrayField(name: String): IField<ArrayType<ObjectType>> = Field(name, this)
}

class DatastoresKeyspace internal constructor(alias: String? = null) : SystemKeyspace("datastores", alias) {
    val id: IField<StringType> = stringField("id")
    val url: IField<StringType> = stringField("url")

    fun alias(alias: String) = DatastoresKeyspace(alias)
}

class NamespacesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("namespaces", alias) {
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val datastoreId: IField<StringType> = stringField("datastore_id")

    fun alias(alias: String) = NamespacesKeyspace(alias)
}

class BucketsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("buckets", alias) {
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")

    fun alias(alias: String) = BucketsKeyspace(alias)
}

class ScopesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("scopes", alias) {
    val bucketName: IField<StringType> = stringField("bucket")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")

    fun alias(alias: String) = ScopesKeyspace(alias)
}

class KeyspacesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("keyspaces", alias) {
    val bucketName: IField<StringType> = stringField("bucket")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
    val scopeField: IField<StringType> = stringField("scope")

    fun alias(alias: String) = KeyspacesKeyspace(alias)
}

class IndexesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("indexes", alias) {
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

    fun alias(alias: String) = IndexesKeyspace(alias)
}

class GroupInfoKeyspace internal constructor(alias: String? = null) : SystemKeyspace("group_info", alias) {
    val description: IField<StringType> = stringField("description")
    val id: IField<StringType> = stringField("id")
    val ldapGroupRef: IField<StringType> = stringField("ldap_group_ref")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")

    fun alias(alias: String) = GroupInfoKeyspace(alias)
}

class BucketInfoKeyspace internal constructor(alias: String? = null) : SystemKeyspace("bucket_info", alias) {
    fun alias(alias: String) = BucketInfoKeyspace(alias)
}

class DualKeyspace internal constructor(alias: String? = null) : SystemKeyspace("dual", alias) {
    fun alias(alias: String) = DualKeyspace(alias)
}

class VitalsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("vitals", alias) {
    fun alias(alias: String) = VitalsKeyspace(alias)
}

class ActiveRequestsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("active_requests", alias) {
    fun alias(alias: String) = ActiveRequestsKeyspace(alias)
}

class PreparedsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("prepareds", alias) {
    fun alias(alias: String) = PreparedsKeyspace(alias)
}

class CompletedRequestsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("completed_requests", alias) {
    fun alias(alias: String) = CompletedRequestsKeyspace(alias)
}

class CompletedRequestsHistoryKeyspace internal constructor(alias: String? = null) : SystemKeyspace("completed_requests_history", alias) {
    fun alias(alias: String) = CompletedRequestsHistoryKeyspace(alias)
}

class AwrKeyspace internal constructor(alias: String? = null) : SystemKeyspace("awr", alias) {
    fun alias(alias: String) = AwrKeyspace(alias)
}

class MyUserInfoKeyspace internal constructor(alias: String? = null) : SystemKeyspace("my_user_info", alias) {
    val domain: IField<StringType> = stringField("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArrayField("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArrayField("groups")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val passwordChangeDate: IField<StringType> = stringField("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")

    fun alias(alias: String) = MyUserInfoKeyspace(alias)
}

class UserInfoKeyspace internal constructor(alias: String? = null) : SystemKeyspace("user_info", alias) {
    val domain: IField<StringType> = stringField("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArrayField("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArrayField("groups")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val passwordChangeDate: IField<StringType> = stringField("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")

    fun alias(alias: String) = UserInfoKeyspace(alias)
}

class NodesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("nodes", alias) {
    val nameField: IField<StringType> = stringField("name")
    val ports: IField<ObjectType> = objectField("ports")
    val services: IField<ArrayType<StringType>> = stringArrayField("services")

    fun alias(alias: String) = NodesKeyspace(alias)
}

class ApplicableRolesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("applicable_roles", alias) {
    val bucketName: IField<StringType> = stringField("bucket_name")
    val scopeField: IField<StringType> = stringField("scope_name")
    val collectionField: IField<StringType> = stringField("collection_name")
    val grantee: IField<StringType> = stringField("grantee")
    val role: IField<StringType> = stringField("role")

    fun alias(alias: String) = ApplicableRolesKeyspace(alias)
}

class DictionaryKeyspace internal constructor(alias: String? = null) : SystemKeyspace("dictionary", alias) {
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

    fun alias(alias: String) = DictionaryKeyspace(alias)
}

class DictionaryCacheKeyspace internal constructor(alias: String? = null) : SystemKeyspace("dictionary_cache", alias) {
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

    fun alias(alias: String) = DictionaryCacheKeyspace(alias)
}

class FunctionsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("functions", alias) {
    val definition: IField<ObjectType> = objectField("definition")
    val identity: IField<ObjectType> = objectField("identity")

    fun alias(alias: String) = FunctionsKeyspace(alias)
}

class FunctionsCacheKeyspace internal constructor(alias: String? = null) : SystemKeyspace("functions_cache", alias) {
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

    fun alias(alias: String) = FunctionsCacheKeyspace(alias)
}

class TasksCacheKeyspace internal constructor(alias: String? = null) : SystemKeyspace("tasks_cache", alias) {
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

    fun alias(alias: String) = TasksCacheKeyspace(alias)
}

class TransactionsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("transactions", alias) {
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

    fun alias(alias: String) = TransactionsKeyspace(alias)
}

class SequencesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("sequences", alias) {
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

    fun alias(alias: String) = SequencesKeyspace(alias)
}

class AllSequencesKeyspace internal constructor(alias: String? = null) : SystemKeyspace("all_sequences", alias) {
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

    fun alias(alias: String) = AllSequencesKeyspace(alias)
}

class AusKeyspace internal constructor(alias: String? = null) : SystemKeyspace("aus", alias) {
    fun alias(alias: String) = AusKeyspace(alias)
}

class AusSettingsKeyspace internal constructor(alias: String? = null) : SystemKeyspace("aus_settings", alias) {
    fun alias(alias: String) = AusSettingsKeyspace(alias)
}
