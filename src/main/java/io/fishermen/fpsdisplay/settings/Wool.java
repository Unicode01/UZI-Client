// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import java.nio.ByteBuffer;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.gui.GuiScreen;
import java.lang.reflect.Field;
import java.util.Random;
import java.lang.reflect.Method;

import pw.cinque.event.Connection;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;

public class Wool extends GuiSettings
{
    public static CommandSettings mcc;
    public static CommandSettings mca;
    public static ClickListener iff;
    public static ClickListener bh;
    public static ClickListener DisWhenBreakBlocks;
    private long nlu;
    private long nld;
    private long nru;
    private long nrd;
    private long nd;
    private long ne;
    private double drr;
    private boolean dr;
    private Method gg;
    public Random r;
    private static Field bst;
    private static Field fff;
    private static Field bff;
    private static boolean isBreakingBlocks;
    private static BlockPos blockPos;
    
    public Wool() {
        super(GuiSettings.a(new char[] { 'A', 'u', 't', 'o', 'C', 'l', 'i', 'c', 'k' }), "", c4.combat1, 0, 0);
        this.r = new Random();
        Wool.mcc = new CommandSettings(GuiSettings.a(new char[] { 'M', 'i', 'n', 'C', 'P', 'S' }), 15.0, 1.0, 20.0, 0.1);
        Wool.mca = new CommandSettings(GuiSettings.a(new char[] { 'M', 'a', 'x', 'C', 'P', 'S' }), 15.0, 1.0, 20.0, 0.1);
        Wool.iff = new ClickListener(GuiSettings.a(new char[] { 'I', 'n', 'v', ' ', 'F', 'i', 'l', 'l' }), true);
        Wool.bh = new ClickListener(GuiSettings.a(new char[] { 'B', 'l', 'o', 'c', 'k', ' ', 'H', 'i', 't' }), true);
        Wool.DisWhenBreakBlocks = new ClickListener("DisWhenBreakBlocks",true);
        this.avav(Wool.mcc);
        this.avav(Wool.mca);
        this.avav(Wool.bh);
        this.avav(Wool.iff);
        this.avav(Wool.DisWhenBreakBlocks);
        try {
            this.gg = GuiScreen.class.getDeclaredMethod("mouseClicked", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (object instanceof C07PacketPlayerDigging && !mc.playerController.extendedReach() && mc.playerController != null) {
            C07PacketPlayerDigging c07PacketPlayerDigging = (C07PacketPlayerDigging) object;
            if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                isBreakingBlocks = true;
                blockPos = c07PacketPlayerDigging.getPosition();
            } else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                isBreakingBlocks = false;
            }
        }
    }

    @SubscribeEvent
    public void onTick(final TickEvent.RenderTickEvent e) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (Wool.mc.currentScreen == null) {
            Mouse.poll();
            if(blockPos != null && mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air && isBreakingBlocks){
                isBreakingBlocks = false;
            }
            if(isBreakingBlocks && DisWhenBreakBlocks.i()){
                return;
            }
            if (Mouse.isButtonDown(0)) {
                if (this.nld > 0L && this.nlu > 0L) {
                    if (System.currentTimeMillis() > this.nld) {
                        final int attackKeyBind = Wool.mc.gameSettings.keyBindAttack.getKeyCode();
                        KeyBinding.setKeyBindState(attackKeyBind, true);
                        KeyBinding.onTick(attackKeyBind);
                        s(0, true);
                        this.vcx();
                    }
                    else if (System.currentTimeMillis() > this.nlu) {
                        KeyBinding.setKeyBindState(Wool.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                        s(0, false);
                    }
                }
                else {
                    this.vcx();
                }
                final boolean bh = Wool.bh.i();
                if (bh && Mouse.isButtonDown(1)) {
                    if (this.nrd > 0L && this.nru > 0L) {
                        if (System.currentTimeMillis() > this.nrd) {
                            KeyBinding.setKeyBindState(Wool.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                            KeyBinding.onTick(Wool.mc.gameSettings.keyBindUseItem.getKeyCode());
                            s(1, true);
                            this.vcxx();
                        }
                        else if (System.currentTimeMillis() > this.nru) {
                            final int useItemKeyBind = Wool.mc.gameSettings.keyBindUseItem.getKeyCode();
                            KeyBinding.setKeyBindState(useItemKeyBind, false);
                            s(1, false);
                        }
                    }
                    else {
                        this.vcxx();
                    }
                }
                else {
                    final long n = 0L;
                    this.nru = 0L;
                    this.nrd = 0L;
                }
            }
            else {
                final long n2 = 0L;
                this.nru = 0L;
                this.nrd = 0L;
                this.nlu = 0L;
                this.nld = 0L;
            }
        }
        else if (Wool.mc.currentScreen instanceof GuiInventory) {
            if (Mouse.isButtonDown(0) && (Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42))) {
                final boolean inventoryFill = Wool.iff.i();
                if (!inventoryFill) {
                    return;
                }
                if (this.nlu == 0L || this.nld == 0L) {
                    this.vcx();
                    return;
                }
                if (System.currentTimeMillis() > this.nld) {
                    this.vcx();
                    this.c(Wool.mc.currentScreen);
                }
            }
            else {
                final long n3 = 0L;
                this.nru = 0L;
                this.nrd = 0L;
                this.nlu = 0L;
                this.nld = 0L;
            }
        }
    }
    
