package ch.ergon.dope.couchbase.resolvable.keyspace

object SystemBuckets {
    val dataContainers: DataContainers = DataContainers()
    val monitoring: MonitoringCatalogs = MonitoringCatalogs()
    val security: SecurityCatalogs = SecurityCatalogs()
    val other: OtherCatalogs = OtherCatalogs()

    val datastores: DatastoresBucket get() = dataContainers.datastores
    val namespaces: NamespacesBucket get() = dataContainers.namespaces
    val buckets: BucketsBucket get() = dataContainers.buckets
    val scopes: ScopesBucket get() = dataContainers.scopes
    val keyspaces: KeyspacesBucket get() = dataContainers.keyspaces
    val indexes: IndexesBucket get() = dataContainers.indexes
    val dual: DualBucket get() = dataContainers.dual
    val groupInfo: GroupInfoBucket get() = dataContainers.groupInfo
    val bucketInfo: BucketInfoBucket get() = dataContainers.bucketInfo

    val vitals: VitalsBucket get() = monitoring.vitals
    val activeRequests: ActiveRequestsBucket get() = monitoring.activeRequests
    val prepareds: PreparedsBucket get() = monitoring.prepareds
    val completedRequests: CompletedRequestsBucket get() = monitoring.completedRequests
    val completedRequestsHistory: CompletedRequestsHistoryBucket get() = monitoring.completedRequestsHistory
    val awr: AwrBucket get() = monitoring.awr

    val myUserInfo: MyUserInfoBucket get() = security.myUserInfo
    val userInfo: UserInfoBucket get() = security.userInfo
    val nodes: NodesBucket get() = security.nodes
    val applicableRoles: ApplicableRolesBucket get() = security.applicableRoles

    val dictionary: DictionaryBucket get() = other.dictionary
    val dictionaryCache: DictionaryCacheBucket get() = other.dictionaryCache
    val functions: FunctionsBucket get() = other.functions
    val functionsCache: FunctionsCacheBucket get() = other.functionsCache
    val tasksCache: TasksCacheBucket get() = other.tasksCache
    val transactions: TransactionsBucket get() = other.transactions
    val sequences: SequencesBucket get() = other.sequences
    val allSequences: AllSequencesBucket get() = other.allSequences
    val aus: AusBucket get() = other.aus
    val ausSettings: AusSettingsBucket get() = other.ausSettings

    class DataContainers internal constructor() {
        val datastores: DatastoresBucket = DatastoresBucket()
        val namespaces: NamespacesBucket = NamespacesBucket()
        val buckets: BucketsBucket = BucketsBucket()
        val scopes: ScopesBucket = ScopesBucket()
        val keyspaces: KeyspacesBucket = KeyspacesBucket()
        val indexes: IndexesBucket = IndexesBucket()
        val dual: DualBucket = DualBucket()
        val groupInfo: GroupInfoBucket = GroupInfoBucket()
        val bucketInfo: BucketInfoBucket = BucketInfoBucket()
    }

    class MonitoringCatalogs internal constructor() {
        val vitals: VitalsBucket = VitalsBucket()
        val activeRequests: ActiveRequestsBucket = ActiveRequestsBucket()
        val prepareds: PreparedsBucket = PreparedsBucket()
        val completedRequests: CompletedRequestsBucket = CompletedRequestsBucket()
        val completedRequestsHistory: CompletedRequestsHistoryBucket = CompletedRequestsHistoryBucket()
        val awr: AwrBucket = AwrBucket()
    }

    class SecurityCatalogs internal constructor() {
        val myUserInfo: MyUserInfoBucket = MyUserInfoBucket()
        val userInfo: UserInfoBucket = UserInfoBucket()
        val nodes: NodesBucket = NodesBucket()
        val applicableRoles: ApplicableRolesBucket = ApplicableRolesBucket()
    }

    class OtherCatalogs internal constructor() {
        val dictionary: DictionaryBucket = DictionaryBucket()
        val dictionaryCache: DictionaryCacheBucket = DictionaryCacheBucket()
        val functions: FunctionsBucket = FunctionsBucket()
        val functionsCache: FunctionsCacheBucket = FunctionsCacheBucket()
        val tasksCache: TasksCacheBucket = TasksCacheBucket()
        val transactions: TransactionsBucket = TransactionsBucket()
        val sequences: SequencesBucket = SequencesBucket()
        val allSequences: AllSequencesBucket = AllSequencesBucket()
        val aus: AusBucket = AusBucket()
        val ausSettings: AusSettingsBucket = AusSettingsBucket()
    }
}
