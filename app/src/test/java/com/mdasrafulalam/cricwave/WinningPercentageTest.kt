package com.mdasrafulalam.cricwave

import org.junit.Test
import org.junit.Assert.assertEquals

class WinningPercentageTest {
    fun calculateWinningPercentage(totalWins: Int, totalMatchesPlayed: Int, recentWins: Int, recentMatchesPlayed: Int, recentFormWeight: Double): Double {
        val overallWinningPercentage = if (totalMatchesPlayed > 0) totalWins.toDouble() / totalMatchesPlayed.toDouble() else 0.0
        val recentWinningPercentage = if (recentMatchesPlayed > 0) recentWins.toDouble() / recentMatchesPlayed.toDouble() else 0.0
        val weightedWinningPercentage = (1 - recentFormWeight) * overallWinningPercentage + recentFormWeight * recentWinningPercentage
        return weightedWinningPercentage * 100 // Convert to percentage
    }

    @Test
    fun testCalculateWinningPercentage() {
        // Test case 1: All parameters are 0, expected result is 0.0
        val result1 = calculateWinningPercentage(0, 0, 0, 0, 0.5)
        assertEquals(0.0, result1, 0.0)

        // Test case 2: Total matches played is 0, expected result is 0.0
        val result2 = calculateWinningPercentage(2, 0, 1, 1, 0.5)
        assertEquals(50.0, result2, 0.0)

        // Test case 3: Recent matches played is 0, expected result is the overall winning percentage
        val result3 = calculateWinningPercentage(10, 20, 0, 0, 0.5)
        val expected3 = 25.0
        assertEquals(expected3, result3, 0.0)

        // Test case 4: All parameters are non-zero, recent form weight is 0.0, expected result is the overall winning percentage
        val result4 = calculateWinningPercentage(20, 30, 10, 15, 0.0)
        val expected4 = 20.0 / 30.0 * 100.0
        assertEquals(expected4, result4, 0.0)

        // Test case 5: All parameters are non-zero, recent form weight is 1.0, expected result is the recent winning percentage (failing test)
        val result5 = calculateWinningPercentage(20, 30, 10, 15, 1.0)
        val expected5 = 10.0 / 15.0 * 100.0
        assertEquals(expected5, result5, 0.0)

        // Test case 6: All parameters are non-zero, recent form weight is 0.5, expected result is the weighted winning percentage
        val result6 = calculateWinningPercentage(20, 30, 10, 15, 0.5)
        val expected6 = (1 - 0.5) * (20.0 / 30.0) + 0.5 * (10.0 / 15.0)
        assertEquals(expected6 * 100.0, result6, 0.0)
    }
}
