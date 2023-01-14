// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;

public class Boker extends GuiSettings
{
    public static String[] name;
    public static CommandSettings d;
    public static CommandSettings f;
    public static ClickListener ww;
    public static ClickListener ccc;
    public static ClickListener teams;
    
    public Boker() {
        super(GuiSettings.a(new char[] { 'A', 'i', 'm' }), "", c4.combat1, 0, 0);
        Boker.d = new CommandSettings(GuiSettings.a(new char[] { 'S', 'p', 'e', 'e', 'd' }), 35.0, 1.0, 75.0, 1.0);
        Boker.f = new CommandSettings(GuiSettings.a(new char[] { 'F', 'O', 'V' }), 70.0, 30.0, 360.0, 1.0);
        Boker.ww = new ClickListener(GuiSettings.a(new char[] { 'W', 'e', 'a', 'p', 'o', 'n' }), true);
        Boker.ccc = new ClickListener(GuiSettings.a(new char[] { 'C', 'l', 'i', 'c', 'k', 'A', 'i', 'm' }), true);
        Boker.teams = new ClickListener(GuiSettings.a(new char[] { 'T', 'e', 'a', 'm', 's' }), false);
        this.avav(Boker.d);
        this.avav(Boker.f);
        this.avav(Boker.ww);
        this.avav(Boker.ccc);
        this.avav(Boker.teams);
    }
    
    @Override
    public void ti() {
        if (Boker.mc.theWorld != null && mc.thePlayer != null) {
            if (Boker.ww.i()) {
                if (Boker.mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(Boker.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                    return;
                }
            }
            if (Boker.ccc.i() && !Boker.mc.gameSettings.keyBindAttack.isKeyDown()) {
                return;
            }
            final Entity target = this.gg();
            if (target != null && (vcx(target) > 1.0 || vcx(target) < -1.0)) {
                final boolean left = vcx(target) > 0.0;
                final EntityPlayerSP thePlayer = Boker.mc.thePlayer;
                thePlayer.rotationYaw += (float)(left ? (-(Math.abs(vcx(target)) / Boker.d.g3tV4l4u3())) : (Math.abs(vcx(target)) / Boker.d.g3tV4l4u3()));
            }
        }
    }
    public boolean IsTeam(Entity e){
        return e.getDisplayName().getUnformattedText().startsWith(mc.thePlayer.getDisplayName().getUnformattedText().substring(0, 2));
    }
    public Entity gg() {
        Entity e = null;
        int f = (int)Boker.f.g3tV4l4u3();
        for (final Object o : Boker.mc.theWorld.loadedEntityList) {
            final Entity ent = (Entity)o;
            if (ent.isEntityAlive() && !ent.isInvisible() && ent != Boker.mc.thePlayer && Boker.mc.thePlayer.getDistanceToEntity(ent) < 8.0f && this.vvczcvx(ent) && ent instanceof EntityLivingBase && Boker.mc.thePlayer.canEntityBeSeen(ent) && bvc(ent, (float)f)) {
                e = ent;
                f = (int)vcx(ent);
            }
        }
        return e;
    }
    
    public boolean vvczcvx(final Entity e) {
        if(teams.i()){
            return !IsTeam(e);
        }
        return true;
    }
    
    public static float cb(final Entity ent) {
        final double x = ent.posX - Boker.mc.thePlayer.posX;
        final double y = ent.posY - Boker.mc.thePlayer.posY;
        final double z = ent.posZ - Boker.mc.thePlayer.posZ;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        yaw = -yaw;
        double pitch = Math.asin(y / Math.sqrt(x * x + y * y + z * z)) * 57.29577951308232;
        pitch = -pitch;
        return (float)yaw;
    }
    
    public static double vcx(final Entity en) {
        return ((Boker.mc.thePlayer.rotationYaw - cb(en)) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static boolean bvc(final Entity en, float a) {
        a *= 0.5;
        final double v = ((Boker.mc.thePlayer.rotationYaw - cb(en)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (v > 0.0 && v < a) || (-a < v && v < 0.0);
    }
}
