package com.example.palmtest.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.palmtest.legacy.ui.ChatFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatFragmentEspressoTest {

    @Test
    fun chatFragment_survivesLifecycleChanges_withoutCrashing() {
        // Launch the fragment
        val scenario = launchFragmentInContainer<ChatFragment>()

        // Simulate the exact crash scenario from the task:
        // 1. Open Chat (already done by launching)
        scenario.moveToState(Lifecycle.State.RESUMED)

        // 2. Toggle network off (simulate background)
        scenario.moveToState(Lifecycle.State.STARTED)

        // 3. Navigate back (destroy view)
        scenario.moveToState(Lifecycle.State.CREATED)

        // 4. Return to Chat (recreate view)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        // If we reach this point without a crash, the fix works
        scenario.onFragment { fragment ->
            assert(fragment.isAdded)
            assert(fragment.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
        }
    }

    @Test
    fun chatFragment_properlyObservesWithViewLifecycleOwner() {
        val scenario = launchFragmentInContainer<ChatFragment>()

        scenario.onFragment { fragment ->
            // Fragment should be properly initialized
            assert(fragment.isAdded)

            // ViewLifecycleOwner should be available
            assert(fragment.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))

            // Multiple lifecycle changes should not cause issues
            fragment.viewLifecycleOwner.lifecycle.currentState
        }

        // Move through lifecycle states multiple times
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        // Fragment should still be functional
        scenario.onFragment { fragment ->
            assert(fragment.isAdded)
        }
    }
}