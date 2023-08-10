package com.github.lllinear.psychics.psychics

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Ability {
    lateinit var name: String
        private set

    lateinit var description: List<String>
        private set

    lateinit var icon: ItemStack
        private set
}