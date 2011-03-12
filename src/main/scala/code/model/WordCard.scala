package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object WordCard extends WordCard with LongKeyedMetaMapper[WordCard] {
  def selectCard(u:User):Box[WordCard] = {
    var sum:Long = 0
    for(r <- RankInfo.ranksA(u)) {
      sum = sum * 2 + r.rank_count.is
    }
    if(sum == 0) {
      return Empty
    }
    var i = Math.abs(scala.util.Random.nextLong())%sum
    for(r <- RankInfo.ranksD(u)) {
      if(i < r.rank_count.is) {
        return Full(findAll(By(rank, r))(i.toInt))
      }
      i = (i - r.rank_count.is) / 2
    }
    return Empty
  }
}

class WordCard extends LongKeyedMapper[WordCard] with IdPK {
  def getSingleton = WordCard

  object user extends LongMappedMapper(this, User)
  object rank extends LongMappedMapper(this, RankInfo)
  object front extends MappedText(this)
  object back extends MappedText(this)

  def changeRank(torank:Box[RankInfo]) = {
    rank.obj match {
      case Full(r) => r.decrCount()
      case _ =>
    }
    torank match {
      case Full(r) => r.incrCount()
      case _ =>
    }
    rank(torank)
    saveMe()
  }

  def incrRank() = {
    changeRank(Full(rank.obj.get.succRank))
  }
  def initRank() = {
    changeRank(Full(RankInfo.firstRank(user.obj.get)))
  }
}
