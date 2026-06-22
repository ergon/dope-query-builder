package ch.ergon.dope.couchbase.resolvable.bucket

object SystemBuckets {
    val datastoresBucket: DatastoresBucket = DatastoresBucket()
    val namespacesBucket: NamespacesBucket = NamespacesBucket()
    val bucketsBucket: BucketsBucket = BucketsBucket()
    val scopesBucket: ScopesBucket = ScopesBucket()
    val keyspacesBucket: KeyspacesBucket = KeyspacesBucket()
    val indexesBucket: IndexesBucket = IndexesBucket()
    val dualBucket: DualBucket = DualBucket()
    val groupInfoBucket: GroupInfoBucket = GroupInfoBucket()
    val bucketInfoBucket: BucketInfoBucket = BucketInfoBucket()

    val vitalsBucket: VitalsBucket = VitalsBucket()
    val activeRequestsBucket: ActiveRequestsBucket = ActiveRequestsBucket()
    val preparedsBucket: PreparedsBucket = PreparedsBucket()
    val completedRequestsBucket: CompletedRequestsBucket = CompletedRequestsBucket()
    val completedRequestsHistoryBucket: CompletedRequestsHistoryBucket = CompletedRequestsHistoryBucket()
    val awrBucket: AwrBucket = AwrBucket()

    val myUserInfoBucket: MyUserInfoBucket = MyUserInfoBucket()
    val userInfoBucket: UserInfoBucket = UserInfoBucket()
    val nodesBucket: NodesBucket = NodesBucket()
    val applicableRolesBucket: ApplicableRolesBucket = ApplicableRolesBucket()

    val dictionaryBucket: DictionaryBucket = DictionaryBucket()
    val dictionaryCacheBucket: DictionaryCacheBucket = DictionaryCacheBucket()
    val functionsBucket: FunctionsBucket = FunctionsBucket()
    val functionsCacheBucket: FunctionsCacheBucket = FunctionsCacheBucket()
    val tasksCacheBucket: TasksCacheBucket = TasksCacheBucket()
    val transactionsBucket: TransactionsBucket = TransactionsBucket()
    val sequencesBucket: SequencesBucket = SequencesBucket()
    val allSequencesBucket: AllSequencesBucket = AllSequencesBucket()
    val ausBucket: AusBucket = AusBucket()
    val ausSettingsBucket: AusSettingsBucket = AusSettingsBucket()
}
