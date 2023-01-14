//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Documents\Minecraft-Deobfuscator3000-master\1.8.9 mappings"!

// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLivingEvent;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;

import java.awt.*;

public class Zlo extends GuiSettings
{
    public static CommandSettings s;
    public static ClickListener i;
    public static ClickListener d;
    public static ClickListener h;
    
    public Zlo() {
        super(GuiSettings.a(new char[] { 'N', 'a', 'm', 'e', 'T', 'a', 'g', 's' }), "", c4.render, 0, -1);
        Zlo.s = new CommandSettings(GuiSettings.a(new char[] { 'S', 'c', 'a', 'l', 'e' }), 1.0, 0.0, 2.0, 0.1);
        Zlo.i = new ClickListener(GuiSettings.a(new char[] { 'I', 't', 'e', 'm', 's' }), true);
        Zlo.d = new ClickListener(GuiSettings.a(new char[] { 'D', 'i', 's', 't', 'a', 'n', 'c', 'e' }), false);
        Zlo.h = new ClickListener(GuiSettings.a(new char[] { 'H', 'e', 'a', 'l', 't', 'h' }), true);
        this.avav(Zlo.s);
        this.avav(Zlo.i);
        this.avav(Zlo.d);
        this.avav(Zlo.h);
    }
    
    @SubscribeEvent
    public void onPre(final RenderLivingEvent.Specials.Pre d) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (d.entity.equals((Object)Minecraft.getMinecraft().thePlayer) || !(d.entity instanceof EntityPlayer)) {
            return;
        }
        this.x((EntityPlayer)d.entity, d.x, d.y, d.z);
        d.setCanceled(true);
    }
    
    public static boolean i(final float n, final float b, final float v) {
        return n >= b && n <= v;
    }
    
    public static String g(final int l) {
        String s = "";
        for (int i = 0; i < l; ++i) {
            s += " ";
        }
        return s;
    }
    
    public static float l(final float v, final int d) {
        return (float)(Math.floor(v * Math.pow(10.0, d)) / Math.pow(10.0, d));
    }
    
    public static void d(final float x, final float y, final float w, final float h, final int c) {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        Minecraft.getMinecraft().getResourceManager();
        final float alpha = (c >> 24 & 0xFF) / 255.0f;
        final float red = (c >> 16 & 0xFF) / 255.0f;
        final float green = (c >> 8 & 0xFF) / 255.0f;
        final float blue = (c & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + w), (double)y);
        GL11.glVertex2d((double)(x + w), (double)(y + h));
        GL11.glVertex2d((double)x, (double)(y + h));
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }
    
    private void x(final EntityPlayer e, final double x, double y, final double z) {
        y += e.isSneaking() ? 0.5 : 0.7;
        String h = EnumChatFormatting.GREEN.toString();
        double heaelth = Math.ceil(e.getHealth() + e.getAbsorptionAmount()) / 2.0;
        if (Zlo.i((float)heaelth, 5.0f, 6.5f)) {
            h = EnumChatFormatting.YELLOW.toString();
        } else if (Zlo.i((float)heaelth, 0.0f, 5.0f)) {
            h = EnumChatFormatting.RED.toString();
        }
        String d = EnumChatFormatting.GREEN.toString();
        if (Zlo.i(Math.max(1.6f, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 30.0f, 50.0f)) {
            d = EnumChatFormatting.DARK_GREEN.toString();
        } else if (Zlo.i(Math.max(1.6f, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 15.0f, 30.0f)) {
            d = EnumChatFormatting.YELLOW.toString();
        } else if (Math.max(1.6f, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)) < 15.0f) {
            d = EnumChatFormatting.RED.toString();
        }
        String na = ScorePlayerTeam.formatPlayerName(e.getTeam(), e.getDisplayNameString()).replace(" ", "").trim();
        String k = (Zlo.h.i() ? h + heaelth + " " : "") + Zlo.g(Minecraft.getMinecraft().fontRendererObj.getStringWidth(na) / 3) + (Zlo.d.i() ? " " + d + Zlo.l(Math.max(1.6f, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 1) + "m" : " ");
        int w = Minecraft.getMinecraft().fontRendererObj.getStringWidth((Zlo.h.i() ? heaelth + " " : "") + Zlo.g(Minecraft.getMinecraft().fontRendererObj.getStringWidth(na) / 3) + (Zlo.d.i() ? " " + Zlo.l(Math.max(1.6f, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 1) + "m" : " ")) / 2;
        float o = (float)((double)(Math.max(1.6f, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)) / 125.0f) * s.g3tV4l4u3());
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 1.4, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        RenderManager renderManager = mc.getRenderManager();
        GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-o, -o, o);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        int f = new Color(20, 20, 20, 100).getRGB();
        Zlo.d(-w - 4, -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1), w * 2 + 5, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3, f);
        Zlo.d(k, -w - 2, -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1), -1);
        int naa = e.isInvisible() ? 754431 : (e.isSneaking() ? 12257310 : -1);
        Zlo.d(na, -w - 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(Zlo.h.i() ? h + heaelth + " " : ""), -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1), naa);
        GL11.glEnable(2929);
        GL11.glEnable(32823);
        GL11.glPolygonOffset(1.0f, -1.0E7f);
        if (i.i()) {
            int i;
            int a = 0;
            for (i = 0; i < e.inventory.armorInventory.length; ++i) {
                if (e.inventory.armorInventory[i] == null) continue;
                a -= 8;
            }
            if (e.getHeldItem() != null) {
                a -= 8;
                ItemStack r = e.getHeldItem().copy();
                if (r.hasEffect() && (r.getItem() instanceof ItemTool || r.getItem() instanceof ItemArmor)) {
                    r.stackSize = 1;
                }
                this.r(r, a, -26);
                a += 16;
            }
            for (i = 0; i < e.inventory.armorInventory.length; ++i) {
                if (e.inventory.armorInventory[i] == null) continue;
                ItemStack r2 = e.inventory.armorInventory[i].copy();
                if (r2.hasEffect() && (r2.getItem() instanceof ItemTool || r2.getItem() instanceof ItemArmor)) {
                    r2.stackSize = 1;
                }
                this.r(r2, a, -26);
                a += 16;
            }
        }
        GL11.glPolygonOffset(1.0f, 1000000.0f);
        GL11.glDisable(32823);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    private void r(final ItemStack s, final int x, final int y) {
        Zlo.mc.getRenderItem().zLevel = -50.0f;
        Zlo.mc.getRenderItem().renderItemAndEffectIntoGUI(s, x, y);
        Zlo.mc.getRenderItem().renderItemOverlayIntoGUI(Zlo.mc.fontRendererObj, s, x, y, "");
        GL11.glDisable(2896);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.x(s, x, y);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void d(final String str, final int x, final int y, final int c) {
        if (str == null) {
            return;
        }
        final boolean blend = GL11.glIsEnabled(3042);
        GL11.glDisable(3042);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(str, (float)x, (float)y, c);
        if (blend) {
            GL11.glEnable(3042);
        }
    }
    
    private void x(final ItemStack s, final int x, final int y) {
        int n = y - 24;
        if (s.getItem() instanceof ItemArmor) {
            int p = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, s);
            int t = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, s);
            int u = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, s);
            int r = EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, s);
            if (p > 0) {
                Zlo.d("pr" + p, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (t > 0) {
                Zlo.d("th" + t, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (u > 0) {
                Zlo.d("unb" + u, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (r > 0) {
                Zlo.d("ff" + p, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
        } else if (s.getItem() instanceof ItemBow) {
            int p = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, s);
            int pu = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, s);
            int f = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, s);
            int u2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, s);
            if (p > 0) {
                Zlo.d("pow" + p, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (pu > 0) {
                Zlo.d("kb" + pu, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (f > 0) {
                Zlo.d("fl" + f, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (u2 > 0) {
                Zlo.d("unb" + u2, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
        } else if (s.getItem() instanceof ItemSword) {
            int d = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, s);
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, s);
            int f = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, s);
            int u2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, s);
            if (d > 0) {
                Zlo.d("sh" + d, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (k > 0) {
                Zlo.d("kb" + k, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (f > 0) {
                Zlo.d("fa" + f, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (u2 > 0) {
                Zlo.d("unb" + u2, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
        } else if (s.getItem() instanceof ItemTool) {
            int d = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, s);
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, s);
            int f = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, s);
            int u2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, s);
            if (d > 0) {
                Zlo.d("eff" + d, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (k > 0) {
                Zlo.d("unb" + k, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (f > 0) {
                Zlo.d("slk" + f, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
            if (u2 > 0) {
                Zlo.d("for" + u2, x * 2, n, new Color(140, 140, 140).getRGB());
                n += 7;
            }
        }
    }
}
