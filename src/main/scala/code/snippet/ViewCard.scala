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

class ViewCard extends StatefulSnippet {
  override def dispatch = {
    case "render" => render
  }
  val selectedCard:Box[WordCard] = WordCard.selectCard(User.currentUser.get)
  var backvisible:Boolean = false
  def render:(NodeSeq=>NodeSeq) = {
    selectedCard match {
      case Full(card) => {
        ".card-front" #> card.front.is &
        ".card-back" #> (if(backvisible) card.back.is else "") &
        ".card-open" #> (if(backvisible) ClearNodes else onSubmit({s =>
          backvisible=true
        })) &
        ".card-accept" #> (if(backvisible) onSubmit({s =>
          card.incrRank()
          AnswerLog.create.user(User.currentUser).card(card).result(true).save()
          backvisible = false
        }) else ClearNodes) &
        ".card-deny" #> (if(backvisible) onSubmit({s =>
          card.initRank()
          AnswerLog.create.user(User.currentUser).card(card).result(false).save()
          backvisible = false
        }) else ClearNodes)
      }
      case x => { x:NodeSeq => <span>No card there</span> }
    }
  }
}

