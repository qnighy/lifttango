package code.snippet

import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.mapper._
import _root_.net.liftweb.http.{StatefulSnippet}
import _root_.net.liftweb.http.SHtml._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.java.util.Date
import code.lib._
import code.model._
import Helpers._

class PortCard {
  def render = {
    var t = exportText
    "name=porttext" #> textarea(t, t = _) &
    "type=submit" #> onSubmit({s => importText(t)})
  }
  def exportText:String =
    WordCard.findAll(By(WordCard.user, User.currentUser)).map(card =>
      "%s##%s\n".format(card.front.is, card.back.is)
    ).mkString("")
  def importText(s:String):Unit = {
    s.lines.foreach({l =>
      l match {
        case PortCard.re(f,b) => WordCard.addNewCard(User.currentUser.get,f,b)
        case _ =>
      }
    })
  }

  def anslog =
    "name=anslog-text" #> textarea(exportAnswerLog, s => s)
  def exportAnswerLog:String =
    AnswerLog.findAll(By(AnswerLog.user, User.currentUser), OrderBy(AnswerLog.time, Ascending)).map(al =>
      "%s,%s,%s,%s\n".format(
        al.time.is,
        al.front.is,
        al.back.is,
        if(al.result) "TRUE" else "FALSE")
    ).mkString("")
}

object PortCard {
  val re = "(.*)##(.*)".r
}

