package javaee6.web.stringinterpolation

object JpqlStringInterpolation {
  implicit class JpqlStringInterpolationWrapper(val sc: StringContext) extends AnyVal {
    def jpql(params: Any*): Jpql =
      Jpql.bind(sc.parts.map(StringContext.treatEscapes).mkString("?"))((1 to params.size).zip(params): _*)
  }
}
