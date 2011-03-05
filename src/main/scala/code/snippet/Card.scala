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

class Card extends StatefulSnippet {
  override def dispatch = {
    case "view" => view
    case "adder" => adder
  }
  def proc_card:Box[WordCard] = {
    WordCard.find(OrderBy(WordCard.lastmod, Ascending)) match {
      case Full(card) => {
        if(card.skip.is==0) {
          Full(card)
        } else {
          card.skip(card.skip.is-1)
          card.lastmod(new java.util.Date())
          card.save()
          proc_card
        }
      }
      case x => x
    }
  }
  var backvisible:Boolean = false
  def view:(NodeSeq=>NodeSeq) = {
    proc_card match {
      case Full(card) => {
        "class=card-front *" #> card.front.is &
        "class=card-back *" #> (if(backvisible) card.back.is else "") &
        "class=card-open" #> (if(backvisible) ClearNodes else onSubmit({s =>
          backvisible=true
        })) &
        "class=card-accept" #> (if(backvisible) onSubmit({s =>
          card.lastmod(new java.util.Date())
          card.skip(card.rank.is+1)
          card.rank(card.rank.is+1)
          card.save()
          backvisible = false
        }) else ClearNodes) &
        "class=card-deny" #> (if(backvisible) onSubmit({s =>
          card.lastmod(new java.util.Date())
          card.skip(0)
          card.rank(0)
          card.save()
          backvisible = false
        }) else ClearNodes)
      }
      case x => { x:NodeSeq => <span>====</span> }
    }
  }
  def adder = {
    val card = WordCard.create
    "name=front" #> card.front.toForm &
    "name=back" #> card.back.toForm &
    "type=submit" #> onSubmit({s =>
      card.rank(0)
      card.skip(0)
      card.lastmod(new java.util.Date())
      card.save()
    })
  }
}
