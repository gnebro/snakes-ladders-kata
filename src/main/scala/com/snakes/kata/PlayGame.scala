package com.snakes.kata

import com.snakes.kata.model._

import scala.util.Random

object PlayGame {

  val rnd = new Random(System.currentTimeMillis())

  def rollDice: Int = rnd.nextInt(6) + 1

  def movePlayerOf(game: Game, player: Player, numberOfSquares: Int): Game = {
    if(game.status == Finished) game
    else {
      val players: Map[String, PlayerPosition] = game.players
      players.get(player.id).fold(game)(currentPosition => {
        val newPosition = updateSquare(game.board, currentPosition.square, numberOfSquares)
        val updated = players + (player.id -> PlayerPosition(player, newPosition))
        if(newPosition == 100) game.copy(players = updated, status = Finished)
        else game.copy(players = updated, status = Started)
      })
    }
  }

  def winner(game: Game): Option[Player] = {
    if(game.status == Finished) {
      game.players.find {
        case (_, position) if position.square == 100 => true
        case _ => false
      }.flatMap(p => Some(p._2.player))
    } else {
      None
    }

  }

  private def updateSquare(board: Board, current: Int, increment: Int): Int = {
    val newPosition = if(current + increment > 100) current
      else current + increment

    board.bridges.find(_.from == newPosition).fold(newPosition)(_.to)

  }

}
