package org.kurron.tomcat

/**
 * An abstract of a Tomcat server.
 */
public interface TomcatServer {
    void createLoader(ClassLoader classLoader)
    void createContext(String fullContextPath, String webAppPath)
    void start()
    void stop()
    void setPort( int port )
    boolean isStopped()
}