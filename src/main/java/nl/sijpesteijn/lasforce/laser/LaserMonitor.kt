package nl.sijpesteijn.lasforce.laser

import javax.websocket.Session

/**
 * @author Gijs Sijpesteijn
 */
class LaserMonitor(session:Session, lasforceLaser: LasforceLaser) : Runnable {
    val monitor = lasforceLaser
    val session = session

    override fun run() {
        while(true) {
            if (monitor.isConnected()) {
                session.basicRemote.sendText(monitor.getStatus())
            } else {
                monitor.reConnect();
            }
            Thread.sleep(1000);
        }
    }

}