// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.timechanger.ClickListener;

public class fl extends GuiSettings
{
    public static CommandSettings Mode;
    public static ClickListener M3SetonGround;
    public static ClickListener FakeDamage;
    private static boolean start;
    public static CommandSettings Speed;
    public fl() {
        super(GuiSettings.a(new char[] { 'F', 'l', 'y' }), "", c4.movement, 0, -1);
        this.avav(fl.Mode = new CommandSettings(GuiSettings.a(new char[] { 'M', 'o', 'd', 'e'}), 1, 1, 4, 1));
        this.avav(fl.Speed = new CommandSettings("Speed",0.01,0.01,1,0.01));
        this.avav(fl.FakeDamage = new ClickListener("FakeDamage",false));
        this.avav(fl.M3SetonGround = new ClickListener("Mode34SetOnGround",false));
    }

    @SubscribeEvent
    public void a(final LivingEvent.LivingUpdateEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(FakeDamage.i() && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0){
            start=true;
        }
        if(FakeDamage.i() && !start){
            if(mc.thePlayer.fallDistance >= 3.5F){
                return;
            } else {
                if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX,mc.thePlayer.posY-0.1,mc.thePlayer.posZ)).getBlock() != Blocks.air){
                    mc.thePlayer.jump();
                }
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
                mc.thePlayer.onGround = false;
                return;
            }
        }
        if (fl.Mode.g3tV4l4u3() == 1) {
            final int xd = 0;
            if (fl.mc.gameSettings.keyBindJump.isKeyDown()) {
                fl.mc.thePlayer.motionY = 1.0;
            }
            if (fl.mc.gameSettings.keyBindSneak.isKeyDown()) {
                fl.mc.thePlayer.motionY = -1.0;
            }
            if (i((Entity) fl.mc.thePlayer) && !fl.mc.gameSettings.keyBindJump.isKeyDown() && !fl.mc.gameSettings.keyBindSneak.isKeyDown() && (fl.mc.thePlayer.motionY <= -0.41 || fl.mc.thePlayer.onGround)) {
                s(Speed.g3tV4l4u3()*10);
                fl.mc.thePlayer.motionY = -0.6;
            }
            if (!fl.mc.gameSettings.keyBindJump.isKeyDown() && !fl.mc.gameSettings.keyBindSneak.isKeyDown() && (fl.mc.thePlayer.motionY <= -0.42 || fl.mc.thePlayer.onGround)) {
                fl.mc.thePlayer.motionY = 0.4;
            }
        }
        if(fl.Mode.g3tV4l4u3() == 3){
            if (fl.M3SetonGround.i()) {
                mc.thePlayer.onGround = true;
            }
            mc.thePlayer.motionY = 0;
        }
        if(fl.Mode.g3tV4l4u3() == 4){
            if(mc.gameSettings.keyBindJump.isKeyDown()){
                mc.thePlayer.setPosition(mc.thePlayer.posX,mc.thePlayer.posY+Speed.g3tV4l4u3(),mc.thePlayer.posZ);
            }
            if(mc.gameSettings.keyBindSneak.isKeyDown()){
                mc.thePlayer.setPosition(mc.thePlayer.posX,mc.thePlayer.posY-Speed.g3tV4l4u3(),mc.thePlayer.posZ);
            }
            if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()){
                mc.thePlayer.setPosition(TPPosX(Speed.g3tV4l4u3()),mc.thePlayer.posY,TPPosZ(Speed.g3tV4l4u3()));
            }
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionY = 0;
            mc.thePlayer.motionZ = 0;
            if(M3SetonGround.i()){
                mc.thePlayer.onGround = true;
            }
        }
    }
    
    public static boolean i(final Entity ent) {
        return Minecraft.getMinecraft().thePlayer.moveForward != 0.0f || Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0f;
    }
    
    public static float gg() {
        float v = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            v += 180.0f;
        }
        float f = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            f = -0.5f;
        }
        else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0f) {
            f = 0.5f;
        }
        else {
            f = 1.0f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
            v -= 90.0f * f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
            v += 90.0f * f;
        }
        v *= 0.017453292f;
        return v;
    }
    
    public static void s(final double s) {
        Minecraft.getMinecraft().thePlayer.motionX = -(Math.sin(gg()) * s);
        Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(gg()) * s;
    }
    public static double TPPosX(final double s) {
        return mc.thePlayer.posX + -(Math.sin(gg()) * s);
    }
    public static double TPPosZ(final double s) {
        return mc.thePlayer.posZ + Math.cos(gg()) * s;
    }
    @Override
    public void en(){
        if(fl.FakeDamage.i()){
            //selfDamage(3.5D);
        }
        if(fl.Mode.g3tV4l4u3()==2){
            mc.thePlayer.capabilities.allowFlying = true;
            mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.capabilities.setFlySpeed((float) (3 * Speed.g3tV4l4u3()));
        }
    }
    @Override
    public void dd(){
            mc.thePlayer.capabilities.isFlying = false;
            mc.thePlayer.capabilities.allowFlying = false;
            start=false;
    }
    public static void selfDamage(double posY) {
        if(mc.thePlayer.onGround){
            mc.thePlayer.jump();
        }
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY + posY,mc.thePlayer.posZ,false));
    }
}
