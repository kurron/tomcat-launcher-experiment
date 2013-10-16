package org.kurron.tomcat

import groovy.util.logging.Slf4j

/**
 * Embedded Tomcat 7 WAR launcher.
 */
@Slf4j
class Launcher {
    public static void main(String[] args) {
        TomcatServer server = new Tomcat7xServer()
        String path = new File( 'rest-services-1.0.0-SNAPSHOT.war' ).canonicalPath
        log.info( 'path = {}', path)
        server.createContext( '/rest-services', path )
        server.start()
        boolean daemon = false

        Thread shutdownMonitor = new ShutdownMonitor( 8081, 'stop-key', server, daemon)
        shutdownMonitor.start()

        if(!daemon) {
            shutdownMonitor.join()
        }
    }
}
