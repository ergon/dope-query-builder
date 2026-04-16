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

abstract class SystemBucket(
    keyspace: String,
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

class DatastoresBucket internal constructor(alias: String? = null) : SystemBucket("datastores", alias) {
    val id: IField<StringType> = stringField("id")
    val url: IField<StringType> = stringField("url")

    fun alias(alias: String) = DatastoresBucket(alias)
}

class NamespacesBucket internal constructor(alias: String? = null) : SystemBucket("namespaces", alias) {
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val datastoreId: IField<StringType> = stringField("datastore_id")

    fun alias(alias: String) = NamespacesBucket(alias)
}

class BucketsBucket internal constructor(alias: String? = null) : SystemBucket("buckets", alias) {
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")

    fun alias(alias: String) = BucketsBucket(alias)
}

class ScopesBucket internal constructor(alias: String? = null) : SystemBucket("scopes", alias) {
    val bucketName: IField<StringType> = stringField("bucket")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")

    fun alias(alias: String) = ScopesBucket(alias)
}

class KeyspacesBucket internal constructor(alias: String? = null) : SystemBucket("keyspaces", alias) {
    val bucketName: IField<StringType> = stringField("bucket")
    val datastoreId: IField<StringType> = stringField("datastore_id")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val namespaceId: IField<StringType> = stringField("namespace_id")
    val path: IField<StringType> = stringField("path")
    val scopeField: IField<StringType> = stringField("scope")

    fun alias(alias: String) = KeyspacesBucket(alias)
}

class IndexesBucket internal constructor(alias: String? = null) : SystemBucket("indexes", alias) {
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

    fun alias(alias: String) = IndexesBucket(alias)
}

class GroupInfoBucket internal constructor(alias: String? = null) : SystemBucket("group_info", alias) {
    val description: IField<StringType> = stringField("description")
    val id: IField<StringType> = stringField("id")
    val ldapGroupRef: IField<StringType> = stringField("ldap_group_ref")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")

    fun alias(alias: String) = GroupInfoBucket(alias)
}

class BucketInfoBucket internal constructor(alias: String? = null) : SystemBucket("bucket_info", alias) {
    fun alias(alias: String) = BucketInfoBucket(alias)
}

class DualBucket internal constructor(alias: String? = null) : SystemBucket("dual", alias) {
    fun alias(alias: String) = DualBucket(alias)
}

class VitalsBucket internal constructor(alias: String? = null) : SystemBucket("vitals", alias) {
    val uptime: IField<StringType> = stringField("uptime")
    val localTime: IField<StringType> = stringField("local.time")
    val version: IField<StringType> = stringField("version")
    val totalThreads: IField<NumberType> = numberField("total.threads")
    val cores: IField<NumberType> = numberField("cores")
    val gcNum: IField<NumberType> = numberField("gc.num")
    val gcPauseTime: IField<StringType> = stringField("gc.pause.time")
    val gcPausePercent: IField<NumberType> = numberField("gc.pause.percent")
    val memoryUsage: IField<NumberType> = numberField("memory.usage")
    val memoryTotal: IField<NumberType> = numberField("memory.total")
    val memorySystem: IField<NumberType> = numberField("memory.system")
    val cpuUserPercent: IField<NumberType> = numberField("cpu.user.percent")
    val cpuSysPercent: IField<NumberType> = numberField("cpu.sys.percent")
    val requestCompletedCount: IField<NumberType> = numberField("request.completed.count")
    val requestActiveCount: IField<NumberType> = numberField("request.active.count")
    val requestPerSec1min: IField<NumberType> = numberField("request.per.sec.1min")
    val requestPerSec5min: IField<NumberType> = numberField("request.per.sec.5min")
    val requestPerSec15min: IField<NumberType> = numberField("request.per.sec.15min")
    val requestTimeMean: IField<StringType> = stringField("request_time.mean")
    val requestTimeMedian: IField<StringType> = stringField("request_time.median")
    val requestTime80percentile: IField<StringType> = stringField("request_time.80percentile")
    val requestTime95percentile: IField<StringType> = stringField("request_time.95percentile")
    val requestTime99percentile: IField<StringType> = stringField("request_time.99percentile")
    val requestPreparedPercent: IField<NumberType> = numberField("request.prepared.percent")
    val awr: IField<ObjectType> = objectField("awr")

    fun alias(alias: String) = VitalsBucket(alias)
}

class ActiveRequestsBucket internal constructor(alias: String? = null) : SystemBucket("active_requests", alias) {
    val clientContextID: IField<StringType> = stringField("clientContextID")
    val cpuTime: IField<StringType> = stringField("cpuTime")
    val elapsedTime: IField<StringType> = stringField("elapsedTime")
    val executionTime: IField<StringType> = stringField("executionTime")
    val ioTime: IField<StringType> = stringField("ioTime")
    val memoryQuota: IField<NumberType> = numberField("memoryQuota")
    val n1qlFeatCtrl: IField<NumberType> = numberField("n1qlFeatCtrl")
    val node: IField<StringType> = stringField("node")
    val phaseOperators: IField<ObjectType> = objectField("phaseOperators")
    val phaseTimes: IField<ObjectType> = objectField("phaseTimes")
    val queryContext: IField<StringType> = stringField("queryContext")
    val remoteAddr: IField<StringType> = stringField("remoteAddr")
    val requestId: IField<StringType> = stringField("requestId")
    val requestTime: IField<StringType> = stringField("requestTime")
    val scanConsistency: IField<StringType> = stringField("scanConsistency")
    val state: IField<StringType> = stringField("state")
    val statement: IField<StringType> = stringField("statement")
    val statementType: IField<StringType> = stringField("statementType")
    val useCBO: IField<BooleanType> = booleanField("useCBO")
    val userAgent: IField<StringType> = stringField("userAgent")
    val users: IField<StringType> = stringField("users")
    val waitTime: IField<StringType> = stringField("waitTime")

    fun alias(alias: String) = ActiveRequestsBucket(alias)
}

class PreparedsBucket internal constructor(alias: String? = null) : SystemBucket("prepareds", alias) {
    val encodedPlan: IField<StringType> = stringField("encoded_plan")
    val featuresControl: IField<NumberType> = numberField("featuresControl")
    val indexApiVersion: IField<NumberType> = numberField("indexApiVersion")
    val indexScanKeyspaces: IField<ObjectType> = objectField("indexScanKeyspaces")
    val nameField: IField<StringType> = stringField("name")
    val namespace: IField<StringType> = stringField("namespace")
    val node: IField<StringType> = stringField("node")
    val statement: IField<StringType> = stringField("statement")
    val uses: IField<NumberType> = numberField("uses")

    fun alias(alias: String) = PreparedsBucket(alias)
}

class CompletedRequestsBucket internal constructor(alias: String? = null) : SystemBucket("completed_requests", alias) {
    val clientContextID: IField<StringType> = stringField("clientContextID")
    val cpuTime: IField<StringType> = stringField("cpuTime")
    val elapsedTime: IField<StringType> = stringField("elapsedTime")
    val errorCount: IField<NumberType> = numberField("errorCount")
    val errors: IField<ArrayType<ObjectType>> = objectArrayField("errors")
    val ioTime: IField<StringType> = stringField("ioTime")
    val memoryQuota: IField<NumberType> = numberField("memoryQuota")
    val n1qlFeatCtrl: IField<NumberType> = numberField("n1qlFeatCtrl")
    val namedArgs: IField<ObjectType> = objectField("namedArgs")
    val phaseCounts: IField<ObjectType> = objectField("phaseCounts")
    val phaseOperators: IField<ObjectType> = objectField("phaseOperators")
    val phaseTimes: IField<ObjectType> = objectField("phaseTimes")
    val queryContext: IField<StringType> = stringField("queryContext")
    val remoteAddr: IField<StringType> = stringField("remoteAddr")
    val requestId: IField<StringType> = stringField("requestId")
    val requestTime: IField<StringType> = stringField("requestTime")
    val resultCount: IField<NumberType> = numberField("resultCount")
    val resultSize: IField<NumberType> = numberField("resultSize")
    val scanConsistency: IField<StringType> = stringField("scanConsistency")
    val serviceTime: IField<StringType> = stringField("serviceTime")
    val sqlID: IField<StringType> = stringField("sqlID")
    val sessionMemory: IField<NumberType> = numberField("sessionMemory")
    val state: IField<StringType> = stringField("state")
    val statement: IField<StringType> = stringField("statement")
    val statementType: IField<StringType> = stringField("statementType")
    val useCBO: IField<BooleanType> = booleanField("useCBO")
    val usedMemory: IField<NumberType> = numberField("usedMemory")
    val userAgent: IField<StringType> = stringField("userAgent")
    val users: IField<StringType> = stringField("users")
    val waitTime: IField<StringType> = stringField("waitTime")
    val analysis: IField<ArrayType<StringType>> = stringArrayField("~analysis")
    val qualifier: IField<StringType> = stringField("~qualifier")

    fun alias(alias: String) = CompletedRequestsBucket(alias)
}

class CompletedRequestsHistoryBucket internal constructor(alias: String? = null) : SystemBucket("completed_requests_history", alias) {
    val clientContextID: IField<StringType> = stringField("clientContextID")
    val cpuTime: IField<StringType> = stringField("cpuTime")
    val elapsedTime: IField<StringType> = stringField("elapsedTime")
    val errorCount: IField<NumberType> = numberField("errorCount")
    val errors: IField<ArrayType<ObjectType>> = objectArrayField("errors")
    val ioTime: IField<StringType> = stringField("ioTime")
    val memoryQuota: IField<NumberType> = numberField("memoryQuota")
    val n1qlFeatCtrl: IField<NumberType> = numberField("n1qlFeatCtrl")
    val namedArgs: IField<ObjectType> = objectField("namedArgs")
    val phaseCounts: IField<ObjectType> = objectField("phaseCounts")
    val phaseOperators: IField<ObjectType> = objectField("phaseOperators")
    val phaseTimes: IField<ObjectType> = objectField("phaseTimes")
    val queryContext: IField<StringType> = stringField("queryContext")
    val remoteAddr: IField<StringType> = stringField("remoteAddr")
    val requestId: IField<StringType> = stringField("requestId")
    val requestTime: IField<StringType> = stringField("requestTime")
    val resultCount: IField<NumberType> = numberField("resultCount")
    val resultSize: IField<NumberType> = numberField("resultSize")
    val scanConsistency: IField<StringType> = stringField("scanConsistency")
    val serviceTime: IField<StringType> = stringField("serviceTime")
    val sqlID: IField<StringType> = stringField("sqlID")
    val sessionMemory: IField<NumberType> = numberField("sessionMemory")
    val state: IField<StringType> = stringField("state")
    val statement: IField<StringType> = stringField("statement")
    val statementType: IField<StringType> = stringField("statementType")
    val useCBO: IField<BooleanType> = booleanField("useCBO")
    val usedMemory: IField<NumberType> = numberField("usedMemory")
    val userAgent: IField<StringType> = stringField("userAgent")
    val users: IField<StringType> = stringField("users")
    val waitTime: IField<StringType> = stringField("waitTime")
    val analysis: IField<ArrayType<StringType>> = stringArrayField("~analysis")
    val qualifier: IField<StringType> = stringField("~qualifier")

    fun alias(alias: String) = CompletedRequestsHistoryBucket(alias)
}

class AwrBucket internal constructor(alias: String? = null) : SystemBucket("awr", alias) {
    val enabled: IField<BooleanType> = booleanField("enabled")
    val interval: IField<StringType> = stringField("interval")
    val location: IField<StringType> = stringField("location")
    val numStatements: IField<NumberType> = numberField("num_statements")
    val queueLen: IField<NumberType> = numberField("queue_len")
    val threshold: IField<StringType> = stringField("threshold")

    fun alias(alias: String) = AwrBucket(alias)
}

class MyUserInfoBucket internal constructor(alias: String? = null) : SystemBucket("my_user_info", alias) {
    val domain: IField<StringType> = stringField("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArrayField("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArrayField("groups")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val passwordChangeDate: IField<StringType> = stringField("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")

    fun alias(alias: String) = MyUserInfoBucket(alias)
}

class UserInfoBucket internal constructor(alias: String? = null) : SystemBucket("user_info", alias) {
    val domain: IField<StringType> = stringField("domain")
    val externalGroups: IField<ArrayType<ObjectType>> = objectArrayField("external_groups")
    val groups: IField<ArrayType<ObjectType>> = objectArrayField("groups")
    val id: IField<StringType> = stringField("id")
    val nameField: IField<StringType> = stringField("name")
    val passwordChangeDate: IField<StringType> = stringField("password_change_date")
    val roles: IField<ArrayType<ObjectType>> = objectArrayField("roles")

    fun alias(alias: String) = UserInfoBucket(alias)
}

class NodesBucket internal constructor(alias: String? = null) : SystemBucket("nodes", alias) {
    val nameField: IField<StringType> = stringField("name")
    val ports: IField<ObjectType> = objectField("ports")
    val services: IField<ArrayType<StringType>> = stringArrayField("services")

    fun alias(alias: String) = NodesBucket(alias)
}

class ApplicableRolesBucket internal constructor(alias: String? = null) : SystemBucket("applicable_roles", alias) {
    val bucketName: IField<StringType> = stringField("bucket_name")
    val scopeField: IField<StringType> = stringField("scope_name")
    val collectionField: IField<StringType> = stringField("collection_name")
    val grantee: IField<StringType> = stringField("grantee")
    val role: IField<StringType> = stringField("role")

    fun alias(alias: String) = ApplicableRolesBucket(alias)
}

class DictionaryBucket internal constructor(alias: String? = null) : SystemBucket("dictionary", alias) {
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

    fun alias(alias: String) = DictionaryBucket(alias)
}

class DictionaryCacheBucket internal constructor(alias: String? = null) : SystemBucket("dictionary_cache", alias) {
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

    fun alias(alias: String) = DictionaryCacheBucket(alias)
}

class FunctionsBucket internal constructor(alias: String? = null) : SystemBucket("functions", alias) {
    val definition: IField<ObjectType> = objectField("definition")
    val identity: IField<ObjectType> = objectField("identity")

    fun alias(alias: String) = FunctionsBucket(alias)
}

class FunctionsCacheBucket internal constructor(alias: String? = null) : SystemBucket("functions_cache", alias) {
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

    fun alias(alias: String) = FunctionsCacheBucket(alias)
}

class TasksCacheBucket internal constructor(alias: String? = null) : SystemBucket("tasks_cache", alias) {
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

    fun alias(alias: String) = TasksCacheBucket(alias)
}

class TransactionsBucket internal constructor(alias: String? = null) : SystemBucket("transactions", alias) {
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

    fun alias(alias: String) = TransactionsBucket(alias)
}

class SequencesBucket internal constructor(alias: String? = null) : SystemBucket("sequences", alias) {
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

    fun alias(alias: String) = SequencesBucket(alias)
}

class AllSequencesBucket internal constructor(alias: String? = null) : SystemBucket("all_sequences", alias) {
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

    fun alias(alias: String) = AllSequencesBucket(alias)
}

class AusBucket internal constructor(alias: String? = null) : SystemBucket("aus", alias) {
    val enable: IField<BooleanType> = booleanField("enable")
    val schedule: IField<ObjectType> = objectField("schedule")
    val changePercentage: IField<NumberType> = numberField("change_percentage")
    val allBuckets: IField<BooleanType> = booleanField("all_buckets")
    val createMissingStatistics: IField<BooleanType> = booleanField("create_missing_statistics")

    fun alias(alias: String) = AusBucket(alias)
}

class AusSettingsBucket internal constructor(alias: String? = null) : SystemBucket("aus_settings", alias) {
    val enable: IField<BooleanType> = booleanField("enable")
    val changePercentage: IField<NumberType> = numberField("change_percentage")
    val updateStatisticsTimeout: IField<NumberType> = numberField("update_statistics_timeout")

    fun alias(alias: String) = AusSettingsBucket(alias)
}
