package com.github.lllinear.psychics.psychics

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class None : Psychic() {
    override val name = "무능력"
    override val description = listOf("아무런 능력이 없습니다.")
    override val icon = ItemStack(Material.BARRIER)
    override val health = 20.0
    override val maxMana = 0
    override val manaRegen = 0
    override var abilityList: List<Ability> = ArrayList()
}