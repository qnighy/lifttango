package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object AnswerLog extends AnswerLog with LongKeyedMetaMapper[AnswerLog]

class AnswerLog extends LongKeyedMapper[AnswerLog] with IdPK {
  def getSingleton = AnswerLog

  object time extends MappedDateTime(this)
  object user extends LongMappedMapper(this, User)
  object card extends LongMappedMapper(this, WordCard)
  object result extends MappedBoolean(this)
}

