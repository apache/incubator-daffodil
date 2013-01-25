package daffodil.section05.simple_types

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
import daffodil.debugger.Debugger

class TestSimpleTypes2 extends JUnitSuite {
  val testDir = "/daffodil/section05/simple_types/"
  val aa = testDir + "SimpleTypes.tdml"
  lazy val runner = new DFDLTestSuite(Misc.getRequiredResource(aa))

  @Test def test_warning_exercise() {
    val exc = intercept[Exception] {
      runner.runOneTest("warning_exercise")
    }
    assertTrue(exc.getMessage().contains("Did not find"))
  }
  //  @Test def test_Long3() {runner.runOneTest("Long3")}
  //  @Test def test_Long4() {runner.runOneTest("Long4")}

  @Test def test_dateTimeImplicitPattern() { runner.runOneTest("dateTimeImplicitPattern") }

  @Test def test_whiteSpaceBeforeValidInt() { runner.runOneTest("whiteSpaceBeforeValidInt") }
  @Test def test_whiteSpaceDuringValidInt() { runner.runOneTest("whiteSpaceDuringValidInt") }

  @Test def test_whiteSpaceBeforeValidLong() { runner.runOneTest("whiteSpaceBeforeValidLong") }
  @Test def test_whiteSpaceDuringValidLong() { runner.runOneTest("whiteSpaceDuringValidLong") }

  @Test def test_whiteSpaceBeforeValidShort() { runner.runOneTest("whiteSpaceBeforeValidShort") }
  @Test def test_whiteSpaceDuringValidShort() { runner.runOneTest("whiteSpaceDuringValidShort") }

  @Test def test_whiteSpaceBeforeValidUnsignedInt() { runner.runOneTest("whiteSpaceBeforeValidUnsignedInt") }
  @Test def test_whiteSpaceDuringValidUnsignedInt() { runner.runOneTest("whiteSpaceDuringValidUnsignedInt") }

  @Test def test_whiteSpaceBeforeValidUnsignedShort() { runner.runOneTest("whiteSpaceBeforeValidUnsignedShort") }
  @Test def test_whiteSpaceDuringValidUnsignedShort() { runner.runOneTest("whiteSpaceDuringValidUnsignedShort") }

  @Test def test_whiteSpaceBeforeValidUnsignedByte() { runner.runOneTest("whiteSpaceBeforeValidUnsignedByte") }
  @Test def test_whiteSpaceDuringValidUnsignedByte() { runner.runOneTest("whiteSpaceDuringValidUnsignedByte") }

  @Test def test_whiteSpaceBeforeValidValue() { runner.runOneTest("whiteSpaceBeforeValidValue") }
  @Test def test_whiteSpaceDuringValidValue() { runner.runOneTest("whiteSpaceDuringValidValue") }
  
  @Test def test_posinteger_binary_01() { runner.runOneTest("nonNegInt_binary_01") }

  @Test def test_hexBinary_01() { runner.runOneTest("hexBinary_01") }

  @Test def test_nonNegativeInteger_text() { runner.runOneTest("nonNegativeInteger_text") }
  @Test def test_nonNegativeInteger_bin() { runner.runOneTest("nonNegativeInteger_bin") }

}
