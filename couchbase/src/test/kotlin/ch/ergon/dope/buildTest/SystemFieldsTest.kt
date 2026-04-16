package ch.ergon.dope.buildTest

import ch.ergon.dope.QueryBuilder
import ch.ergon.dope.couchbase.resolvable.keyspace.SystemBuckets
import ch.ergon.dope.couchbase.resolver.CouchbaseResolver
import kotlin.test.Test
import kotlin.test.assertEquals

class SystemFieldsTest {
    @Test
    fun `should select all fields from datastores`() {
        val b = SystemBuckets.datastoresBucket
        val expected = "SELECT `datastores`.`id`, `datastores`.`url` FROM `system`:`datastores`"

        val actual = QueryBuilder
            .select(b.id, b.url)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from namespaces`() {
        val b = SystemBuckets.namespacesBucket
        val expected = "SELECT `namespaces`.`id`, `namespaces`.`name`, `namespaces`.`datastore_id` FROM `system`:`namespaces`"

        val actual = QueryBuilder
            .select(b.id, b.namespaceName, b.datastoreId)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from buckets`() {
        val b = SystemBuckets.bucketsBucket
        val expected = "SELECT `buckets`.`datastore_id`, `buckets`.`name`, `buckets`.`namespace`, " +
            "`buckets`.`namespace_id`, `buckets`.`path` FROM `system`:`buckets`"

        val actual = QueryBuilder
            .select(b.datastoreId, b.bucketName, b.namespace, b.namespaceId, b.path)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from scopes`() {
        val b = SystemBuckets.scopesBucket
        val expected = "SELECT `scopes`.`bucket`, `scopes`.`datastore_id`, `scopes`.`name`, " +
            "`scopes`.`namespace`, `scopes`.`namespace_id`, `scopes`.`path` FROM `system`:`scopes`"

        val actual = QueryBuilder
            .select(b.bucketName, b.datastoreId, b.scopeName, b.namespace, b.namespaceId, b.path)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from keyspaces`() {
        val b = SystemBuckets.keyspacesBucket
        val expected = "SELECT `keyspaces`.`bucket`, `keyspaces`.`datastore_id`, `keyspaces`.`id`, " +
            "`keyspaces`.`name`, `keyspaces`.`namespace`, `keyspaces`.`namespace_id`, " +
            "`keyspaces`.`path`, `keyspaces`.`scope` FROM `system`:`keyspaces`"

        val actual = QueryBuilder
            .select(b.bucketName, b.datastoreId, b.id, b.keyspaceName, b.namespace, b.namespaceId, b.path, b.scopeField)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from indexes`() {
        val b = SystemBuckets.indexesBucket
        val expected = "SELECT `indexes`.`bucket_id`, `indexes`.`condition`, `indexes`.`datastore_id`, " +
            "`indexes`.`id`, `indexes`.`index_key`, `indexes`.`is_primary`, `indexes`.`keyspace_id`, " +
            "`indexes`.`name`, `indexes`.`metadata`, `indexes`.`namespace_id`, `indexes`.`state`, " +
            "`indexes`.`using` FROM `system`:`indexes`"

        val actual = QueryBuilder
            .select(
                b.bucketId, b.condition, b.datastoreId, b.id, b.indexKey, b.isPrimary,
                b.keyspaceId, b.indexName, b.metadata, b.namespaceId, b.state, b.using,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from group_info`() {
        val b = SystemBuckets.groupInfoBucket
        val expected = "SELECT `group_info`.`description`, `group_info`.`id`, " +
            "`group_info`.`ldap_group_ref`, `group_info`.`roles` FROM `system`:`group_info`"

        val actual = QueryBuilder
            .select(b.description, b.id, b.ldapGroupRef, b.roles)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from vitals`() {
        val b = SystemBuckets.vitalsBucket
        val expected = "SELECT `vitals`.`uptime`, `vitals`.`local.time`, `vitals`.`version`, " +
            "`vitals`.`total.threads`, `vitals`.`cores`, `vitals`.`gc.num`, " +
            "`vitals`.`gc.pause.time`, `vitals`.`gc.pause.percent`, `vitals`.`memory.usage`, " +
            "`vitals`.`memory.total`, `vitals`.`memory.system`, `vitals`.`cpu.user.percent`, " +
            "`vitals`.`cpu.sys.percent`, `vitals`.`request.completed.count`, " +
            "`vitals`.`request.active.count`, `vitals`.`request.per.sec.1min`, " +
            "`vitals`.`request.per.sec.5min`, `vitals`.`request.per.sec.15min`, " +
            "`vitals`.`request_time.mean`, `vitals`.`request_time.median`, " +
            "`vitals`.`request_time.80percentile`, `vitals`.`request_time.95percentile`, " +
            "`vitals`.`request_time.99percentile`, `vitals`.`request.prepared.percent`, " +
            "`vitals`.`awr` FROM `system`:`vitals`"

        val actual = QueryBuilder
            .select(
                b.uptime, b.localTime, b.version, b.totalThreads, b.cores, b.gcNum,
                b.gcPauseTime, b.gcPausePercent, b.memoryUsage, b.memoryTotal, b.memorySystem,
                b.cpuUserPercent, b.cpuSysPercent, b.requestCompletedCount, b.requestActiveCount,
                b.requestPerSec1min, b.requestPerSec5min, b.requestPerSec15min,
                b.requestTimeMean, b.requestTimeMedian, b.requestTime80percentile,
                b.requestTime95percentile, b.requestTime99percentile, b.requestPreparedPercent,
                b.awr,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from active_requests`() {
        val b = SystemBuckets.activeRequestsBucket
        val expected = "SELECT `active_requests`.`clientContextID`, `active_requests`.`cpuTime`, " +
            "`active_requests`.`elapsedTime`, `active_requests`.`executionTime`, " +
            "`active_requests`.`ioTime`, `active_requests`.`memoryQuota`, " +
            "`active_requests`.`n1qlFeatCtrl`, `active_requests`.`node`, " +
            "`active_requests`.`phaseOperators`, `active_requests`.`phaseTimes`, " +
            "`active_requests`.`queryContext`, `active_requests`.`remoteAddr`, " +
            "`active_requests`.`requestId`, `active_requests`.`requestTime`, " +
            "`active_requests`.`scanConsistency`, `active_requests`.`state`, " +
            "`active_requests`.`statement`, `active_requests`.`statementType`, " +
            "`active_requests`.`useCBO`, `active_requests`.`userAgent`, " +
            "`active_requests`.`users`, `active_requests`.`waitTime` FROM `system`:`active_requests`"

        val actual = QueryBuilder
            .select(
                b.clientContextID, b.cpuTime, b.elapsedTime, b.executionTime, b.ioTime,
                b.memoryQuota, b.n1qlFeatCtrl, b.node, b.phaseOperators, b.phaseTimes,
                b.queryContext, b.remoteAddr, b.requestId, b.requestTime, b.scanConsistency,
                b.state, b.statement, b.statementType, b.useCBO, b.userAgent, b.users, b.waitTime,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from prepareds`() {
        val b = SystemBuckets.preparedsBucket
        val expected = "SELECT `prepareds`.`encoded_plan`, `prepareds`.`featuresControl`, " +
            "`prepareds`.`indexApiVersion`, `prepareds`.`indexScanKeyspaces`, " +
            "`prepareds`.`name`, `prepareds`.`namespace`, `prepareds`.`node`, " +
            "`prepareds`.`statement`, `prepareds`.`uses` FROM `system`:`prepareds`"

        val actual = QueryBuilder
            .select(
                b.encodedPlan, b.featuresControl, b.indexApiVersion, b.indexScanKeyspaces,
                b.preparedStatementName, b.namespace, b.node, b.statement, b.uses,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from completed_requests`() {
        val b = SystemBuckets.completedRequestsBucket
        val expected = "SELECT `completed_requests`.`clientContextID`, `completed_requests`.`cpuTime`, " +
            "`completed_requests`.`elapsedTime`, `completed_requests`.`errorCount`, " +
            "`completed_requests`.`errors`, `completed_requests`.`ioTime`, " +
            "`completed_requests`.`memoryQuota`, `completed_requests`.`n1qlFeatCtrl`, " +
            "`completed_requests`.`namedArgs`, `completed_requests`.`phaseCounts`, " +
            "`completed_requests`.`phaseOperators`, `completed_requests`.`phaseTimes`, " +
            "`completed_requests`.`queryContext`, `completed_requests`.`remoteAddr`, " +
            "`completed_requests`.`requestId`, `completed_requests`.`requestTime`, " +
            "`completed_requests`.`resultCount`, `completed_requests`.`resultSize`, " +
            "`completed_requests`.`scanConsistency`, `completed_requests`.`serviceTime`, " +
            "`completed_requests`.`sqlID`, `completed_requests`.`sessionMemory`, " +
            "`completed_requests`.`state`, `completed_requests`.`statement`, " +
            "`completed_requests`.`statementType`, `completed_requests`.`useCBO`, " +
            "`completed_requests`.`usedMemory`, `completed_requests`.`userAgent`, " +
            "`completed_requests`.`users`, `completed_requests`.`waitTime`, " +
            "`completed_requests`.`~analysis`, `completed_requests`.`~qualifier` " +
            "FROM `system`:`completed_requests`"

        val actual = QueryBuilder
            .select(
                b.clientContextID, b.cpuTime, b.elapsedTime, b.errorCount, b.errors,
                b.ioTime, b.memoryQuota, b.n1qlFeatCtrl, b.namedArgs, b.phaseCounts,
                b.phaseOperators, b.phaseTimes, b.queryContext, b.remoteAddr, b.requestId,
                b.requestTime, b.resultCount, b.resultSize, b.scanConsistency, b.serviceTime,
                b.sqlID, b.sessionMemory, b.state, b.statement, b.statementType, b.useCBO,
                b.usedMemory, b.userAgent, b.users, b.waitTime, b.analysis, b.qualifier,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from completed_requests_history`() {
        val b = SystemBuckets.completedRequestsHistoryBucket
        val expected = "SELECT `completed_requests_history`.`clientContextID`, `completed_requests_history`.`cpuTime`, " +
            "`completed_requests_history`.`elapsedTime`, `completed_requests_history`.`errorCount`, " +
            "`completed_requests_history`.`errors`, `completed_requests_history`.`ioTime`, " +
            "`completed_requests_history`.`memoryQuota`, `completed_requests_history`.`n1qlFeatCtrl`, " +
            "`completed_requests_history`.`namedArgs`, `completed_requests_history`.`phaseCounts`, " +
            "`completed_requests_history`.`phaseOperators`, `completed_requests_history`.`phaseTimes`, " +
            "`completed_requests_history`.`queryContext`, `completed_requests_history`.`remoteAddr`, " +
            "`completed_requests_history`.`requestId`, `completed_requests_history`.`requestTime`, " +
            "`completed_requests_history`.`resultCount`, `completed_requests_history`.`resultSize`, " +
            "`completed_requests_history`.`scanConsistency`, `completed_requests_history`.`serviceTime`, " +
            "`completed_requests_history`.`sqlID`, `completed_requests_history`.`sessionMemory`, " +
            "`completed_requests_history`.`state`, `completed_requests_history`.`statement`, " +
            "`completed_requests_history`.`statementType`, `completed_requests_history`.`useCBO`, " +
            "`completed_requests_history`.`usedMemory`, `completed_requests_history`.`userAgent`, " +
            "`completed_requests_history`.`users`, `completed_requests_history`.`waitTime`, " +
            "`completed_requests_history`.`~analysis`, `completed_requests_history`.`~qualifier` " +
            "FROM `system`:`completed_requests_history`"

        val actual = QueryBuilder
            .select(
                b.clientContextID, b.cpuTime, b.elapsedTime, b.errorCount, b.errors,
                b.ioTime, b.memoryQuota, b.n1qlFeatCtrl, b.namedArgs, b.phaseCounts,
                b.phaseOperators, b.phaseTimes, b.queryContext, b.remoteAddr, b.requestId,
                b.requestTime, b.resultCount, b.resultSize, b.scanConsistency, b.serviceTime,
                b.sqlID, b.sessionMemory, b.state, b.statement, b.statementType, b.useCBO,
                b.usedMemory, b.userAgent, b.users, b.waitTime, b.analysis, b.qualifier,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from awr`() {
        val b = SystemBuckets.awrBucket
        val expected = "SELECT `awr`.`enabled`, `awr`.`interval`, `awr`.`location`, " +
            "`awr`.`num_statements`, `awr`.`queue_len`, `awr`.`threshold` FROM `system`:`awr`"

        val actual = QueryBuilder
            .select(b.enabled, b.interval, b.location, b.numStatements, b.queueLen, b.threshold)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from my_user_info`() {
        val b = SystemBuckets.myUserInfoBucket
        val expected = "SELECT `my_user_info`.`domain`, `my_user_info`.`external_groups`, " +
            "`my_user_info`.`groups`, `my_user_info`.`id`, `my_user_info`.`name`, " +
            "`my_user_info`.`password_change_date`, `my_user_info`.`roles` FROM `system`:`my_user_info`"

        val actual = QueryBuilder
            .select(b.domain, b.externalGroups, b.groups, b.id, b.userName, b.passwordChangeDate, b.roles)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from user_info`() {
        val b = SystemBuckets.userInfoBucket
        val expected = "SELECT `user_info`.`domain`, `user_info`.`external_groups`, " +
            "`user_info`.`groups`, `user_info`.`id`, `user_info`.`name`, " +
            "`user_info`.`password_change_date`, `user_info`.`roles` FROM `system`:`user_info`"

        val actual = QueryBuilder
            .select(b.domain, b.externalGroups, b.groups, b.id, b.userName, b.passwordChangeDate, b.roles)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from nodes`() {
        val b = SystemBuckets.nodesBucket
        val expected = "SELECT `nodes`.`name`, `nodes`.`ports`, `nodes`.`services` FROM `system`:`nodes`"

        val actual = QueryBuilder
            .select(b.nodeName, b.ports, b.services)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from applicable_roles`() {
        val b = SystemBuckets.applicableRolesBucket
        val expected = "SELECT `applicable_roles`.`bucket_name`, `applicable_roles`.`scope_name`, " +
            "`applicable_roles`.`collection_name`, `applicable_roles`.`grantee`, " +
            "`applicable_roles`.`role` FROM `system`:`applicable_roles`"

        val actual = QueryBuilder
            .select(b.bucketName, b.scopeField, b.collectionField, b.grantee, b.role)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from dictionary`() {
        val b = SystemBuckets.dictionaryBucket
        val expected = "SELECT `dictionary`.`avgDocKeySize`, `dictionary`.`avgDocSize`, " +
            "`dictionary`.`bucket`, `dictionary`.`distributionKeys`, `dictionary`.`docCount`, " +
            "`dictionary`.`indexes`, `dictionary`.`keyspace`, `dictionary`.`namespace`, " +
            "`dictionary`.`node`, `dictionary`.`scope` FROM `system`:`dictionary`"

        val actual = QueryBuilder
            .select(
                b.avgDocKeySize, b.avgDocSize, b.bucketName, b.distributionKeys, b.docCount,
                b.indexes, b.keyspaceName, b.namespace, b.node, b.scopeField,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from dictionary_cache`() {
        val b = SystemBuckets.dictionaryCacheBucket
        val expected = "SELECT `dictionary_cache`.`avgDocKeySize`, `dictionary_cache`.`avgDocSize`, " +
            "`dictionary_cache`.`bucket`, `dictionary_cache`.`distributionKeys`, `dictionary_cache`.`docCount`, " +
            "`dictionary_cache`.`indexes`, `dictionary_cache`.`keyspace`, `dictionary_cache`.`namespace`, " +
            "`dictionary_cache`.`node`, `dictionary_cache`.`scope` FROM `system`:`dictionary_cache`"

        val actual = QueryBuilder
            .select(
                b.avgDocKeySize, b.avgDocSize, b.bucketName, b.distributionKeys, b.docCount,
                b.indexes, b.keyspaceName, b.namespace, b.node, b.scopeField,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from functions`() {
        val b = SystemBuckets.functionsBucket
        val expected = "SELECT `functions`.`definition`, `functions`.`identity` FROM `system`:`functions`"

        val actual = QueryBuilder
            .select(b.definition, b.identity)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from functions_cache`() {
        val b = SystemBuckets.functionsCacheBucket
        val expected = "SELECT `functions_cache`.`#language`, `functions_cache`.`avgServiceTime`, " +
            "`functions_cache`.`lastUse`, `functions_cache`.`maxServiceTime`, " +
            "`functions_cache`.`minServiceTime`, `functions_cache`.`name`, " +
            "`functions_cache`.`namespace`, `functions_cache`.`node`, " +
            "`functions_cache`.`parameters`, `functions_cache`.`type`, " +
            "`functions_cache`.`scope`, `functions_cache`.`expression`, " +
            "`functions_cache`.`text`, `functions_cache`.`library`, " +
            "`functions_cache`.`object`, `functions_cache`.`undefined_function`, " +
            "`functions_cache`.`uses` FROM `system`:`functions_cache`"

        val actual = QueryBuilder
            .select(
                b.language, b.avgServiceTime, b.lastUse, b.maxServiceTime, b.minServiceTime,
                b.functionName, b.namespace, b.node, b.parameters, b.type, b.scopeField,
                b.expression, b.text, b.library, b.objectName, b.undefinedFunction, b.uses,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from tasks_cache`() {
        val b = SystemBuckets.tasksCacheBucket
        val expected = "SELECT `tasks_cache`.`class`, `tasks_cache`.`delay`, `tasks_cache`.`id`, " +
            "`tasks_cache`.`name`, `tasks_cache`.`node`, `tasks_cache`.`state`, " +
            "`tasks_cache`.`subClass`, `tasks_cache`.`submitTime`, `tasks_cache`.`results`, " +
            "`tasks_cache`.`startTime`, `tasks_cache`.`stopTime` FROM `system`:`tasks_cache`"

        val actual = QueryBuilder
            .select(
                b.taskClass, b.delay, b.id, b.taskName, b.node, b.state,
                b.subClass, b.submitTime, b.results, b.startTime, b.stopTime,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from transactions`() {
        val b = SystemBuckets.transactionsBucket
        val expected = "SELECT `transactions`.`durabilityLevel`, `transactions`.`durabilityTimeout`, " +
            "`transactions`.`expiryTime`, `transactions`.`id`, `transactions`.`isolationLevel`, " +
            "`transactions`.`lastUse`, `transactions`.`node`, `transactions`.`numAtrs`, " +
            "`transactions`.`scanConsistency`, `transactions`.`status`, `transactions`.`timeout`, " +
            "`transactions`.`usedMemory`, `transactions`.`uses` FROM `system`:`transactions`"

        val actual = QueryBuilder
            .select(
                b.durabilityLevel, b.durabilityTimeout, b.expiryTime, b.id, b.isolationLevel,
                b.lastUse, b.node, b.numAtrs, b.scanConsistency, b.status, b.timeout,
                b.usedMemory, b.uses,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from sequences`() {
        val b = SystemBuckets.sequencesBucket
        val expected = "SELECT `sequences`.`bucket`, `sequences`.`cache`, `sequences`.`cycle`, " +
            "`sequences`.`increment`, `sequences`.`max`, `sequences`.`min`, `sequences`.`name`, " +
            "`sequences`.`namespace`, `sequences`.`namespace_id`, `sequences`.`path`, " +
            "`sequences`.`scope_id`, `sequences`.`value` FROM `system`:`sequences`"

        val actual = QueryBuilder
            .select(
                b.bucketName, b.cache, b.cycle, b.increment, b.max, b.min,
                b.sequenceName, b.namespace, b.namespaceId, b.path, b.scopeId, b.value,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from all_sequences`() {
        val b = SystemBuckets.allSequencesBucket
        val expected = "SELECT `all_sequences`.`bucket`, `all_sequences`.`cache`, `all_sequences`.`cycle`, " +
            "`all_sequences`.`increment`, `all_sequences`.`max`, `all_sequences`.`min`, `all_sequences`.`name`, " +
            "`all_sequences`.`namespace`, `all_sequences`.`namespace_id`, `all_sequences`.`path`, " +
            "`all_sequences`.`scope_id`, `all_sequences`.`value` FROM `system`:`all_sequences`"

        val actual = QueryBuilder
            .select(
                b.bucketName, b.cache, b.cycle, b.increment, b.max, b.min,
                b.sequenceName, b.namespace, b.namespaceId, b.path, b.scopeId, b.value,
            )
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from aus`() {
        val b = SystemBuckets.ausBucket
        val expected = "SELECT `aus`.`enable`, `aus`.`schedule`, `aus`.`change_percentage`, " +
            "`aus`.`all_buckets`, `aus`.`create_missing_statistics` FROM `system`:`aus`"

        val actual = QueryBuilder
            .select(b.enable, b.schedule, b.changePercentage, b.allBuckets, b.createMissingStatistics)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }

    @Test
    fun `should select all fields from aus_settings`() {
        val b = SystemBuckets.ausSettingsBucket
        val expected = "SELECT `aus_settings`.`enable`, `aus_settings`.`change_percentage`, " +
            "`aus_settings`.`update_statistics_timeout` FROM `system`:`aus_settings`"

        val actual = QueryBuilder
            .select(b.enable, b.changePercentage, b.updateStatisticsTimeout)
            .from(b)
            .build(CouchbaseResolver())
            .queryString

        assertEquals(expected, actual)
    }
}
