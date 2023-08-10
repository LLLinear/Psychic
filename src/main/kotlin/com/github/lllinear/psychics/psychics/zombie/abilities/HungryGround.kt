package com.github.lllinear.psychics.psychics.zombie.abilities

import com.github.lllinear.psychics.Psychics
import com.github.lllinear.psychics.psychics.Ability
import com.github.lllinear.psychics.psychics.Psychic
import com.github.lllinear.psychics.utils.AbilityType
import com.github.lllinear.psychics.utils.PsychicManager
import org.bukkit.*
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.Random
import kotlin.math.cos
import kotlin.math.sin

class HungryGround(psychic: Psychic): Ability(psychic) {
    override val name: String = "헝그리 그라운드"
    override val abilityType: AbilityType = AbilityType.ACTIVE
    override val description: List<String> = listOf("스킬 사용 시 현재 위치에 영역을 형성합니다.", "영역 안 적들은 독 피해를 받고 배고픔이 감소합니다.")
    override val icon: ItemStack = ItemStack(Material.ROTTEN_FLESH)
    override var isActivating: Boolean = false
    override var leftDuration: Int = 0

    override val coolDown: Int = 25 * 20
    override val castingTime: Int = 10
    override val mana: Int = 55
    override val duration: Int = 8 * 20
    override val range: Double = 12.0
    override val damage: Double = 2.0

    val maxHunger: Int = 5

    override fun onTrigger(event: Event) {
        if (event !is PlayerInteractEvent) {
            return
        }

        if (event.item!!.type != icon.type) {
            return
        }

        super.onTrigger(event)
    }

    override fun onActivate(event: Event) {
        isActivating = true
        leftDuration = duration

        val hungryGroundTask = HungryGroundTask(psychic.player, this)
        hungryGroundTask.runTaskTimer(Psychics.getPlugin(), 0L, 1L)

        Psychics.sendMessage(psychic.player, "스킬 사용")
    }
}

class HungryGroundTask(private val player: Player, private val hungryGround: HungryGround): BukkitRunnable() {
    private val loc = player.location

    override fun run() {
        if (hungryGround.leftDuration <= 0) {
            hungryGround.isActivating = false
            hungryGround.leftDuration = 0
            cancel()
        }

        loc.world!!.spawnParticle(Particle.REDSTONE, loc.clone().add(0.0, 5.0, 0.0), 40, 0.0, 3.0, 0.0, DustOptions(Color.fromRGB((230..255).random(), (210..235).random(), (230..255).random()), 1.0f))

        for (i in 0..100) {
            val x = sin(i.toDouble()) * hungryGround.range
            val z = cos(i.toDouble()) * hungryGround.range
            loc.world!!.spawnParticle(Particle.REDSTONE, loc.clone().add(x, 0.5, z), 1, DustOptions(Color.fromRGB((230..255).random(), (210..235).random(), (230..255).random()), 1.0f))
        }

        for (entity in loc.world!!.livingEntities) {
            if (entity.location.distance(loc) > hungryGround.range || entity == player) {
                continue
            }

            if (entity is HumanEntity && entity.foodLevel > hungryGround.maxHunger) {
                entity.foodLevel = hungryGround.maxHunger
            }

            entity.addPotionEffect(PotionEffect(PotionEffectType.POISON, 20 * 3, 1, true, true, false))
        }

        hungryGround.leftDuration -= 1

        player.world.playSound(loc, Sound.BLOCK_SLIME_BLOCK_PLACE, 1.0f, 1.0f)
    }
}