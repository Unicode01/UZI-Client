package io.fishermen.fpsdisplay.settings;

import pw.cinque.event.Connection;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.keystrokes.render.animations.Timer;
import pw.cinque.timechanger.TimeHelper;
import scala.actors.threadpool.helpers.NanoTimer;

public class PingSpoof extends GuiSettings {
    private static boolean en;

    public static CommandSettings Ping;
    public PingSpoof(){
        super("PingSpoof","", c4.Tests,0,-1);
        Ping = new CommandSettings("Ping",100,0,1000,10);
        this.avav(Ping);
    }
    @Override
    public void en(){
        en = true;
    }
    @Override
    public void dd(){
        en = false;
    }

    public static void onPacket(Object object, Connection.Side connection_side) {
        if(en && connection_side == Connection.Side.OUT) wait((int) Ping.g3tV4l4u3());
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
