package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object WordCard extends WordCard with LongKeyedMetaMapper[WordCard]

class WordCard extends LongKeyedMapper[WordCard] with IdPK {
  def getSingleton = WordCard

  object lastmod extends MappedDateTime(this)
  object rank extends MappedLong(this)
  object skip extends MappedLong(this)
  object front extends MappedText(this)
  object back extends MappedText(this)
  object user extends LongMappedMapper(this, User)
}
