package com.github.lllinear.psychics.psychics

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class None : Psychic() {
    override val name: String = "무능력"
    override val description: List<String> = listOf("아무런 능력이 없습니다.")
    override val icon: ItemStack = ItemStack(Material.BARRIER)
    override val abilityList: List<Ability> = ArrayList()
}