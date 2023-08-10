package com.github.lllinear.psychics.events

import com.github.lllinear.psychics.psychics.Psychic
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PsychicSetEvent(private val player: Player, private val psychic: Psychic): PlayerEvent(player) {
    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList? {
            return handlerList
        }
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    fun getPsychic(): Psychic {
        return psychic
    }
}