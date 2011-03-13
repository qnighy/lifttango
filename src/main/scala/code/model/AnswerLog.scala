package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object AnswerLog extends AnswerLog with LongKeyedMetaMapper[AnswerLog]

class AnswerLog extends LongKeyedMapper[AnswerLog] with IdPK {
  def getSingleton = AnswerLog

  object time extends MappedDateTime(this) {
    override def defaultValue = new java.util.Date()
  }
  object user extends LongMappedMapper(this, User)
  object front extends MappedText(this)
  object back extends MappedText(this)
  object result extends MappedBoolean(this)

  def setCard(c:WordCard):AnswerLog = {
    user(c.user.obj).front(c.front.is).back(c.back.is)
  }
}

