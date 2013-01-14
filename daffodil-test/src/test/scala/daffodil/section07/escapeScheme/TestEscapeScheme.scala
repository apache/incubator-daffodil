package daffodil.section07.escapeScheme

import junit.framework.Assert._
import org.scalatest.junit.JUnitSuite
import org.junit.Test
import scala.xml._
import daffodil.xml.XMLUtils
import daffodil.xml.XMLUtils._
import daffodil.compiler.Compiler
import daffodil.util._
import daffodil.tdml.DFDLTestSuite
import java.io.File

class TestEscapeScheme extends JUnitSuite {
  val testDir = "/daffodil/section07/escapeScheme/"
  val tdml = testDir + "escapeScheme.tdml"
  lazy val runner = new DFDLTestSuite(Misc.getRequiredResource(tdml),
    validateTDMLFile = false)

  @Test def test_escape_scheme() { runner.runOneTest("escapeSchemeSimple") }
  @Test def test_escapeSchemeFail() { runner.runOneTest("escapeSchemeFail") }
  @Test def test_escapeSchemeFail2() { runner.runOneTest("escapeSchemeFail2") }
  @Test def test_escapeSchemeFail3() { runner.runOneTest("escapeSchemeFail3") }
  @Test def test_escapeSchemeEmpty() { runner.runOneTest("escapeSchemeEmpty") }
  @Test def test_escapeSchemeNonEmpty() { runner.runOneTest("escapeSchemeNonEmpty") }

  val tdmlNeg = testDir + "escapeSchemeNeg.tdml"
  lazy val runnerNeg = new DFDLTestSuite(Misc.getRequiredResource(tdmlNeg),
    validateTDMLFile = false)

  @Test def test_escapeSchemeNeg() { runnerNeg.runOneTest("escapeSchemeNeg") }

}
