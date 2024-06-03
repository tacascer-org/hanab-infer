package io.github.tacascer.hanab.infer

import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string

val randomPlayer =
    arbitrary { rs ->
        val name = Arb.string().next(rs)
        val hand = Arb.list(randomCard).next(rs)
        Player(name, hand)
    }

val randomCard =
    arbitrary { rs ->
        val value = Arb.int(1..5)
        val color = Arb.enum<CardColor>()
        Card(value.next(rs), color.next(rs))
    }

val randomNumberClue =
    arbitrary { rs ->
        val turnIssued = Arb.int(0..10).next(rs)
        val affectedCards = Arb.list(randomCard).next(rs)
        val numberClue = Arb.int(1..5).next(rs)
        NumberClue(numberClue, turnIssued, affectedCards)
    }

val randomColorClue =
    arbitrary { rs ->
        val turnIssued = Arb.int(0..10).next(rs)
        val affectedCards = Arb.list(randomCard).next(rs)
        val colorClue = Arb.enum<CardColor>().next(rs)
        ColorClue(colorClue, turnIssued, affectedCards)
    }
