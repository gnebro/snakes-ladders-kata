package com.snakes.kata

import org.scalatest.{FreeSpec, Matchers}
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

object PlayGameCheckSpec extends Properties("Prop") {

  property("Dice value should be between 1 and 6") = forAll(PlayGame.rollDice) { n =>
    n >= 1 && n <= 6
  }
}
