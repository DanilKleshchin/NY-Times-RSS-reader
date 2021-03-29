package com.danil.kleshchin.rss.utils.command

import java.util.Stack

object FeedCommandHistory {

    private val queue = Stack<FeedWithUndoCommand>()

    fun push(command: FeedWithUndoCommand) {
        queue.add(command)
    }

    fun pop() = queue.pop()
}
