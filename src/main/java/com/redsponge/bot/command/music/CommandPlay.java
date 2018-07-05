package com.redsponge.bot.command.music;

import com.redsponge.bot.SpongeBot;
import com.redsponge.bot.command.CommandCategory;
import com.redsponge.bot.command.ICommand;
import com.redsponge.bot.command.Permission;
import com.redsponge.bot.music.AudioEventHandler;
import com.redsponge.bot.music.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.audio.AudioConnection;
import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class CommandPlay implements ICommand {

    AudioTrack track;

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        Member member = event.getMember();
        VoiceChannel myChannel = member.getVoiceState().getChannel();
        AudioManager manager = event.getGuild().getAudioManager();

        manager.openAudioConnection(myChannel);

        AudioPlayer player = SpongeBot.instance.audioPlayerManager.createPlayer();
        AudioEventHandler eventHandler = new AudioEventHandler(event, manager);
        player.addListener(eventHandler);

        track = null;

        SpongeBot.instance.audioPlayerManager.loadItem("dQw4w9WgXcQ", new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                track = audioTrack;
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {

            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });






        //AudioSendHandler sendHandler = new

    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Plays a song!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public Permission getPermission() {
        return Permission.GOD;
    }
}
