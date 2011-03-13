package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object WordCard extends WordCard with LongKeyedMetaMapper[WordCard] {
  def selectCard(u:User):Box[WordCard] = {
    val c = count(u)
    if(c==0) return Empty
    var r = 0
    while(true) {
      val i = Math.abs(scala.util.Random.nextLong())%c
      val wc = find(By(user,u),By(internalId,i)).get
      if(wc.rank.is <= r) return Full(wc)
      r = r + 1
    }
    return Empty
  }
  def addNewCard(u:User, f:String, b:String):WordCard = {
    create.user(u).rank(0).front(f).back(b).internalId(count(u)).saveMe()
  }
  def count(u:User):Long = {
    find(By(user,u),OrderBy(internalId,Descending)).dmap[Long](-1)(_.internalId.is)+1
  }
  override def beforeDelete = {wc:WordCard => wc.deleteInternalId} :: super.beforeDelete
}

class WordCard extends LongKeyedMapper[WordCard] with IdPK {
  def getSingleton = WordCard

  object user extends LongMappedMapper(this, User)
  object rank extends MappedLong(this)
  object internalId extends MappedLong(this)
  object front extends MappedText(this)
  object back extends MappedText(this)

  def incrRank():WordCard = {
    val r = rank.is
    rank(rank.is+1)
    saveMe()
  }
  def initRank():WordCard = {
    rank(0).saveMe()
  }

  def deleteInternalId():Unit = {
    WordCard.find(By(WordCard.user,user.is),OrderBy(WordCard.internalId,Descending)) match {
      case Full(c) if c != this => c.internalId(internalId.is).save()
      case _ =>
    }
  }
}
