package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.event.Connection;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.EventSlowDown;

public class NoSlow extends GuiSettings {
    public static boolean en = false;
    public static int ticks = 0;
    public static CommandSettings Speed;
    public NoSlow(){
        super("NoSlow","",c4.Tests,0,-1);
        Speed = new CommandSettings("Speed",0.20,0.1,1.0,0.01);
        this.avav(Speed);
    }

    @Override
    public void en(){
        en=true;
    }
    @Override
    public void dd(){
        en=false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame()){
            return true;
        }
        if(en && connection_side == Connection.Side.IN && object instanceof S30PacketWindowItems && (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking())){
            ticks = mc.thePlayer.ticksExisted;
            return false;
        }
        return true;
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent e){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) {
            int process = Math.min(Math.round(((mc.thePlayer.ticksExisted - ticks) / 30f * 100)), 100);
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                    mc.thePlayer.inventory.getCurrentItem(), 0f, 0f, 0f));
        } else {
            ticks = mc.thePlayer.ticksExisted;
        }
        if(mc.thePlayer.onGround && (mc.thePlayer.isUsingItem() || mc.thePlayer.isBlocking()) && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())){
            s(Speed.g3tV4l4u3());
        }
    }

    public float gg() {
        float y = ne.mc.thePlayer.rotationYaw;
        final float f = ne.mc.thePlayer.moveForward;
        final float s = ne.mc.thePlayer.moveStrafing;
        y += ((f < 0.0f) ? 180 : 0);
        if (s < 0.0f) {
            y += ((f == 0.0f) ? 90.0f : ((f < 0.0f) ? -45.0f : 45.0f));
        }
        if (s > 0.0f) {
            y -= ((f == 0.0f) ? 90.0f : ((f < 0.0f) ? -45.0f : 45.0f));
        }
        return y * 0.017453292f;
    }

    public void s(final double s) {
        ne.mc.thePlayer.motionX = -MathHelper.sin(this.gg()) * s;
        ne.mc.thePlayer.motionZ = MathHelper.cos(this.gg()) * s;
    }
}
