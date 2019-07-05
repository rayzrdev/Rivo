package dev.rayzr.rivo

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import kotlin.system.exitProcess

fun main() {
    Rivo.load()

    val jda = JDABuilder(Rivo.token)
            .addEventListeners(Rivo)
            .build()

    // Generate invite
    println(jda.getInviteUrl(
            Permission.MESSAGE_MANAGE,
            Permission.MANAGE_CHANNEL,
            Permission.MANAGE_ROLES,
            Permission.KICK_MEMBERS,
            Permission.BAN_MEMBERS
    ))
}

val yaml = Yaml()

object Rivo : EventListener {
    lateinit var token: String
    lateinit var prefix: String

    fun load() {
        val configFile = File("config.yml")

        if (!configFile.exists()) {
            Files.copy(javaClass.getResourceAsStream("/config.yml"), configFile.toPath())

            println("No config.yml found! The default one has been copied to the current directory. Please set it up before running Rivo again.")
            exitProcess(1)
        }

        val output = yaml.load(FileInputStream(configFile)) as Map<String, Any>

        token = output["token"].toString()
        prefix = output["prefix"].toString()
    }

    override fun onEvent(event: GenericEvent) {
        if (event is GuildMessageReceivedEvent) {
            // TODO: Handle commands.
        }
    }
}
