package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

/**
 * The singleton that has methods for accessing the database
 */
object WordCard extends WordCard with LongKeyedMetaMapper[WordCard] {
}

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class WordCard extends LongKeyedMapper[WordCard] with IdPK {
  def getSingleton = WordCard // what's the "meta" server

  object lastmod extends MappedDateTime(this)
  object rank extends MappedLong(this)
  object skip extends MappedLong(this)
  object front extends MappedText(this)
  object back extends MappedText(this)
}

