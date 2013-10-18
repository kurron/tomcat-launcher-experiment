package org.kurron.tomcat

/**
 * An abstract of a Tomcat server.
 */
public interface TomcatServer {
    void createLoader(ClassLoader classLoader)
    TomcatVersion getVersion()
    def getEmbedded()
    void setHome(String home)
    def getContext()
    void createContext(String fullContextPath, String webAppPath)
    void setConfigFile(URL configFile)
    void start()
    void stop()
    void setPort( int port )
    boolean isStopped()
}