package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.timechanger.TimeHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Aim2 extends GuiSettings{
    static Random rand = new Random();
    public static CommandSettings range;
    public static CommandSettings rotation2Value;
    public static CommandSettings rotationValue;
    public static CommandSettings speed;
    public static CommandSettings horizontal;
    public static CommandSettings vertical;
    public static CommandSettings switchsize;
    public static CommandSettings switchDelay;
    public static ClickListener attackPlayers;
    public static ClickListener attackAnimals;
    public static ClickListener attackMobs;
    public static ClickListener throughblock;
    public static ClickListener invisible;
    public static ClickListener weapon;
    public static ClickListener randoms2;
    public static ClickListener clickAim;
    public static ClickListener strafe;
    public static ArrayList targets = new ArrayList();
    public static EntityLivingBase target = null;
    public int index;
    float[] lastRotations;
    private final TimeHelper switchTimer = new TimeHelper();
    public Aim2(){
        super("Aim2","",c4.combat1,0,-1);
        this.avav(range = new CommandSettings("Range", 4.5, 0.0, 10.0,0.5));
        this.avav(attackPlayers = new ClickListener("Players", true));
        this.avav(attackAnimals = new ClickListener("Animals", false));
        this.avav(attackMobs = new ClickListener("Mobs", false));
        this.avav(throughblock = new ClickListener("ThroughBlock", true));
        this.avav(invisible = new ClickListener("Invisibles", false));
        this.avav(weapon = new ClickListener("OnlyWeapon", false));
        this.avav(rotation2Value = new CommandSettings("Yaw Offset", 200.0, 0.0, 400.0, 10.0));
        this.avav(rotationValue = new CommandSettings("Pitch Offset", 200.0, 0.0, 400.0, 10.0));
        this.avav(randoms2 = new ClickListener("Pitch Random", true));
        this.avav(speed = new CommandSettings("Speed", 0.1, 0.0, 1.0, 0.01));
        this.avav(horizontal = new CommandSettings("Horizontal", 4.0, 0.0, 10.0, 0.1));
        this.avav(vertical = new CommandSettings("Vertical", 2.0, 0.0, 10.0, 0.1));
        this.avav(switchsize = new CommandSettings("Max Targets", 1.0, 1.0, 5.0, 1.0));
        this.avav(switchDelay = new CommandSettings("Switch Delay", 50.0, 0.0, 2000.0, 10.0));
        this.avav(clickAim = new ClickListener("Click Aim", false));
        this.avav(strafe = new ClickListener("Strafe Increase", false));
    }

    public static float[] getNeededRotations(Vec3 vec) {
        Vec3 playerVector = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        double y = vec.yCoord - playerVector.yCoord;
        double x = vec.xCoord - playerVector.xCoord;
        double z = vec.zCoord - playerVector.zCoord;
        double dff = Math.sqrt(x * x + z * z);
        float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
        float pitch = (float)(-Math.toDegrees(Math.atan2(y, dff)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }

    public static Vec3 getCenter(AxisAlignedBB bb) {
        double value = Math.random();
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * ((Double)rotation2Value.g3tV4l4u3() / 400.0), bb.minY + (bb.maxY - bb.minY) * ((Boolean)randoms2.i() ? value : (Double)rotationValue.g3tV4l4u3() / 400.0), bb.minZ + (bb.maxZ - bb.minZ) * ((Double)rotation2Value.g3tV4l4u3() / 400.0));
    }
    public static boolean isOnSameTeam(Entity entity) {
        if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("§")) {
            return Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() > 2 && entity.getDisplayName().getUnformattedText().length() > 2 ? Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2)) : false;
        } else {
            return false;
        }
    }
    private static boolean isValidEntity(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            if (entity.isDead || ((EntityLivingBase)entity).getHealth() <= 0.0F) {
                return false;
            }

            if ((double)mc.thePlayer.getDistanceToEntity(entity) < (Double)range.g3tV4l4u3() && entity != mc.thePlayer && !mc.thePlayer.isDead && !(entity instanceof EntityArmorStand) && !(entity instanceof EntitySnowman)) {
                if (entity instanceof EntityPlayer && (Boolean)attackPlayers.i()) {
                    if (!mc.thePlayer.canEntityBeSeen(entity) && !(Boolean)throughblock.i()) {
                        return false;
                    }

                    if (entity.isInvisible() && !(Boolean)invisible.i()) {
                        return false;
                    }

                    return !AntiBot.isEntityBot(entity) && !isOnSameTeam(entity);
                }

                if (entity instanceof EntityMob && (Boolean)attackMobs.i()) {
                    return !AntiBot.isEntityBot(entity);
                }

                if ((entity instanceof EntityAnimal || entity instanceof EntityVillager) && (Boolean)attackAnimals.i()) {
                    return !AntiBot.isEntityBot(entity);
                }
            }
        }

        return false;
    }

    @SubscribeEvent
    public void onPre(TickEvent.PlayerTickEvent event) {
        if (mc.theWorld != null) {
            if (mc.thePlayer != null) {
                if (mc.thePlayer.isEntityAlive()) {
                    if (!this.clickAim.i() || mc.gameSettings.keyBindAttack.isKeyDown()) {
                        if (this.holdWeapon()) {
                            if (!targets.isEmpty() && this.index >= targets.size()) {
                                this.index = 0;
                            }

                            Iterator var2 = targets.iterator();

                            while(var2.hasNext()) {
                                EntityLivingBase ent = (EntityLivingBase)var2.next();
                                if (!isValidEntity(ent)) {
                                    targets.remove(ent);
                                }
                            }

                            this.getTarget(event);
                            if (targets.size() == 0) {
                                target = null;
                            } else {
                                target = (EntityLivingBase)targets.get(this.index);
                                if ((double)mc.thePlayer.getDistanceToEntity(target) > (Double)range.g3tV4l4u3()) {
                                    target = (EntityLivingBase)targets.get(0);
                                }
                            }

                            if (target != null) {
                                if (target.hurtTime == 10 && this.switchTimer.isDelayComplete((Double)this.switchDelay.g3tV4l4u3() * 1000.0) && targets.size() > 1) {
                                    this.switchTimer.reset();
                                    ++this.index;
                                }

                                this.lastRotations = getNeededRotations(getCenter(((EntityLivingBase)targets.get(this.index)).getEntityBoundingBox()));
                                if (this.lastRotations[1] > 90.0F) {
                                    this.lastRotations[1] = 90.0F;
                                } else if (this.lastRotations[1] < -90.0F) {
                                    this.lastRotations[1] = -90.0F;
                                }

                                if (target != null) {
                                    double horizontalSpeed = (Double)this.horizontal.g3tV4l4u3() * 3.0 + ((Double)this.horizontal.g3tV4l4u3() > 0.0 ? rand.nextDouble() : 0.0);
                                    double verticalSpeed = (Double)this.vertical.g3tV4l4u3() * 3.0 + ((Double)this.vertical.g3tV4l4u3() > 0.0 ? rand.nextDouble() : 0.0);
                                    if ((Boolean)this.strafe.i() && mc.thePlayer.moveStrafing != 0.0F) {
                                        horizontalSpeed *= 1.25;
                                    }

                                    if (target != null) {
                                        horizontalSpeed *= (Double)this.speed.g3tV4l4u3();
                                        verticalSpeed *= (Double)this.speed.g3tV4l4u3();
                                    }

                                    this.faceTarget(target, 0.0F, (float)verticalSpeed);
                                    this.faceTarget(target, (float)horizontalSpeed, 0.0F);
                                }
                            } else {
                                targets.clear();
                                this.lastRotations = new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
                            }

                        }
                    }
                }
            }
        }
    }

    private void getTarget(TickEvent.PlayerTickEvent event) {
        int maxSize = ((Double)this.switchsize.g3tV4l4u3()).intValue();
        Iterator var3 = mc.theWorld.loadedEntityList.iterator();

        while(var3.hasNext()) {
            Entity o3 = (Entity)var3.next();
            EntityLivingBase curEnt;
            if (o3 instanceof EntityLivingBase && isValidEntity(curEnt = (EntityLivingBase)o3) && !targets.contains(curEnt)) {
                targets.add(curEnt);
            }

            if (targets.size() >= maxSize) {
                break;
            }
        }

        targets.sort((o1, o2) -> {
            float[] rot1 = getRotationToEntity((Entity) o1);
            float[] rot2 = getRotationToEntity((Entity) o2);
            return (int)(mc.thePlayer.rotationYaw - rot1[0] - (mc.thePlayer.rotationYaw - rot2[0]));
        });
    }
    public static float[] getRotationToEntity(Entity entity) {
        double pX = Minecraft.getMinecraft().thePlayer.posX;
        double pY = Minecraft.getMinecraft().thePlayer.posY + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight();
        double pZ = Minecraft.getMinecraft().thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)entity.getEyeHeight();
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        float yaw = (float)(Math.toDegrees(Math.atan2(dZ, dX)) + 90.0);
        float pitch = (float)Math.toDegrees(Math.atan2(dH, dY));
        return new float[]{yaw, 90.0F - pitch};
    }
    public boolean holdWeapon() {
        if (!(Boolean)weapon.i()) {
            return true;
        } else {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.thePlayer.getCurrentEquippedItem() == null) {
                return false;
            } else {
                return mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword || mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemTool;
            }
        }
    }

    protected float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
        float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
        if (deltaAngle > maxIncrement) {
            deltaAngle = maxIncrement;
        }

        if (deltaAngle < -maxIncrement) {
            deltaAngle = -maxIncrement;
        }

        return currentRotation + deltaAngle / 2.0F;
    }

    private void faceTarget(Entity target, float yawspeed, float pitchspeed) {
        EntityPlayerSP player = mc.thePlayer;
        float yaw = this.lastRotations[0];
        float pitch = this.lastRotations[1];
        player.rotationYaw = this.getRotation(player.rotationYaw, yaw, yawspeed);
        player.rotationPitch = this.getRotation(player.rotationPitch, pitch, pitchspeed);
    }
}
