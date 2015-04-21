/* Copyright (c) 2012-2015 Tresys Technology, LLC. All rights reserved.
 *
 * Developed by: Tresys Technology, LLC
 *               http://www.tresys.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal with
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 *  1. Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimers.
 * 
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimers in the
 *     documentation and/or other materials provided with the distribution.
 * 
 *  3. Neither the names of Tresys Technology, nor the names of its contributors
 *     may be used to endorse or promote products derived from this Software
 *     without specific prior written permission.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE
 * SOFTWARE.
 */

package edu.illinois.ncsa.daffodil.processors

import edu.illinois.ncsa.daffodil.grammar.Terminal
import edu.illinois.ncsa.daffodil.dsom._
import edu.illinois.ncsa.daffodil.schema.annotation.props.gen.{ AlignmentUnits, LengthKind, TextTrimKind, TextNumberJustification, TextStringJustification, TextCalendarJustification, TextBooleanJustification }
import edu.illinois.ncsa.daffodil.compiler.DaffodilTunableParameters
import edu.illinois.ncsa.daffodil.processors.{ Parser => DaffodilParser }
import edu.illinois.ncsa.daffodil.exceptions.Assert
import edu.illinois.ncsa.daffodil.util.Maybe
import edu.illinois.ncsa.daffodil.util.Maybe._
import edu.illinois.ncsa.daffodil.processors.parsers.LeadingSkipRegionParser
import edu.illinois.ncsa.daffodil.processors.parsers.TrailingSkipRegionParser
import edu.illinois.ncsa.daffodil.processors.parsers.AlignmentFillParser
import edu.illinois.ncsa.daffodil.dpath.NodeInfo.PrimType
import edu.illinois.ncsa.daffodil.schema.annotation.props.gen.TextPadKind

case class LeadingSkipRegion(e: Term) extends Terminal(e, true) {
  e.schemaDefinitionUnless(e.leadingSkip < DaffodilTunableParameters.maxSkipLength,
    "Property leadingSkip %s is larger than limit %s", e.leadingSkip, DaffodilTunableParameters.maxSkipLength)

  val alignment = e.alignmentUnits match {
    case AlignmentUnits.Bits => 1
    case AlignmentUnits.Bytes => 8
    case _ => 0 //SDE("Skip/Alignment values must have length units of Bits or Bytes.")
  }

  def parser: DaffodilParser = new LeadingSkipRegionParser(alignment, e.leadingSkip, e.runtimeData)
}

case class TrailingSkipRegion(e: Term) extends Terminal(e, true) {
  e.schemaDefinitionUnless(e.trailingSkip < DaffodilTunableParameters.maxSkipLength,
    "Property trailingSkip %s is larger than limit %s", e.trailingSkip, DaffodilTunableParameters.maxSkipLength)

  val lengthKindContext = e match {
    case eb: ElementBase => eb
    case _ => {
      Assert.invariant(e.nearestEnclosingElement != None) //root element is an ElementBase, all others have a nearestEnclosingElement
      e.nearestEnclosingElement.get
    }
  }
  e.schemaDefinitionWhen(lengthKindContext.lengthKind == LengthKind.Delimited && e.terminator.isConstant && e.terminator.constantAsString == "",
    "Property terminator must be defined when trailingSkip > 0 and lengthKind='delimited'")

  val alignment = e.alignmentUnits match {
    case AlignmentUnits.Bits => 1
    case AlignmentUnits.Bytes => 8
    case _ => 0 //SDE("Skip/Alignment values must have lenght units of Bits or Bytes")
  }

  def parser: Parser = new TrailingSkipRegionParser(alignment, e.trailingSkip, e.runtimeData)
}

case class AlignmentFill(e: Term) extends Terminal(e, true) {

  val alignment = e.alignmentValueInBits

  def isAligned(currBitPos: Long): Boolean = {
    if (alignment == 0 || currBitPos == 0) return true
    if ((currBitPos - alignment) < 0) return false
    if ((currBitPos % alignment) == 0) return true
    return false
  }

  def parser: Parser = new AlignmentFillParser(e.alignment, alignment, e.runtimeData)
}

case class FinalUnusedRegion(e: ElementBase) extends Primitive(e, false)

