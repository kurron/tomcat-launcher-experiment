package org.kurron.tomcat

import org.apache.catalina.startup.Tomcat

/**
 * A Tomcat 7 implementation of the API.
 */
class Tomcat7xServer implements TomcatServer {
    final Tomcat tomcat
    def context
    private boolean stopped

    public Tomcat7xServer() {
        this.tomcat = new Tomcat()
    }

    @Override
    void createLoader(ClassLoader classLoader) {
        Class webappLoader = classLoader.loadClass('org.apache.catalina.loader.WebappLoader')
        context.loader = webappLoader.newInstance(classLoader)
    }

    @Override
    TomcatVersion getVersion() {
        TomcatVersion.VERSION_7X
    }

    @Override
    def getEmbedded() {
        tomcat
    }

    @Override
    void setHome(String home) {
        tomcat.baseDir = home
    }

    @Override
    def getContext() {
        context
    }

    @Override
    void createContext(String fullContextPath, String webAppPath) {
        def context = tomcat.addWebapp(null, fullContextPath, webAppPath)
        context.unpackWAR = false
        this.context = context
    }

    @Override
    void setConfigFile(URL configFile) {
        if(configFile) {
            context.configFile = configFile
        }
    }

    @Override
    void start() {
        stopped = false
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
