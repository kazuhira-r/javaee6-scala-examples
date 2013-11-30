package example

import java.io.IOException

import javax.servlet.ServletException
import javax.servlet.annotation.{WebServlet}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import org.infinispan.Cache
import org.infinispan.manager.{DefaultCacheManager, EmbeddedCacheManager}

@WebServlet(value = Array("/simple"))
class SimpleServlet extends HttpServlet {
  lazy val manager: EmbeddedCacheManager = new DefaultCacheManager
  lazy val cache: Cache[String, Int] = manager.getCache()

  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  override protected def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit = {
    if (cache.containsKey("counter")) {
      cache.put("counter", cache.get("counter") + 1)
    } else {
      cache.put("counter", 1)
    }

    res.getWriter().write(s"Hello! Simple Servlet, Counter[${cache.get("counter")}]")
  }

  override def destroy(): Unit = {
    try {
      cache.stop()
    } finally {
      manager.stop()
    }
  }
}
