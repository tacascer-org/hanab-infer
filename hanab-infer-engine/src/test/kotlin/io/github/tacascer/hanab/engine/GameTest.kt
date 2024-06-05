package io.github.tacascer.hanab.engine

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next

class GameTest : FunSpec({
    test("can't create game with fewer than 2 players") {
        val exception =
            shouldThrow<IllegalArgumentException> { Game(Arb.list(randomPlayer(), 1..1).next()) }

        exception.message shouldBe "Hanabi is a game for 2-5 players"
    }

    test("can't create game with more than 5 players") {
        val exception =
            shouldThrow<IllegalArgumentException> {
                Game(Arb.list(randomPlayer(), 6..10).next())
            }
        exception.message shouldBe "Hanabi is a game for 2-5 players"
    }
})
