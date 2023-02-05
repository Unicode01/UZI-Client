package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;

public class Regen extends GuiSettings{
    public static CommandSettings mode;
    public static CommandSettings health;
    public static CommandSettings speed;
    public static CommandSettings tick;
    public Regen(){
        super("Regen","",c4.Player,0,-1);
        mode = new CommandSettings("Mode",1,1,2,1);
        health = new CommandSettings("Health", 15, 0, 20, 1);
        speed = new CommandSettings("Speed",  20, 1, 300, 1);
        tick = new CommandSettings("Tick", 1, 1, 20, 1);
        this.avav(mode);
        this.avav(health);
        this.avav(speed);
        this.avav(tick);
    }
    @SubscribeEvent
    public void a (TickEvent.PlayerTickEvent ena){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (mc.thePlayer.ticksExisted > 10 && mc.thePlayer.getHealth() < health.g3tV4l4u3() && mc.thePlayer.ticksExisted % tick.g3tV4l4u3() == 0) {
            for (int i = 0; i < speed.g3tV4l4u3(); i++) {
                switch ((int) mode.g3tV4l4u3()) {
                    case 1:
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
                        break;

                    case 2:
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 1E-9, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
                        break;
                }
            }
        }
    }
}
