package org.kurron.tomcat

import org.apache.catalina.startup.Tomcat

/**
 * A Tomcat 7 implementation of the API.
 */
class Tomcat7xServer implements TomcatServer {
    final Tomcat tomcat
    def context
    private boolean stopped
    int port = 8080

    public Tomcat7xServer() {
        this.tomcat = new Tomcat()
    }

    @Override
    void setPort( int port ) {
        this.port = port
    }

    @Override
    void createLoader(ClassLoader classLoader) {
        Class webappLoader = classLoader.loadClass('org.apache.catalina.loader.WebappLoader')
        context.loader = webappLoader.newInstance(classLoader)
    }

    @Override
    void createContext(String fullContextPath, String webAppPath) {
        def context = tomcat.addWebapp(null, fullContextPath, webAppPath)
        context.unpackWAR = false
        this.context = context
    }

    @Override
    void start() {
        stopped = false
        tomcat.port = port
        tomcat.start()
    }

    @Override
    void stop() {
        stopped = true
        tomcat.stop()
        tomcat.destroy()
    }

    @Override
    boolean isStopped() {
        stopped
    }
}
