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

class EditCard {
  def render(xhtml:NodeSeq) =
    WordCard.findAll(By(WordCard.user, User.currentUser)).flatMap(card =>
      <lift:form.post>{
        renderCard(card)(xhtml)
      }</lift:form.post>
    )

  def renderCard(card:WordCard) =
    ".card-rank" #> card.rank.obj.get.rank_num &
    "name=card-front" #> text(card.front.is, card.front(_)) &
    "name=card-back" #> text(card.back.is, card.back(_)) &
    "name=card-save" #> submit("Save Card", () => card.initRank().save) &
    "name=card-del" #> submit("Delete", () => card.changeRank(Empty).delete_!)
}



