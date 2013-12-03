package javaee6.web.service

import javax.ejb.{LocalBean, Stateless}

@Stateless
@LocalBean
class CalcService {
  def add(left: Int, right: Int): Int =
    left + right

  def multiply(left: Int, right: Int): Int =
    left * right
}
