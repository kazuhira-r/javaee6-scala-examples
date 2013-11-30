package javaee6.web.filter

import javax.servlet.{Filter, FilterChain, FilterConfig, ServletRequest, ServletResponse}
import javax.servlet.annotation.WebFilter

@WebFilter(Array("/*"))
class EncodingFilter extends Filter {
  override def init(filterConfig: FilterConfig): Unit = ()

  override def destroy(): Unit = ()

  override def doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain): Unit = {
    req.setCharacterEncoding("UTF-8")
    chain.doFilter(req, res)
  }
}
