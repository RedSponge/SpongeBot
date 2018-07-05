package com.redsponge.bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class AudioEventHandler extends AudioEventAdapter {

    private MessageReceivedEvent event;
    private AudioManager manager;

    public AudioEventHandler(MessageReceivedEvent event, AudioManager manager) {
        this.event = event;
        this.manager = manager;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        event.getChannel().sendMessage("STARTING TO PLAY").queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        event.getChannel().sendMessage("STOPPING TO PLAY").queue();
        manager.closeAudioConnection();
    }
}
