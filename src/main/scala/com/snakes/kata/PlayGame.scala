package com.snakes.kata

import com.snakes.kata.model.{Finished, Game, Player}

import scala.util.Random

object PlayGame {

  val rnd = new Random(System.currentTimeMillis())

  def rollDice: Int = ???

  def movePlayerOf(game: Game, player: Player, numberOfSquares: Int): Game = ???

  def winner(game: Game): Option[Player] = ???

}
