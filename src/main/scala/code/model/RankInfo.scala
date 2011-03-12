package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object RankInfo extends RankInfo with LongKeyedMetaMapper[RankInfo] {
  def getRank(u:User, r:Long) = {
    RankInfo.find(By(user, u), By(rank_num, r)) match {
      case Full(x) => x
      case _ => create.user(u).rank_num(r).saveMe()
    }
  }
  def firstRank(u:User) = getRank(u, 0)
  def ranksA(u:User) = findAll(By(user, u), OrderBy(rank_num, Ascending))
  def ranksD(u:User) = findAll(By(user, u), OrderBy(rank_num, Descending))
}

class RankInfo extends LongKeyedMapper[RankInfo] with IdPK {
  def getSingleton = RankInfo

  object user extends LongMappedMapper(this, User)
  object rank_num extends MappedLong(this)
  object rank_count extends MappedLong(this) {
    override def defaultValue = 0
  }
  def succRank = RankInfo.getRank(user.obj.get, rank_num.is+1)
  def incrCount():Unit = {
    rank_count(rank_count.is+1)
    save()
  }
  def decrCount():Unit = {
    rank_count(rank_count.is-1)
    save()
  }
}

