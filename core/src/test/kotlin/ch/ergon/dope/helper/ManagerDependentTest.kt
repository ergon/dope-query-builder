package ch.ergon.dope.helper

import ch.ergon.dope.DopeQueryManager
import org.junit.jupiter.api.BeforeEach

interface ManagerDependentTest {
    var manager: DopeQueryManager

    @BeforeEach
    fun setUp() {
        manager = DopeQueryManager()
    }
}
