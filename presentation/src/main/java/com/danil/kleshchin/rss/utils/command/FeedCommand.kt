package com.danil.kleshchin.rss.utils.command

interface FeedCommand {
    suspend fun execute()
}

interface FeedWithUndoCommand : FeedCommand {
    suspend fun undo()
}
