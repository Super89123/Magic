package org.super89.supermegamod.magic;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.framework.qual.QualifierArgument;

import java.util.Objects;
import java.util.logging.Level;

public class SlashCommandListener extends ListenerAdapter {
    private final Magic plugin;

    public SlashCommandListener(Magic plugin) {
        this.plugin = plugin;
    }

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
                info.setColor(0xf555555);
                info.setFooter("Quantov", event.getMember().getUser().getAvatarUrl());
                for(Player player : Bukkit.getOnlinePlayers()){
                     // event.getHook().sendMessage(player.getName()).queue();
                    //event.getHook().sendMessage(player.getAddress().getHostString()).queue();
                    info.addField(player.getName(), player.getName()+" "+player.getAddress().getHostString() +" "+ player.getClientBrandName() +" "+ player.getPing(), true);

                }
                event.getHook().sendMessageEmbeds(info.build()).queue();
                System.out.println("I do it");
                break;
            case "supersay":
                String says = Objects.requireNonNull(event.getOption("event")).getAsString();
                if(plugin.isSupersays()){

                    if(says.equalsIgnoreCase("ночь") || says.equalsIgnoreCase("Пора спать") || says.equalsIgnoreCase("Нотч")){
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.sendTitle(ChatColor.GRAY+ "Кто-то включил", ChatColor.BLACK + "ночь!");
                            player.playSound(player, Sound.ENTITY_GHAST_DEATH, 100, 100);
                        }
                        say(event,"Я включил ночь!");

                    } else if (says.equalsIgnoreCase("слепота") || says.equalsIgnoreCase("ослепить") || says.equalsIgnoreCase("слепой") || says.contains("слеп")) {
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 5, false,false,false));
                            player.playSound(player, Sound.ENTITY_GHAST_DEATH, 100, 100);
                        }
                        
                    } else{
                        say(event, "Я пока не знаю что это)");
                    }
                }
                else{
                    plugin.getLogger().log(Level.INFO, "Внимание! super Says отключен");
                    event.reply("Эта функция выключена в конфигурации плагина! Обращайтесь к администрации").setEphemeral(true).queue();
                }
            default:
                event.reply("Я не заю что это такое:(").setEphemeral(true).queue();
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