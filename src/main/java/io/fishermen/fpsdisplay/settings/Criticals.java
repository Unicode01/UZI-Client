package io.fishermen.fpsdisplay.settings;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;
import pw.cinque.keystrokes.render.Gui;

public class Criticals extends GuiSettings{
    public static CommandSettings mode;
    static TimerUtilsA timer;
    public static boolean ena;
    static boolean cancelSomePackets;
    public Criticals (){
        super("Criticals","",c4.combat2,0,-1);
        mode = new CommandSettings("Mode",1,1,4,1);
        this.avav(mode);
        this.timer = new TimerUtilsA();
    }
    @Override
    public void en(){
        ena = true;
    }
    public void dd(){
        ena = false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if (mc.thePlayer.onGround && connection_side == Connection.Side.OUT && GuiSettings.isPlayerInGame() && ena) {
            if (object instanceof C02PacketUseEntity) {
                C02PacketUseEntity c02packetuseentity = (C02PacketUseEntity) object;

                if (c02packetuseentity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if (mode.g3tV4l4u3()==1) {
                        if (mc.thePlayer.isCollidedVertically && timer.isDelay(500L)) {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0627D, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                            Entity entity = c02packetuseentity.getEntityFromWorld(mc.theWorld);

                            if (entity != null) {
                                mc.thePlayer.onCriticalHit(entity);
                            }

                            timer.setLastMS();
                            cancelSomePackets = true;
                        }
                    } else if (mode.g3tV4l4u3() == 1) {
                        if (canJump()) {
                            mc.thePlayer.jump();
                        }
                    } else if (mode.g3tV4l4u3() ==2 && canJump()) {
                        mc.thePlayer.jump();
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0031311231111D,mc.thePlayer.posZ, false));
                    }
                }
            } else if (mode.g3tV4l4u3() == 3 && object instanceof C03PacketPlayer && cancelSomePackets) {
                cancelSomePackets = false;
                return false;
            }
        }
        return true;
    }

    static boolean canJump() {
        return mc.thePlayer.isOnLadder() ? false : (mc.thePlayer.isInWater() ? false : (mc.thePlayer.isInLava() ? false : (mc.thePlayer.isSneaking() ? false : (mc.thePlayer.isRiding() ? false : !mc.thePlayer.isPotionActive(Potion.blindness)))));
    }

    class TimerUtilsA {

        private long lastMS = 0L;
        private long prevMS = 0L;

        public boolean isDelay(long delay) {
            return System.currentTimeMillis() - this.lastMS >= delay;
        }

        public long getCurrentMS() {
            return System.nanoTime() / 1000000L;
        }

        public void setLastMS(long lastMS) {
            this.lastMS = lastMS;
        }

        public void setLastMS() {
            this.lastMS = System.currentTimeMillis();
        }

        public int convertToMS(int d) {
            return 1000 / d;
        }

        public boolean hasReached(float f) {
            return (float) (this.getCurrentMS() - this.lastMS) >= f;
        }

        public void reset() {
            this.lastMS = this.getCurrentMS();
        }

        public boolean delay(float milliSec) {
            return (float) (this.getTime() - this.prevMS) >= milliSec;
        }

        public long getTime() {
            return System.nanoTime() / 1000000L;
        }
    }
}
