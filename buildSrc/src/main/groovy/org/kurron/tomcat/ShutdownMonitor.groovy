package org.kurron.tomcat

import groovy.util.logging.Slf4j

/**
 * Created with IntelliJ IDEA.
 * User: vagrant
 * Date: 10/16/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
class ShutdownMonitor extends Thread {
    final int port
    final String key
    final TomcatServer server
    final boolean daemon
    ServerSocket serverSocket

    public ShutdownMonitor(int port, String key, TomcatServer server, boolean daemon) {
        if(port <= 0) {
            throw new IllegalStateException('Bad stop port')
        }

        this.port = port
        this.key = key
        this.server = server
        this.daemon = daemon

        if(daemon) {
            setDaemon(true)
        }

        setName('TomcatPluginShutdownMonitor')
        serverSocket = new ServerSocket(port, 1, InetAddress.getByName('127.0.0.1'))
        serverSocket.reuseAddress = true
    }

    @Override
    void run() {
        Socket socket = serverSocket.accept()
        socket.setSoLinger(false, 0)
        socket.keepAlive = true

        while(!server.stopped ) {
            try {
                LineNumberReader lin = new LineNumberReader(new InputStreamReader(socket.inputStream))

                String keyCmd = lin.readLine()

                if(key && !(key == keyCmd)) {
                    continue
                }

                String cmd = lin.readLine()

                if('stop' == cmd) {
                    log.info 'Shutting down server'

                    try {
                        log.info 'Stopping server'
                        server.stop()
                    }
                    catch(Exception e) {
                        log.error 'Exception when stopping server', e
                    }
                }
            }
            catch(Exception e) {
                log.error 'Exception in shutdown monitor', e
                System.exit(1)
            }
        }

        try {
            socket.close()
            serverSocket.close()
        }
        catch(Exception e) {
            log.error 'Exception when stopping server', e
            System.exit(1)
        }
    }
}