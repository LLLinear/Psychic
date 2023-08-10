package com.github.lllinear.psychics.psychics

import com.github.lllinear.psychics.Psychics
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Psychic {
    abstract val name: String

    abstract val description: List<String>

    abstract val icon: ItemStack

    lateinit var player: Player
}

var Player.psychic: Psychic
    get() { return psychic }
    set(value) {
        psychic = value
    }