package com.github.lllinear.psychics

import com.github.lllinear.psychics.psychics.psychic
import com.github.lllinear.psychics.utils.PsychicInfoInventory
import com.github.lllinear.psychics.utils.PsychicManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class PsychicCommandExecutor: CommandExecutor, TabExecutor {
    private val options = listOf("set", "info")

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            val optionList = ArrayList<String>()
            for (option in options) {
                if (!option.contains(args[0])) {
                    continue
                }
                optionList.add(option)
            }
            return optionList
        } else if ((args[0] == "set" || args[0] == "info") && args.size == 2) {
            val playerNameList = ArrayList<String>()
            for (player in Bukkit.getOnlinePlayers()) {
                if (!player.name.contains(args[1])) {
                    continue
                }
                playerNameList.add(player.name)
            }
            return playerNameList.toMutableList()
        } else if ((args[0] == "set" || args[0] == "info") && args.size == 3) {
            val psychicList = ArrayList<String>()
            for (psychic in PsychicManager.getPsychicList()) {
                if (!psychic::class.simpleName!!.lowercase().contains(args[2])) {
                    continue
                }
                psychicList.add(psychic::class.simpleName!!.lowercase())
            }
            return psychicList
        }

        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, laabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            Psychics.sendMessage(sender as Player, "Please use this command in-game.")
            return false
        }

        if (!sender.hasPermission("lllinear.command.psychic")) {
            Psychics.sendMessage(sender, "You don't have enough permission to use this command.")
            return false
        }

        if (args.isEmpty() || !options.contains(args[0])) {
            Psychics.sendMessage(sender, "/psychic set <playerName> <psychicName> - Set player's psychic.")
            Psychics.sendMessage(sender, "/psychic info <playerName> <psychicName> - Show info gui to player.")
        }

        if (args[0] == "set") {
            if (args.size < 3) {
                Psychics.sendMessage(sender, "/psychic set <playerName> <psychicName> - Set player's psychic.")
                return false
            }

            if (Bukkit.getPlayer(args[1]) == null) {
                Psychics.sendMessage(sender, "Player not found.")
                return false
            }

            val player = Bukkit.getPlayer(args[1])!!
            val psychic = PsychicManager.getPsychic(args[2])
            player.psychic = psychic
            Psychics.sendMessage(player, "Set ${player.name}'s psychic to ${psychic.name}")
            return true
        } else if (args[0] == "info") {
            if (args.size < 3) {
                Psychics.sendMessage(sender, "/psychic info <playerName> <psychicName> - Show info gui to player.")
                return false
            }

            if (Bukkit.getPlayer(args[1]) == null) {
                Psychics.sendMessage(sender, "Player not found.")
                return false
            }

            val player = Bukkit.getPlayer(args[1])
            val psychic = PsychicManager.getPsychic(args[2])
            PsychicInfoInventory(player!!, psychic)
            return true
        }

        return true
    }
}