package org.super89.supermegamod.magic;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        System.out.println("I showes smt");
        event.getChannel().sendMessage("ffff");
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName())
        {

            case "players":
                say(event, Bukkit.getOnlinePlayers().toString());
                event.getChannel().sendMessage((CharSequence) Bukkit.getOnlinePlayers().toString());
                System.out.println("I do it");// content is required so no null-check here
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }
    @Override
    public void onReady(ReadyEvent event){
        System.out.println("я вкл");

    }
    public void say(SlashCommandInteractionEvent event, String content)
    {
        event.reply(content).queue(); // This requires no permissions!
    }
}