    private void vcx() {
        final double mcc = Wool.mcc.g3tV4l4u3();
        final double mca = Wool.mca.g3tV4l4u3();
        if (mcc > mca) {
            return;
        }
        final double CPS = mcc + this.r.nextDouble() * (mca - mcc);
        long delay = (int)Math.round(1000.0 / CPS);
        if (System.currentTimeMillis() > this.nd) {
            if (!this.dr && this.r.nextInt(100) >= 85) {
                this.dr = true;
                this.drr = 1.1 + this.r.nextDouble() * 0.15;
            }
            else {
                this.dr = false;
            }
            this.nd = System.currentTimeMillis() + 500L + this.r.nextInt(1500);
        }
        if (this.dr) {
            delay *= (long)this.drr;
        }
        if (System.currentTimeMillis() > this.ne) {
            if (this.r.nextInt(100) >= 80) {
                delay += 50L + this.r.nextInt(150);
            }
            this.ne = System.currentTimeMillis() + 500L + this.r.nextInt(1500);
        }
        this.nld = System.currentTimeMillis() + delay;
        this.nlu = System.currentTimeMillis() + delay / 2L - this.r.nextInt(10);
    }
    
    public static void s(final int button, final boolean state) {
        final MouseEvent m = new MouseEvent();
        Wool.fff.setAccessible(true);
        try {
            Wool.fff.set(m, button);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Wool.fff.setAccessible(false);
        Wool.bst.setAccessible(true);
        try {
            Wool.bst.set(m, state);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Wool.bst.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)m);
        try {
            Wool.bff.setAccessible(true);
            final ByteBuffer buffer = (ByteBuffer)Wool.bff.get(null);
            Wool.bff.setAccessible(false);
            buffer.put(button, (byte)(state ? 1 : 0));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private void vcxx() {
        final double mcc = 4.0;
        final double mca = 6.0;
        final double CPS = 4.0 + this.r.nextDouble() * 2.0;
        final long delay = (int)Math.round(1000.0 / CPS);
        this.nrd = System.currentTimeMillis() + delay;
        this.nru = System.currentTimeMillis() + 20L + this.r.nextInt(30);
    }
    
    private void c(final GuiScreen s) {
        final int var1 = Mouse.getX() * s.width / Wool.mc.displayWidth;
        final int var2 = s.height - Mouse.getY() * s.height / Wool.mc.displayHeight - 1;
        final int var3 = 0;
        try {
            this.gg.setAccessible(true);
            this.gg.invoke(s, var1, var2, 0);
            this.gg.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        try {
            Wool.fff = MouseEvent.class.getDeclaredField("button");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            Wool.bst = MouseEvent.class.getDeclaredField("buttonstate");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            Wool.bff = Mouse.class.getDeclaredField("buttons");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
