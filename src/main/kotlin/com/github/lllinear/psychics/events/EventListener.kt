package com.github.lllinear.psychics.events

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.mana
import com.github.lllinear.psychics.psychics.maxMana
import com.github.lllinear.psychics.utils.ManaBar
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EventListener: Listener {
    @EventHandler
    fun onPsychicSet(event: PsychicSetEvent) {
        val player = event.player
        val psychic = event.getPsychic()

        ManaBar.hide(player)

        player.maxHealth = psychic.health
        player.health = psychic.health

        player.mana = psychic.maxMana

        if (psychic.maxMana > 0) {
            ManaBar.create(player)
        }

        Psychics.sendMessage(player, "Your psychic is now ${psychic.name}.")
    }
}