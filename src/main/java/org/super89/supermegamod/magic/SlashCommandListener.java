package org.super89.supermegamod.magic;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.framework.qual.QualifierArgument;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        System.out.println("I showes smt");
        event.getChannel().sendMessage("ffff");

        if (event.getGuild() == null)
            return;
        switch (event.getName())
        {

            case "players":
                event.deferReply().queue();
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("Игроки в сети");
                info.setDescription("Играют:");
                info.setColor(0xf45642);
                info.setFooter("Quantov", event.getMember().getUser().getAvatarUrl());
                for(Player player : Bukkit.getOnlinePlayers()){
                     // event.getHook().sendMessage(player.getName()).queue();
                    //event.getHook().sendMessage(player.getAddress().getHostString()).queue();
                    info.addField(player.getName(), player.getName()+" "+player.getAddress().getHostString(), true);
                }
                event.getHook().sendMessageEmbeds(info.build()).queue();
                System.out.println("I do it");
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