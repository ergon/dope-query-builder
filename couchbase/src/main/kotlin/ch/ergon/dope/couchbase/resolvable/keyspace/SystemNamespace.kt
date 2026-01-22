package ch.ergon.dope.couchbase.resolvable.keyspace

class SystemNamespace internal constructor() {
    val dataContainers: DataContainers = DataContainers()
    val monitoring: MonitoringCatalogs = MonitoringCatalogs()
    val security: SecurityCatalogs = SecurityCatalogs()
    val other: OtherCatalogs = OtherCatalogs()

    val datastores: DatastoresKeyspace get() = dataContainers.datastores
    val namespaces: NamespacesKeyspace get() = dataContainers.namespaces
    val buckets: BucketsKeyspace get() = dataContainers.buckets
    val scopes: ScopesKeyspace get() = dataContainers.scopes
    val keyspaces: KeyspacesKeyspace get() = dataContainers.keyspaces
    val indexes: IndexesKeyspace get() = dataContainers.indexes
    val dual: DualKeyspace get() = dataContainers.dual
    val groupInfo: GroupInfoKeyspace get() = dataContainers.groupInfo
    val bucketInfo: BucketInfoKeyspace get() = dataContainers.bucketInfo

    val vitals: VitalsKeyspace get() = monitoring.vitals
    val activeRequests: ActiveRequestsKeyspace get() = monitoring.activeRequests
    val prepareds: PreparedsKeyspace get() = monitoring.prepareds
    val completedRequests: CompletedRequestsKeyspace get() = monitoring.completedRequests
    val completedRequestsHistory: CompletedRequestsHistoryKeyspace get() = monitoring.completedRequestsHistory
    val awr: AwrKeyspace get() = monitoring.awr

    val myUserInfo: MyUserInfoKeyspace get() = security.myUserInfo
    val userInfo: UserInfoKeyspace get() = security.userInfo
    val nodes: NodesKeyspace get() = security.nodes
    val applicableRoles: ApplicableRolesKeyspace get() = security.applicableRoles

    val dictionary: DictionaryKeyspace get() = other.dictionary
    val dictionaryCache: DictionaryCacheKeyspace get() = other.dictionaryCache
    val functions: FunctionsKeyspace get() = other.functions
    val functionsCache: FunctionsCacheKeyspace get() = other.functionsCache
    val tasksCache: TasksCacheKeyspace get() = other.tasksCache
    val transactions: TransactionsKeyspace get() = other.transactions
    val sequences: SequencesKeyspace get() = other.sequences
    val allSequences: AllSequencesKeyspace get() = other.allSequences
    val aus: AusKeyspace get() = other.aus
    val ausSettings: AusSettingsKeyspace get() = other.ausSettings

    class DataContainers internal constructor() {
        val datastores: DatastoresKeyspace = DatastoresKeyspace()
        val namespaces: NamespacesKeyspace = NamespacesKeyspace()
        val buckets: BucketsKeyspace = BucketsKeyspace()
        val scopes: ScopesKeyspace = ScopesKeyspace()
        val keyspaces: KeyspacesKeyspace = KeyspacesKeyspace()
        val indexes: IndexesKeyspace = IndexesKeyspace()
        val dual: DualKeyspace = DualKeyspace()
        val groupInfo: GroupInfoKeyspace = GroupInfoKeyspace()
        val bucketInfo: BucketInfoKeyspace = BucketInfoKeyspace()
    }

    class MonitoringCatalogs internal constructor() {
        val vitals: VitalsKeyspace = VitalsKeyspace()
        val activeRequests: ActiveRequestsKeyspace = ActiveRequestsKeyspace()
        val prepareds: PreparedsKeyspace = PreparedsKeyspace()
        val completedRequests: CompletedRequestsKeyspace = CompletedRequestsKeyspace()
        val completedRequestsHistory: CompletedRequestsHistoryKeyspace = CompletedRequestsHistoryKeyspace()
        val awr: AwrKeyspace = AwrKeyspace()
    }

    class SecurityCatalogs internal constructor() {
        val myUserInfo: MyUserInfoKeyspace = MyUserInfoKeyspace()
        val userInfo: UserInfoKeyspace = UserInfoKeyspace()
        val nodes: NodesKeyspace = NodesKeyspace()
        val applicableRoles: ApplicableRolesKeyspace = ApplicableRolesKeyspace()
    }

    class OtherCatalogs internal constructor() {
        val dictionary: DictionaryKeyspace = DictionaryKeyspace()
        val dictionaryCache: DictionaryCacheKeyspace = DictionaryCacheKeyspace()
        val functions: FunctionsKeyspace = FunctionsKeyspace()
        val functionsCache: FunctionsCacheKeyspace = FunctionsCacheKeyspace()
        val tasksCache: TasksCacheKeyspace = TasksCacheKeyspace()
        val transactions: TransactionsKeyspace = TransactionsKeyspace()
        val sequences: SequencesKeyspace = SequencesKeyspace()
        val allSequences: AllSequencesKeyspace = AllSequencesKeyspace()
        val aus: AusKeyspace = AusKeyspace()
        val ausSettings: AusSettingsKeyspace = AusSettingsKeyspace()
    }
}

fun system(): SystemNamespace = SystemNamespace()
