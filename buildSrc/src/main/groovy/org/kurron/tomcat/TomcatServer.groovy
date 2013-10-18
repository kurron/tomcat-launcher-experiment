package org.kurron.tomcat

/**
 * An abstract of a Tomcat server.
 */
public interface TomcatServer {
    TomcatVersion getVersion()
    def getEmbedded()
    void setHome(String home)
    def getContext()
    void createContext(String fullContextPath, String webAppPath)
    void setConfigFile(URL configFile)
    void start()
    void stop()
    boolean isStopped()
}