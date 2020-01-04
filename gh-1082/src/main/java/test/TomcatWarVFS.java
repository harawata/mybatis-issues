package test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.ibatis.io.DefaultVFS;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

public class TomcatWarVFS extends VFS {

  private static final Log log = LogFactory.getLog(TomcatWarVFS.class);
  private DefaultVFS delegate = new DefaultVFS();

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public List<String> list(URL url, String path) throws IOException {
    StringBuilder urlStr = new StringBuilder(url.toString());
    log.debug("URL is: " + urlStr);
    if (urlStr.indexOf("war:file:/") == 0) {
      // The WAR
      int index = urlStr.lastIndexOf(".war");
      if (index > -1) {
        // error
      }
      urlStr.setLength(index + 4);
      urlStr.delete(0, 4);
      return scanJar(urlStr, path);
    } else if (urlStr.indexOf("jar:war:file:/") == 0) {
      // A JAR inside WAR
      int index = urlStr.lastIndexOf(".jar");
      if (index > -1) {
        // error
      }
      urlStr.setLength(index + 4);
      urlStr.delete(0, 4);
      return scanJar(urlStr, path);
    } else {
      // unpackWars = true
      return delegate.list(url, path);
    }
  }

  protected List<String> scanJar(StringBuilder urlStr, String path) throws IOException {
    List<String> resources = new ArrayList<>();
    try (JarInputStream jarStream = new JarInputStream(new URL(urlStr.toString()).openStream())) {
      for (JarEntry entry; (entry = jarStream.getNextJarEntry()) != null;) {
        log.debug("Check entry: " + entry.getName());
        if (!entry.isDirectory()) {
          StringBuilder name = new StringBuilder(entry.getName());
          if (name.indexOf("WEB-INF/classes/") == 0) {
            name.delete(0, 16);
          }
          if (name.indexOf(path) == 0) {
            log.debug("Found resource: " + name);
            resources.add(name.toString());
          }
        }
      }
    }
    return resources;
  }
}
