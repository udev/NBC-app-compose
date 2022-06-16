package com.nbc.uitest

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.nbc.uitest.feature.HomePage
import com.nbc.uitest.feature.HomeScreenActivity
import com.nbc.uitest.feature.home.HomeScreenViewmodel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {
    // interesting approach via: https://medium.com/nerd-for-tech/writing-an-integration-test-with-jetpack-compose-and-dagger-hilt-8ef888c1a23d
    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HomeScreenActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            HomePage(composeTestRule.activity.viewModels<HomeScreenViewmodel>().value)
        }
    }

    @Test
    fun shelfListItemHeightTest() {
        composeTestRule.apply {
            waitUntil {
                onNodeWithTag("shelfList").onChildren().fetchSemanticsNodes().isNotEmpty()
            }
        }
        composeTestRule.onAllNodesWithTag("shelListItem").apply {
            onFirst().assertHeightIsAtLeast(83.dp)
            onLast().assertHeightIsAtLeast(83.dp)
        }
    }
}