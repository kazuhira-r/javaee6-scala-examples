package org.littlewings.javaee6.service

import java.util.Date

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DateService {
  private[this] val thisTime: Date = new Date

  def time: Date = thisTime
}
