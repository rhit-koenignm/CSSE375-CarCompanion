package com.example.carcompanion.ui

import com.example.carcompanion.ui.troubleshooting.TroubleshootingFlowController
import org.junit.Test

class TroubleshootingFlowControllerTests {

    @Test
    fun testBasic() {
        val controller = TroubleshootingFlowController()
        val machine = controller.machine
        assert(machine. == TroubleshootingFlowController.States.Start)
        machine.processEvent(TroubleshootingFlowController.Events.ResetEvent)
        assert(machine.state == TroubleshootingFlowController.States.Start)
    }
}