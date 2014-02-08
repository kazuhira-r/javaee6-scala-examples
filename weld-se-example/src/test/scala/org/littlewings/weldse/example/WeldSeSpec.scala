package org.littlewings.weldse.example

import javax.enterprise.inject.Instance

import org.jboss.weld.environment.se.{Weld, WeldContainer}

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class WeldExample extends FunSpec {
  describe("weld-se spec") {
    it("simple use") {
      val weld: Weld = new Weld
      val container: WeldContainer = weld.initialize()

      val instance: Instance[AnyRef] = container.instance
      val calcService = instance.select(classOf[CalcService]).get

      calcService.plus(1, 3) should be (4)

      weld.shutdown()
    }

    it("normal") {
      val weld: Weld = new Weld
      val container: WeldContainer = weld.initialize()

      val instance: Instance[AnyRef] = container.instance
      val calcService1 = instance.select(classOf[CalcService]).get
      val calcService2 = instance.select(classOf[CalcService]).get

      calcService1 eq calcService2 should be (false)

      weld.shutdown()
    }

    it("application-scoped") {
      val weld: Weld = new Weld
      val container: WeldContainer = weld.initialize()

      val dateService1 = container.instance.select(classOf[DateService]).get
      val dateService2 = container.instance.select(classOf[DateService]).get

      dateService1 should be theSameInstanceAs dateService2

      dateService1.time should be (dateService2.time)

      weld.shutdown()
    }

    it("@Inject resolved") {
      val weld: Weld = new Weld
      val container: WeldContainer = weld.initialize()

      val instance: Instance[AnyRef] = container.instance

      val facadeService = instance.select(classOf[FacadeService]).get
      val dateService = instance.select(classOf[DateService]).get

      facadeService.dateService should be theSameInstanceAs dateService

      facadeService.dateService.time should be (dateService.time)
      facadeService.calcService.plus(1, 5) should be (6)

      weld.shutdown()
    }
  }
}
