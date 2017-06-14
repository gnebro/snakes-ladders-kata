package com.snakes.kata

import com.snakes.kata.model._
import org.scalatest.{FreeSpec, Matchers}

class PlayGameSpec extends FreeSpec with Matchers {

  import PlayGameFixture._
  import PlayGame._

  "Given the game is started" - {
    "When the token is placed on the board" - {
      "Then the token is on square 1" in {
        GameWithOnePlayerAndSimpleBoard.players.forall(_._2.square == 1) shouldBe true
        GameWithMultiplePlayersAndBoardWithSnake.players.forall(_._2.square == 1) shouldBe true
      }
    }
  }

  "Given the token is on square 1" - {
    "When the token is moved 3 spaces" - {
      val game = movePlayerOf(GameWithOnePlayerAndSimpleBoard, Player_1, 3)
      "Then the token is on square 4" in {
        game.players.get(Player_1.id).head.square shouldBe 4
        game.status shouldBe Started
      }

      "And then it is moved 4 spaces" - {
        val game1 = movePlayerOf(game, Player_1, 4)
        "Then the token is on square 8" in {
          game1.players.get(Player_1.id).head.square shouldBe 8
          game1.status shouldBe Started
        }
      }
    }
  }

  "Given the token is on square 97" - {
    val game97 = movePlayerOf(GameWithOnePlayerAndSimpleBoard, Player_1, 96)
    "When the token is moved 3 spaces" - {
      val game100 = movePlayerOf(game97, Player_1, 3)
      "Then the token is on square 100" in {
        game100.players.get(Player_1.id).head.square shouldBe 100
        game100.status shouldBe Finished
      }
      "And the player has won the game" in {
        winner(game100) shouldBe Some(Player_1)
      }

      "And then it is moved 4 spaces" - {
        val gameNotChanged = movePlayerOf(game97, Player_1, 4)
        "Then the token is on square 8" in {
          gameNotChanged.players.get(Player_1.id).head.square shouldBe 97
          winner(gameNotChanged) shouldBe None
          gameNotChanged.status shouldBe Started
        }
      }
    }
  }

  "Given there is a snake connecting squares 2 and 12" - {
    "When the token lands on square 12" - {
      val gameSnake = movePlayerOf(GameWithMultiplePlayersAndBoardWithSnake, Player_2, 11)
      "Then the token is on square 2" in {
        gameSnake.players.get(Player_2.id).head.square shouldBe 2
      }
    }
    "When the token lands on square 2" - {
      val game = movePlayerOf(GameWithMultiplePlayersAndBoardWithSnake, Player_2, 1)
      "Then the token is on square 2" in {
        game.players.get(Player_2.id).head.square shouldBe 2
      }
    }
  }

  "Given there is a ladder connecting squares 2 and 12" - {
    "When the token lands on square 2" - {
      val gameLadder = movePlayerOf(GameWithMultiplePlayersAndBoardWithLadder, Player_2, 1)
      "Then the token is on square 12" in {
        gameLadder.players.get(Player_2.id).head.square shouldBe 12
      }
    }
    "When the token lands on square 12" - {
      val game = movePlayerOf(GameWithMultiplePlayersAndBoardWithLadder, Player_2, 11)
      "Then the token is on square 12" in {
        game.players.get(Player_2.id).head.square shouldBe 12
      }
    }
  }

}

object PlayGameFixture {

  val SnakeFrom12to2 = Snake(12, 2)

  val LadderFrom2to12 = Ladder(2, 12)

  val Player_1 = Player("1")
  val Player_2 = Player("2")
  val Player_3 = Player("3")

  val OnePlayer = Map(Player_1.id -> PlayerPosition(Player_1))
  val MultiplePlayers = Map(Player_1.id -> PlayerPosition(Player_1), Player_2.id -> PlayerPosition(Player_2), Player_3.id -> PlayerPosition(Player_3))

  val SimpleBoard = Board(Seq.empty[Bridge])
  val BoardWithSnake = Board(Seq(SnakeFrom12to2))
  val BoardWitLadder = Board(Seq(LadderFrom2to12))

  val GameWithOnePlayerAndSimpleBoard = Game(SimpleBoard, OnePlayer)
  val GameWithMultiplePlayersAndSimpleBoard = Game(SimpleBoard, MultiplePlayers)
  val GameWithMultiplePlayersAndBoardWithSnake = Game(BoardWithSnake, MultiplePlayers)
  val GameWithMultiplePlayersAndBoardWithLadder = Game(BoardWitLadder, MultiplePlayers)

}
