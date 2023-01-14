// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import java.awt.*;
import java.lang.reflect.Field;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import pw.cinque.event.EventSlowDown;

import java.util.ArrayList;

public abstract class GuiSettings
{
    protected ArrayList<pw.cinque.CommandSettings.GuiSettings> v;
    public static boolean HurtTick;
    public String n;
    private String d;
    private c4 cc;
    private boolean en;
    private int kb;
    protected static Minecraft mc;
    private boolean p;
    private int ccc;

    public static void bop(double s) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (float)(forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (float)(forward > 0.0D ? 45 : -45);
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            double rad = Math.toRadians(yaw + 90.0F);
            double sin = Math.sin(rad);
            double cos = Math.cos(rad);
            mc.thePlayer.motionX = forward * s * cos + strafe * s * sin;
            mc.thePlayer.motionZ = forward * s * sin - strafe * s * cos;
        }

    }
    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F;
    }
    
    public GuiSettings(final String n, final String d, final c4 cc, final int b, final int c) {
        this.p = false;
        this.n = n;
        this.d = d;
        this.cc = cc;
        this.kb = b;
        this.en = false;
        GuiSettings.mc = Minecraft.getMinecraft();
        this.v = new ArrayList<pw.cinque.CommandSettings.GuiSettings>();
        this.ccc = c;
    }
    
    public static GuiSettings g3tM0dul3(final Class<? extends GuiSettings> a) {
        for (final GuiSettings m : CommandSettings.m) {
            if (m.getClass() == a) {
                return m;
            }
        }
        return null;
    }
    
    public GuiSettings(final String n, final c4 c) {
        this.p = false;
        this.n = n;
        this.cc = c;
        this.kb = 0;
        this.en = false;
    }

    public static boolean isPlayerInGame() {
        return mc.thePlayer != null && mc.theWorld != null;
    }
    public void kb() {
        if (this.kb == 0) {
            return;
        }
        if (!this.p && Keyboard.isKeyDown(this.kb)) {
            this.t();
            this.p = true;
        }
        if (!Keyboard.isKeyDown(this.kb)) {
            this.p = false;
        }
    }
    
    public void tt() {
        this.en = false;
    }
    
    public int ccc() {
        return this.ccc;
    }

    public String g3t4() {
        return this.n;
    }
    
    public ArrayList<pw.cinque.CommandSettings.GuiSettings> v() {
        return this.v;
    }
    
    public void avav(final pw.cinque.CommandSettings.GuiSettings v) {
        this.v.add(v);
    }
    
    public c4 cc() {
        return this.cc;
    }
    
    public boolean g3t() {
        return this.en;
    }
    
    public void en() {
    }
    
    public void dd() {
    }
    
    public void t() {
        final boolean en = !this.en;
        this.en = en;
        if (!en) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            FMLCommonHandler.instance().bus().unregister((Object)this);
            this.dd();
        }
        if (this.en) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            FMLCommonHandler.instance().bus().register((Object)this);
            this.en();
        }
    }

    
    public void ti() {
    }
    
    public void l() {
    }
    
    public void rz() {
    }
    
    public void uy() {
    }
    
    public int g() {
        return this.kb;
    }
    
    public static String a(final char[] chars) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public void dsadsa() {
        for (final pw.cinque.CommandSettings.GuiSettings v : this.v()) {
            v.a();
        }
        nn(this.n);
        this.n = null;
    }
    
    public void sb(final int kb) {
        this.kb = kb;
    }
    
    public static void nn(final String s) {
        Field d = null;
        try {
            d = String.class.getDeclaredField("value");
        }
        catch (NoSuchFieldException e) {
            return;
        }
        d.setAccessible(true);
        char[] a = null;
        try {
            a = (char[])d.get(s);
        }
        catch (IllegalAccessException e2) {
            return;
        }
        for (int i = 3; i < a.length; ++i) {
            char ch = a[i];
            a[i] = '\0';
            ch = '\0';
            a[i] = ch;
        }
        try {
            d.set(s, a);
            d.setAccessible(false);
        }
        catch (Exception ex) {}
    }

    public enum c4
    {
        combat1,
        combat2,
        movement,
        render,
        Player,
        Tests;
    }

    public static class HUD {

        public static final int rc = -1089466352;
        private static final double p2 = 6.283185307179586D;
        private static final Minecraft mc = Minecraft.getMinecraft();
        public static boolean ring_c = false;

        public static void re(BlockPos bp, int color, boolean shade) {
            if (bp != null) {
                double x = (double)bp.getX() - mc.getRenderManager().viewerPosX;
                double y = (double)bp.getY() - mc.getRenderManager().viewerPosY;
                double z = (double)bp.getZ() - mc.getRenderManager().viewerPosZ;
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glLineWidth(2.0F);
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                float a = (float)(color >> 24 & 255) / 255.0F;
                float r = (float)(color >> 16 & 255) / 255.0F;
                float g = (float)(color >> 8 & 255) / 255.0F;
                float b = (float)(color & 255) / 255.0F;
                GL11.glColor4d(r, g, b, a);
                RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
                if (shade) {
                    dbb(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), r, g, b);
                }

                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
            }
        }

        public static void drawBoxAroundEntity(Entity e, int type, double expand, double shift, int color, boolean damage) {
            if (e instanceof EntityLivingBase) {
                double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
                double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
                double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
                float d = (float)expand / 40.0F;
                if (e instanceof EntityPlayer && damage && ((EntityPlayer)e).hurtTime != 0) {
                    color = Color.RED.getRGB();
                }

                GlStateManager.pushMatrix();
                if (type == 3) {
                    GL11.glTranslated(x, y - 0.2D, z);
                    GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
                    GlStateManager.disableDepth();
                    GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
                    int outline = Color.black.getRGB();
                    net.minecraft.client.gui.Gui.drawRect(-20, -1, -26, 75, outline);
                    net.minecraft.client.gui.Gui.drawRect(20, -1, 26, 75, outline);
                    net.minecraft.client.gui.Gui.drawRect(-20, -1, 21, 5, outline);
                    net.minecraft.client.gui.Gui.drawRect(-20, 70, 21, 75, outline);
                    if (color != 0) {
                        net.minecraft.client.gui.Gui.drawRect(-21, 0, -25, 74, color);
                        net.minecraft.client.gui.Gui.drawRect(21, 0, 25, 74, color);
                        net.minecraft.client.gui.Gui.drawRect(-21, 0, 24, 4, color);
                        net.minecraft.client.gui.Gui.drawRect(-21, 71, 25, 74, color);
                    } else {
                        int st = Client.rainbowDraw(2L, 0L);
                        int en = Client.rainbowDraw(2L, 1000L);
                        dGR(-21, 0, -25, 74, st, en);
                        dGR(21, 0, 25, 74, st, en);
                        net.minecraft.client.gui.Gui.drawRect(-21, 0, 21, 4, en);
                        net.minecraft.client.gui.Gui.drawRect(-21, 71, 21, 74, st);
                    }

                    GlStateManager.enableDepth();
                } else {
                    int i;
                    if (type == 4) {
                        EntityLivingBase en = (EntityLivingBase)e;
                        double r = en.getHealth() / en.getMaxHealth();
                        int b = (int)(74.0D * r);
                        int hc = r < 0.3D ? Color.red.getRGB() : (r < 0.5D ? Color.orange.getRGB() : (r < 0.7D ? Color.yellow.getRGB() : Color.green.getRGB()));
                        GL11.glTranslated(x, y - 0.2D, z);
                        GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
                        GlStateManager.disableDepth();
                        GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
                        i = (int)(21.0D + shift * 2.0D);
                        net.minecraft.client.gui.Gui.drawRect(i, -1, i + 5, 75, Color.black.getRGB());
                        net.minecraft.client.gui.Gui.drawRect(i + 1, b, i + 4, 74, Color.darkGray.getRGB());
                        net.minecraft.client.gui.Gui.drawRect(i + 1, 0, i + 4, b, hc);
                        GlStateManager.enableDepth();
                    } else if (type == 6) {
                        d3p(x, y, z, 0.699999988079071D, 45, 1.5F, color, color == 0);
                    } else {
                        if (color == 0) {
                            color = Client.rainbowDraw(2L, 0L);
                        }

                        float a = (float)(color >> 24 & 255) / 255.0F;
                        float r = (float)(color >> 16 & 255) / 255.0F;
                        float g = (float)(color >> 8 & 255) / 255.0F;
                        float b = (float)(color & 255) / 255.0F;
                        if (type == 5) {
                            GL11.glTranslated(x, y - 0.2D, z);
                            GL11.glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
                            GlStateManager.disableDepth();
                            GL11.glScalef(0.03F + d, 0.03F, 0.03F + d);
                            int base = 1;
                            d2p(0.0D, 95.0D, 10, 3, Color.black.getRGB());

                            for(i = 0; i < 6; ++i) {
                                d2p(0.0D, 95 + (10 - i), 3, 4, Color.black.getRGB());
                            }

                            for(i = 0; i < 7; ++i) {
                                d2p(0.0D, 95 + (10 - i), 2, 4, color);
                            }

                            d2p(0.0D, 95.0D, 8, 3, color);
                            GlStateManager.enableDepth();
                        } else {
                            AxisAlignedBB bbox = e.getEntityBoundingBox().expand(0.1D + expand, 0.1D + expand, 0.1D + expand);
                            AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
                            GL11.glBlendFunc(770, 771);
                            GL11.glEnable(3042);
                            GL11.glDisable(3553);
                            GL11.glDisable(2929);
                            GL11.glDepthMask(false);
                            GL11.glLineWidth(2.0F);
                            GL11.glColor4f(r, g, b, a);
                            if (type == 1) {
                                RenderGlobal.drawSelectionBoundingBox(axis);
                            } else if (type == 2) {
                                dbb(axis, r, g, b);
                            }

                            GL11.glEnable(3553);
                            GL11.glEnable(2929);
                            GL11.glDepthMask(true);
                            GL11.glDisable(3042);
                        }
                    }
                }

                GlStateManager.popMatrix();
            }
        }

        public static void dbb(AxisAlignedBB abb, float r, float g, float b) {
            float a = 0.25F;
            Tessellator ts = Tessellator.getInstance();
            WorldRenderer vb = ts.getWorldRenderer();
            vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            ts.draw();
            vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            ts.draw();
            vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            ts.draw();
            vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            ts.draw();
            vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            ts.draw();
            vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
            vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
            ts.draw();
        }

        public static void dtl(Entity e, int color, float lw) {
            if (e != null) {
                double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
                double y = (double)e.getEyeHeight() + e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
                double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) Client.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
                float a = (float)(color >> 24 & 255) / 255.0F;
                float r = (float)(color >> 16 & 255) / 255.0F;
                float g = (float)(color >> 8 & 255) / 255.0F;
                float b = (float)(color & 255) / 255.0F;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glLineWidth(lw);
                GL11.glColor4f(r, g, b, a);
                GL11.glBegin(2);
                GL11.glVertex3d(0.0D, mc.thePlayer.getEyeHeight(), 0.0D);
                GL11.glVertex3d(x, y, z);
                GL11.glEnd();
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }

        public static void dGR(int left, int top, int right, int bottom, int startColor, int endColor) {
            int j;
            if (left < right) {
                j = left;
                left = right;
                right = j;
            }

            if (top < bottom) {
                j = top;
                top = bottom;
                bottom = j;
            }

            float f = (float)(startColor >> 24 & 255) / 255.0F;
            float f1 = (float)(startColor >> 16 & 255) / 255.0F;
            float f2 = (float)(startColor >> 8 & 255) / 255.0F;
            float f3 = (float)(startColor & 255) / 255.0F;
            float f4 = (float)(endColor >> 24 & 255) / 255.0F;
            float f5 = (float)(endColor >> 16 & 255) / 255.0F;
            float f6 = (float)(endColor >> 8 & 255) / 255.0F;
            float f7 = (float)(endColor & 255) / 255.0F;
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.shadeModel(7425);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(right, top, 0.0D).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(left, top, 0.0D).color(f1, f2, f3, f).endVertex();
            worldrenderer.pos(left, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
            worldrenderer.pos(right, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
            tessellator.draw();
            GlStateManager.shadeModel(7424);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
        }

        public static void db(int w, int h, int r) {
            int c = r == -1 ? -1089466352 : r;
            net.minecraft.client.gui.Gui.drawRect(0, 0, w, h, c);
        }

        public static void drawColouredText(String text, char lineSplit, int leftOffset, int topOffset, long colourParam1, long shift, boolean rect, FontRenderer fontRenderer) {
            int bX = leftOffset;
            int l = 0;
            long colourControl = 0L;

            for(int i = 0; i < text.length(); ++i) {
                char c = text.charAt(i);
                if (c == lineSplit) {
                    ++l;
                    leftOffset = bX;
                    topOffset += fontRenderer.FONT_HEIGHT + 5;
                    //reseting text colour?
                    colourControl = shift * (long)l;
                } else {
                    fontRenderer.drawString(String.valueOf(c), (float)leftOffset, (float)topOffset, Client.astolfoColorsDraw((int)colourParam1, (int)colourControl), rect);
                    leftOffset += fontRenderer.getCharWidth(c);
                    if (c != ' ') {
                        colourControl -= 90L;
                    }
                }
            }

        }

        public static PositionMode getPostitionMode(int marginX, int marginY, double height, double width) {
            int halfHeight = (int)(height / 4);
            int halfWidth = (int) width;
            PositionMode positionMode = null;
            // up left

            if(marginY < halfHeight) {
                if(marginX < halfWidth) {
                    positionMode = PositionMode.UPLEFT;
                }
                if(marginX > halfWidth) {
                    positionMode = PositionMode.UPRIGHT;
                }
            }

            if(marginY > halfHeight) {
                if(marginX < halfWidth) {
                    positionMode = PositionMode.DOWNLEFT;
                }
                if(marginX > halfWidth) {
                    positionMode = PositionMode.DOWNRIGHT;
                }
            }

            return positionMode;
        }

        public static void d2p(double x, double y, int radius, int sides, int color) {
            float a = (float)(color >> 24 & 255) / 255.0F;
            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(r, g, b, a);
            worldrenderer.begin(6, DefaultVertexFormats.POSITION);

            for(int i = 0; i < sides; ++i) {
                double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
                worldrenderer.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0D).endVertex();
            }

            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }

        public static void d3p(double x, double y, double z, double radius, int sides, float lineWidth, int color, boolean chroma) {
            float a = (float)(color >> 24 & 255) / 255.0F;
            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;
            mc.entityRenderer.disableLightmap();
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glDepthMask(false);
            GL11.glLineWidth(lineWidth);
            if (!chroma) {
                GL11.glColor4f(r, g, b, a);
            }

            GL11.glBegin(1);
            long d = 0L;
            long ed = 15000L / (long)sides;
            long hed = ed / 2L;

            for(int i = 0; i < sides * 2; ++i) {
                if (chroma) {
                    if (i % 2 != 0) {
                        if (i == 47) {
                            d = hed;
                        }

                        d += ed;
                    }

                    int c = Client.rainbowDraw(2L, d);
                    float r2 = (float)(c >> 16 & 255) / 255.0F;
                    float g2 = (float)(c >> 8 & 255) / 255.0F;
                    float b2 = (float)(c & 255) / 255.0F;
                    GL11.glColor3f(r2, g2, b2);
                }

                double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
                GL11.glVertex3d(x + Math.cos(angle) * radius, y, z + Math.sin(angle) * radius);
            }

            GL11.glEnd();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(true);
            GL11.glDisable(2848);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            mc.entityRenderer.enableLightmap();
        }

        public enum PositionMode {
            UPLEFT,
            UPRIGHT,
            DOWNLEFT,
            DOWNRIGHT
        }
    }
    public static class Client{

        public static boolean othersExist() {
            for(Entity wut : mc.theWorld.getLoadedEntityList()){
                if(wut instanceof EntityPlayer) return  true;
            }
            return false;
        }

        public static void setMouseButtonState(int mouseButton, boolean held) {
            MouseEvent m = new MouseEvent();

            ObfuscationReflectionHelper.setPrivateValue(MouseEvent.class, m, mouseButton, "button");
            ObfuscationReflectionHelper.setPrivateValue(MouseEvent.class, m, held, "buttonstate");
            MinecraftForge.EVENT_BUS.post(m);

            ByteBuffer buttons = ObfuscationReflectionHelper.getPrivateValue(Mouse.class, null, "buttons");
            buttons.put(mouseButton, (byte)(held ? 1 : 0));
            ObfuscationReflectionHelper.setPrivateValue(Mouse.class, null, buttons, "buttons");

        }


        public static net.minecraft.util.Timer getTimer() {
            return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
        }

        public static void resetTimer() {
            try {
                getTimer().timerSpeed = 1.0F;
            } catch (NullPointerException ignored) {}

        }
        public static int rainbowDraw(long speed, long... delay) {
            long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
            return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
        }

        public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
            float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
            while (hue > speed) {
                hue -= speed;
            }
            hue /= speed;
            if (hue > 0.5) {
                hue = 0.5F - (hue - 0.5f);
            }
            hue += 0.5F;
            return Color.HSBtoRGB(hue, 0.5f, 1F);
        }

        public static int astolfoColorsDraw(int yOffset, int yTotal) {
            return astolfoColorsDraw(yOffset, yTotal, 2900F);
        }

        public static int kopamedColoursDraw(int yOffset, int yTotal){
            float speed = 6428;
            float hue;
            try {
                hue = (float)(System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) / (yOffset / yTotal));
            } catch (ArithmeticException divisionByZero) {
                hue = (float)(System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) / ((yOffset / yTotal + 1) + 1));
            }

            while (hue > speed) {
                hue -= speed;
            }
            hue /= speed;
            if (hue > 2) {
                hue = 2F - (hue - 2f);
            }
            hue += 2F;

            float current = (System.currentTimeMillis()% speed) + ((yOffset + yTotal) * 9);

            while (current > speed) {
                current -= speed;
            }
            current /= speed;
            if (current > 2) {
                current = 2F - (current - 2f);
            }
            current += 2F;

            return Color.HSBtoRGB((current / (current - yTotal)) + current, 1f, 1F);
        }


        public static boolean currentScreenMinecraft() {
            return mc.currentScreen == null;
        }

        public static int serverResponseTime() {
            return mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
        }
        public static String reformat(String txt) {
            return txt.replace("&", "ยง");
        }
    }
}
