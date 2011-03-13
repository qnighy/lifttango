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
    var front_str:String = ""
    var back_str:String = ""
    "name=front" #> text(front_str, front_str = _) &
    "name=back" #> text(back_str, back_str = _) &
    "type=submit" #> onSubmit({s =>
      WordCard.addNewCard(user, front_str, back_str)
    })
  }
}


