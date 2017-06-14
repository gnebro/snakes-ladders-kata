package com.snakes.kata

package object model {


  // Game's status
  sealed trait Status
  case object NotStarted extends Status
  case object Started extends Status
  case object Finished extends Status

  // Snakes and Ladders
  sealed abstract class Bridge(val from: Int, val to: Int) {
    require(to <=100 && to >= 1, "Bridges cannot extend outside the board: to")
    require(from <=100 && from >= 1, "Bridges cannot extend outside the board: to")
  }

  final case class Snake(override val from: Int, override val to: Int) extends Bridge(from, to) {
    require(to < from, "Snakes can not take forward")
  }
  final case class Ladder(override val from: Int, override val to: Int) extends Bridge(from, to) {
    require(to > from, "Ladders can not take backwards")
  }

  // Players and player's position
  final case class Player(id: String)
  final case class PlayerPosition(player: Player, square: Int = 1) {
    require(square >=1 && square <= 100, "Square must be bwtween 1 and 100 included")
  }

  // Game Board
  final case class Board(bridges: Seq[Bridge]) {
    require(bridges.groupBy(_.from).forall { case (_, seq) => seq.size <= 1 }, "can not have bridges from the same square")
  }

  final case class Game(board: Board, players: Map[String, PlayerPosition], status: Status = NotStarted)


}
