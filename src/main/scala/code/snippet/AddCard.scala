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

class AddCard extends StatefulSnippet {
  override def dispatch = {
    case "render" => render
  }
  def render = {
    val user = User.currentUser.get
    val card = WordCard.create.user(user)
    "name=front" #> card.front.toForm &
    "name=back" #> card.back.toForm &
    "type=submit" #> onSubmit({s =>
      card.initRank()
    })
  }
}


