package io.fishermen.fpsdisplay.settings;

import jdk.nashorn.internal.ir.Block;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;
import pw.cinque.keystrokes.render.animations.Direction;
import pw.cinque.keystrokes.render.animations.impl.DecelerateAnimation;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.timechanger.TimeHelper;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewKillaura extends GuiSettings{

    private static boolean en;
    public static pw.cinque.CommandSettings.CommandSettings range;
    public static pw.cinque.CommandSettings.CommandSettings SwitchDelay;
    public static pw.cinque.CommandSettings.CommandSettings angle;
    public static pw.cinque.CommandSettings.CommandSettings CriticalMode;
    public static pw.cinque.CommandSettings.CommandSettings BlockRange;
    public static CommandSettings CPS;

    public static ClickListener teams;
    public static ClickListener Inv;
    public static ClickListener AttackAnimal;
    public static ClickListener AttackMobs;
    public static ClickListener ABFix;
    public static ClickListener AutoBlock;
    public static ClickListener InGUI;

    public static Entity LockEntity;
    public static float RotationYaw;
    public static float RotationPitch;
    public static ClickListener ThroughWall;
    private TimeHelper TimeHelper = new TimeHelper();
    private TimeHelper TimeHelper2 = new TimeHelper();
    static float fov;
    boolean a;
    private static float LastYaw;
    private static float LastPitch;
    static double helper;
    private float YawBefore;
    private float PitchBefore;
    private static float YawChangeTo;
    private static float PitchChangeTo;
    private static ClickListener NoRotation;
    private static ClickListener AutoBlockSendPacket;
    private static ClickListener LegitBlockFix;
    private static boolean needBlock;
    boolean b;
    public Random rand;
    ScaledResolution sr = new ScaledResolution(mc);
    private static boolean isBlocking;
    public static ClickListener TargetESP;
    private Entity BlockEntity;
    public static CommandSettings KaMode;
    public static CommandSettings PitchOffset;
    public static CommandSettings YawOffset;
    private static int TickCounter;
    protected long l;
    public NewKillaura(){
        super("NewKillaura","",c4.combat2,0,-1);
        this.a = true;
        this.b = false;
        this.rand = new Random();
        this.l = -1L;
        KaMode = new CommandSettings("KillAura Mode",1,1,2,1);
        range = new CommandSettings(GuiSettings.a(new char[]{'R', 'a', 'n', 'g', 'e'}), 3.7, 1.0, 8.0, 0.1);
        angle = new CommandSettings(GuiSettings.a(new char[]{'A', 'n', 'g', 'l', 'e'}), 360.0, 0.0, 360.0, 0.1);
        CPS = new CommandSettings(GuiSettings.a(new char[]{ 'C', 'P', 'S'}), 15.0, 1.0, 20.0, 0.1);
        SwitchDelay = new CommandSettings("SwitchDelay",100,1,1000,10);
        teams = new ClickListener(GuiSettings.a(new char[]{'T', 'e', 'a', 'm', 's'}), true);
        Inv = new ClickListener(GuiSettings.a(new char[]{'A', 't', 't', 'a', 'c', 'k', 'I', 'n', 'v'}), false);
        AttackAnimal = new ClickListener(GuiSettings.a(new char[]{'H', 'i', 't', 'A', 'n', 'i', 'm', 'a', 'l'}), false);
        AttackMobs = new ClickListener(GuiSettings.a(new char[]{'H', 'i', 't', 'M', 'o', 'b', 's'}), false);
        PitchOffset = new CommandSettings("PitchOffset",0,0,15,0.5);
        YawOffset = new CommandSettings("YawOffset",0,0,1,0.01);
        ABFix = new ClickListener("ABFix",true);
        AutoBlock = new ClickListener("AutoBlock",false);
        BlockRange = new CommandSettings("BlockRange", 3.00, 1.00, 8.00, 0.05);
        TargetESP = new ClickListener("TargetESP",true);
        InGUI = new ClickListener("InGUI",false);
        NoRotation = new ClickListener("NoRotation",false);
        AutoBlockSendPacket = new ClickListener("AutoBlockSendPacket",false);
        LegitBlockFix = new ClickListener("LegitBlockFix",false);
        ThroughWall = new ClickListener("ThroughWall",false);
        this.avav(KaMode);
        this.avav(range);
        this.avav(angle);
        this.avav(CPS);
        this.avav(SwitchDelay);
        this.avav(teams);
        this.avav(Inv);
        this.avav(CriticalMode);
        this.avav(AttackAnimal);
        this.avav(AttackMobs);
        this.avav(ABFix);
        this.avav(AutoBlock);
        this.avav(BlockRange);
        this.avav(TargetESP);
        this.avav(InGUI);
        this.avav(NoRotation);
        this.avav(PitchOffset);
        this.avav(YawOffset);
        this.avav(AutoBlockSendPacket);
        this.avav(LegitBlockFix);
        this.avav(ThroughWall);
    }
    @SubscribeEvent
    public void Tick(final RenderLivingEvent.Specials.Pre d) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(LockEntity != null && d.entity == LockEntity){
            GuiSettings.HUD.drawBoxAroundEntity(LockEntity,1,0D,0D,Color.white.getRGB(),false);
            GuiSettings.HUD.drawBoxAroundEntity(LockEntity,2,0D,0D,Color.RED.getRGB(),false);
        }
    }
    public static void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, x, y + 8);
        //mc.getRenderItem().renderItemOverlayIntoGUINameTags(mc.fontRendererObj, stack, x - 1, y + 10, null);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        //NameTags.renderEnchantText(stack, x, y);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame()){
            return true;
        }
        if(en && object instanceof C03PacketPlayer && !(object instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(object instanceof C03PacketPlayer.C05PacketPlayerLook) && !(object instanceof C03PacketPlayer.C06PacketPlayerPosLook) && LockEntity != null){
            return false;
        }
        if(en && object instanceof C03PacketPlayer && !NoRotation.i() && LockEntity != null){
            if(object instanceof  C03PacketPlayer.C04PacketPlayerPosition){
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(RotationYaw,RotationPitch,mc.thePlayer.onGround));
                return false;
            }
            YawChangeTo = (float)(RotationYaw - YawOffset.g3tV4l4u3() * 2 + YawOffset.g3tV4l4u3() * Math.random());
            if(YawChangeTo > 180) {YawChangeTo = 180;}
            if (YawChangeTo < -180) {YawChangeTo = -180;}
            PitchChangeTo = (float)(RotationPitch + PitchOffset.g3tV4l4u3() * Math.random());
            if(PitchChangeTo > 90) {PitchChangeTo = 90;}
            if (PitchChangeTo < -90) {PitchChangeTo = -90;}
            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,YawChangeTo,"yaw","field_149476_e");
            ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object, PitchChangeTo,"pitch","field_149473_f");
            if(LastPitch != PitchChangeTo || LastYaw != YawChangeTo){
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,true,"rotating","field_149481_i");
                LastYaw = YawChangeTo;
                LastPitch = PitchChangeTo;
            }else {
                ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,false,"rotating","field_149481_i");
            }
        }
        return true;
    }

    @Override
    public void en() {
        TimeHelper.reset();
        TimeHelper2.reset();
        en = true;
        BlockEntity = null;
        LockEntity = null;
        RotationYaw = 0;
        RotationPitch = 0;
        fov = 0;
        YawBefore = 0;
        PitchBefore = 0;
        YawChangeTo = 0;
        PitchChangeTo = 0;
        needBlock = false;
        if(isBlocking){
            unblock();
        }
        isBlocking=false;
    }
    @Override
    public void dd(){
        TimeHelper.reset();
        TimeHelper2.reset();
        en=false;
        BlockEntity = null;
        LockEntity = null;
        RotationYaw = 0;
        RotationPitch = 0;
        fov = 0;
        YawBefore = 0;
        PitchBefore = 0;
        YawChangeTo = 0;
        PitchChangeTo = 0;
        needBlock = false;
        if(isBlocking){
            unblock();
        }
        isBlocking=false;
    }
    @SubscribeEvent
    public void Res(PlayerEvent.PlayerRespawnEvent P){
        if(P.player == mc.thePlayer){
            TimeHelper.reset();
            TimeHelper2.reset();
            BlockEntity = null;
            LockEntity = null;
            RotationYaw = 0;
            RotationPitch = 0;
            fov = 0;
            YawBefore = 0;
            PitchBefore = 0;
            YawChangeTo = 0;
            PitchChangeTo = 0;
            needBlock = false;
            if(isBlocking){
                unblock();
            }
            isBlocking=false;
        }
    }
    @SubscribeEvent
    public void ab(TickEvent.PlayerTickEvent event){
        if(!GuiSettings.isPlayerInGame() || (InGUI.i() && mc.currentScreen != null)){
            return;
        }
        if(AutoBlock.i() && BetterSword.IsSword(mc.thePlayer.inventory.getCurrentItem()) && !((EntityLivingBase)mc.thePlayer).isDead && !mc.thePlayer.isSpectator() /* && !isblocking */){
            if(AutoBlockSendPacket.i()){
                if(needBlock && !isBlocking){
                    block();
                    isBlocking=true;
                }else if(isBlocking){
                    unblock();
                    isBlocking=false;
                }
            }else if(needBlock) {
                mc.thePlayer.setItemInUse(mc.thePlayer.inventory.getCurrentItem(), 20);
            }
        }
        if(TimeHelper2.isDelayComplete(1000/CPS.g3tV4l4u3()) && LockEntity != null && !LegitBlockFix.i()){
            if(!isMoving() && mc.thePlayer.rotationPitch == PitchBefore && mc.thePlayer.rotationYaw == YawBefore && !NoRotation.i() && !((EntityLivingBase)mc.thePlayer).isDead && !mc.thePlayer.isSpectator()){
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,RotationYaw,RotationPitch,mc.thePlayer.onGround));
            }else{
                PitchBefore = mc.thePlayer.rotationPitch;
                YawBefore = mc.thePlayer.rotationYaw;
            }
            mc.thePlayer.swingItem();
            if(ABFix.i() && (mc.thePlayer.isBlocking() || (AutoBlockSendPacket.i() && isBlocking))){
                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
            }
            AttackEntity(LockEntity);
            if(ABFix.i() && (mc.thePlayer.isBlocking() || (AutoBlockSendPacket.i() && isBlocking))){
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
            }
            TimeHelper2.reset();
        }
        if(LegitBlockFix.i() && LockEntity != null){
            if(!isMoving() && mc.thePlayer.rotationPitch == PitchBefore && mc.thePlayer.rotationYaw == YawBefore && !NoRotation.i() && !((EntityLivingBase)mc.thePlayer).isDead && !mc.thePlayer.isSpectator()){
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,RotationYaw,RotationPitch,mc.thePlayer.onGround));
            }else{
                PitchBefore = mc.thePlayer.rotationPitch;
                YawBefore = mc.thePlayer.rotationYaw;
            }
            if(mc.thePlayer.isBlocking()){
                if(TickCounter == 0){
                    unblock();
                    TickCounter++;
                }
                if(TickCounter == 1 && TimeHelper2.isDelayComplete(1000/CPS.g3tV4l4u3())){
                    mc.thePlayer.swingItem();
                    AttackEntity(LockEntity);
                    TickCounter++;
                    TimeHelper2.reset();
                }
                if(TickCounter == 2){
                    block();
                    TickCounter = 0;
                }
            }else{
                mc.thePlayer.swingItem();
                if(ABFix.i() && (mc.thePlayer.isBlocking() || (AutoBlockSendPacket.i() && isBlocking))){
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
                }
                AttackEntity(LockEntity);
                if(ABFix.i() && (mc.thePlayer.isBlocking() || (AutoBlockSendPacket.i() && isBlocking))){
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
                }
                TickCounter = 0;
            }
        }
        if(TimeHelper.isDelayComplete(SwitchDelay.g3tV4l4u3())){
            if (mc.theWorld == null) {
                return;
            }
            if (mc.thePlayer == null) {
                return;
            }
            if (!mc.thePlayer.isEntityAlive()) {
                return;
            }
            Entity v = null;
            double dd = NewKillaura.angle.g3tV4l4u3();
            final float range = (float) NewKillaura.range.g3tV4l4u3();
            for (final Entity e : getTargets()) {
                if (e.equals(mc.thePlayer)) {
                    continue;
                }
                if(!needBlock){
                    needBlock = InBlockRange(e);
                    if(needBlock){
                        BlockEntity = e;
                    }
                }else if (!InBlockRange(BlockEntity)){
                    needBlock = false;
                }
                if (!this.v(e)) {
                    continue;
                }
                final float yaw = g(e)[1];
                final double cd = v(yaw, mc.thePlayer.rotationYaw);
                if (dd <= cd) {
                    continue;
                }
                v = e;
                dd = cd;
            }
            if(v!=null){
                LockEntity = v;
                RotationYaw = mc.thePlayer.getRotationYawHead() + fov;
                RotationPitch = getPitch(v);
                }else{
                LockEntity = null;
                RotationYaw = mc.thePlayer.getRotationYawHead();
                RotationPitch = mc.thePlayer.rotationPitch;
            }
            TimeHelper.reset();
        }
    }
    public float getPitch(Entity e){
        if(mc.thePlayer.posY+mc.thePlayer.getEyeHeight() > e.posY+e.getEyeHeight()){
            double TmpY = mc.thePlayer.posY+mc.thePlayer.getEyeHeight() - (e.posY+e.getEyeHeight());
            double TmpX = Math.abs(mc.thePlayer.posX - e.posX);
            double TmpZ = Math.abs(mc.thePlayer.posZ - e.posZ);
            double OnXZDistance = Math.sqrt(TmpX*TmpX + TmpZ*TmpZ);
            return (float) Math.toDegrees(Math.atan(TmpY/OnXZDistance));
        }
        if(mc.thePlayer.posY + mc.thePlayer.getEyeHeight() < e.posY+e.getEyeHeight()){
            double TmpY = e.posY+e.getEyeHeight() - (mc.thePlayer.posY+ mc.thePlayer.getEyeHeight());
            double TmpX = Math.abs(mc.thePlayer.posX - e.posX);
            double TmpZ = Math.abs(mc.thePlayer.posZ - e.posZ);
            double OnXZDistance = Math.sqrt(TmpX*TmpX + TmpZ*TmpZ);
            return -(float) Math.toDegrees(Math.atan(TmpY/OnXZDistance));
        }
        if(mc.thePlayer.posY+mc.thePlayer.getEyeHeight() == e.posY+e.getEyeHeight()){
            return 0F;
        }
        return mc.thePlayer.rotationPitch;
    }
    public boolean a(final Entity t) {
        if (AttackMobs.i() && (t instanceof EntityCreature || t instanceof EntityBat || t instanceof EntitySlime || t instanceof EntitySquid)) { ///被选中，判断是否是怪物
            return true;
        }
        if (AttackAnimal.i() && t instanceof EntityAnimal) { ///被选中,判断是否是动物
            return true;
        }
        ///判断是否是玩家
        return t instanceof EntityPlayer;
    }

    /*判断是否攻击*/
    public boolean v(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!entity.isEntityAlive()) {
            return false;
        }
        if (mc.thePlayer.isSpectator()) {
            return false;
        }
        if (!this.a(entity)) {
            return false;
        }
        if (!Inv.i()) {
            if (entity.isInvisible()) {
                return false;
            }
        }
        if(!ThroughWall.i()){
            if(!mc.thePlayer.canEntityBeSeen(entity)){
                return false;
            }
        }
        if (teams.i() && entity.getDisplayName().getUnformattedText().startsWith(mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2))) {
            return false;
        }
        final float RangeA = (float) range.g3tV4l4u3();
        return mc.thePlayer.getDistanceToEntity(entity) <= RangeA;
    }
    public boolean InBlockRange(Entity E){
        if (E == null) {
            return false;
        }
        if (!E.isEntityAlive()) {
            return false;
        }
        if (mc.thePlayer.isSpectator()) {
            return false;
        }
        if (!this.a(E)) {
            return false;
        }
        if (!Inv.i()) {
            if (!mc.thePlayer.canEntityBeSeen(E)) {
                return false;
            }
        }
        if (teams.i() && E.getDisplayName().getUnformattedText().startsWith(mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2))) {
            return false;
        }
        final float RangeF = (float) BlockRange.g3tV4l4u3();
        return mc.thePlayer.getDistanceToEntity(E) <= RangeF;
    }
    public static float[] g(final Entity entity) {
        final double x = entity.posX - mc.thePlayer.posX;
        final double z = entity.posZ - mc.thePlayer.posZ;
        double y;
        if (entity instanceof EntityEnderman) {
            y = entity.posY - mc.thePlayer.posY;
        } else {
            y = entity.posY + (entity.getEyeHeight() - 1.9) - mc.thePlayer.posY + (mc.thePlayer.getEyeHeight() - 1.9);
        }
        helper = MathHelper.sqrt_double(x * x + z * z);
        float bnn = (float) Math.toDegrees(-Math.atan(x / z));
        final float e = (float) (-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            bnn = (float) (90.0 + Math.toDegrees(Math.atan(z / x)));
        } else if (z < 0.0 && x > 0.0) {
            bnn = (float) (-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        return new float[]{e, bnn};
    }


    public long gg() {
        return System.nanoTime() / 1000000L;
    }

    public boolean h(final long MS) {
        return this.gg() >= this.l + MS;
    }

    public void ud() {
        this.l = this.gg();
    }

    public static double v(final float angle1, final float angle2) {
        fov = angle1 - angle2 % 360.0f;
        float dd = Math.abs(angle1 - angle2) % 360.0f;
        if (dd > 180.0f) {
            dd = 360.0f - dd;
        }
        return dd;
    }
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F);
    }
    public static void unblock() {
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
    }
    public static void block(){
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f));
    }
    public java.util.List<EntityLivingBase> getTargets() {
        Stream<EntityLivingBase> stream = this.mc.theWorld.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityLivingBase)
                .map(entity -> (EntityLivingBase) entity)
                .filter(NewKillaura::isValidAttack);
        List<EntityLivingBase> list = stream.collect(Collectors.toList());
        return list;
    }
    public static void AttackEntity(Entity E){
        if(KaMode.g3tV4l4u3() == 1){
            mc.playerController.attackEntity(mc.thePlayer,E);
        } else if (KaMode.g3tV4l4u3() == 2) {
            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(E,C02PacketUseEntity.Action.ATTACK));
        }
    }

    public static boolean isValidAttack(EntityLivingBase entityLivingBase) {
        return isValid(entityLivingBase);
    }
    public static boolean isValid(EntityLivingBase entity) {
        if (entity.isDead || entity.getHealth() <= 0 || entity == mc.thePlayer) {
            return false;
        }

        // not armor standdddddd
        if (entity instanceof EntityArmorStand) {
            return false;
        }

        return true;
    }
}
