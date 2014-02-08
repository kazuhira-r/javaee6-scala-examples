package org.littlewings.weldse.example

import javax.inject.Inject

class FacadeService {
  @Inject
  var calcService: CalcService = _

  @Inject
  var dateService: DateService = _
}
