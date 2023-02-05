package io.fishermen.fpsdisplay.settings;

import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.timechanger.TimeHelper;

import java.util.ArrayList;

public class Strafe extends GuiSettings{
    public static ClickListener DamageStrafe;
    private static TimeHelper TimeHelper = new TimeHelper();
    private static int Hurt;
    public static CommandSettings DamageStrafeTime;
    public static CommandSettings StrafeSpeed;
    public Strafe(){
        super("Strafe","",c4.movement,0,1);
        this.avav(DamageStrafe = new ClickListener("DamageStrafe",false));
        this.avav(DamageStrafeTime = new CommandSettings("DamageStrafeTime",500,100,2000,20));
        this.avav(StrafeSpeed = new CommandSettings("StrafeSpeed",0.2,0.01,1,0.01));

    }
    @SubscribeEvent
    public void a(LivingEvent.LivingUpdateEvent e){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(!DamageStrafe.i()){
            if(mc.thePlayer.moveStrafing != 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isBlocking() && !mc.thePlayer.isInLava() && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.onGround){
                s(StrafeSpeed.g3tV4l4u3());
            }
            if(!mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()){
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }
        }else {
            if(!TimeHelper.isDelayComplete(DamageStrafeTime.g3tV4l4u3()) && isMoveKey()) {
                s(StrafeSpeed.g3tV4l4u3());
            }
            if(Da.mc.thePlayer.hurtTime == Da.mc.thePlayer.maxHurtTime && Da.mc.thePlayer.maxHurtTime > 0){
                TimeHelper.reset();
                s(StrafeSpeed.g3tV4l4u3());
            }
        }
    }
    private boolean isMoveKey(){
        return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown();
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
