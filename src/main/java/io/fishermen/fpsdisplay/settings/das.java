// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import io.fishermen.fpsdisplay.InvEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Random;
import pw.cinque.CommandSettings.CommandSettings;

public class das extends GuiSettings
{
    protected long l;
    protected long g;
    public static CommandSettings d;
    protected Random rr;
    private static long t;
    
    public das() {
        super(GuiSettings.a(new char[] { 'A', 'u', 't', 'o', 'A', 'r', 'm', 'o', 'r'  }), "", c4.Player, 0, -1);
        this.l = -1L;
        this.rr = new Random();
        this.avav(das.d = new CommandSettings(GuiSettings.a(new char[] { 'D', 'e', 'l', 'a', 'y' }), 100.0, 0.0, 300.0, 1.0));
    }
    @Override
    public void en(){
        InvEvent.AutoArmor = true;
    }
    @Override
    public void dd(){
        InvEvent.AutoArmor = false;
    }

    public static void Armor(){
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (das.mc.thePlayer != null && das.mc.currentScreen instanceof GuiInventory) {
            int s = -1;
            double m = -1.0;
            int n = -1;
            for (int i = 9; i < 45; ++i) {
                final ItemStack b = das.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (b != null && (g(b) || (f(b) && !g(b)))) {
                    if (f(b) && n == -1) {
                        n = s(b);
                    }
                    final double v = h(b);
                    if (v >= m) {
                        s = i;
                        m = v;
                    }
                }
            }
            if (s != -1 && s((long)das.d.g3tV4l4u3())) {
                if (n != -1) {
                    das.mc.playerController.windowClick(das.mc.thePlayer.inventoryContainer.windowId, 4 + n, 1, 4, (EntityPlayer)das.mc.thePlayer);
                }
                das.mc.playerController.windowClick(das.mc.thePlayer.inventoryContainer.windowId, s, 0, 1, (EntityPlayer)das.mc.thePlayer);
            }
        }
    }
    
    public static boolean s(final long t) {
        if (pp() >= t) {
            das.t = System.nanoTime() / 1000000L;
            return true;
        }
        return false;
    }
    
    public static long pp() {
        return System.nanoTime() / 1000000L - das.t;
    }
    
    public static boolean f(final ItemStack c) {
        if (c.getItem() instanceof ItemArmor) {
            if (das.mc.thePlayer.getEquipmentInSlot(1) != null && c.getUnlocalizedName().contains("boots") && h(c) + ((ItemArmor)c.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(1)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                return true;
            }
            if (das.mc.thePlayer.getEquipmentInSlot(2) != null && c.getUnlocalizedName().contains("leggings") && h(c) + ((ItemArmor)c.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(2)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                return true;
            }
            if (das.mc.thePlayer.getEquipmentInSlot(3) != null && c.getUnlocalizedName().contains("chestplate") && h(c) + ((ItemArmor)c.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(3)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                return true;
            }
            if (das.mc.thePlayer.getEquipmentInSlot(4) != null && c.getUnlocalizedName().contains("helmet") && h(c) + ((ItemArmor)c.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(4)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(4).getItem()).damageReduceAmount) {
                return true;
            }
        }
        return false;
    }
    
    private static int s(final ItemStack s) {
        if (s.getItem() instanceof ItemArmor) {
            if (das.mc.thePlayer.getEquipmentInSlot(1) != null && s.getUnlocalizedName().contains("boots") && h(s) + ((ItemArmor)s.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(1)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                return 4;
            }
            if (das.mc.thePlayer.getEquipmentInSlot(2) != null && s.getUnlocalizedName().contains("leggings") && h(s) + ((ItemArmor)s.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(2)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                return 3;
            }
            if (das.mc.thePlayer.getEquipmentInSlot(3) != null && s.getUnlocalizedName().contains("chestplate") && h(s) + ((ItemArmor)s.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(3)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                return 2;
            }
            if (das.mc.thePlayer.getEquipmentInSlot(4) != null && s.getUnlocalizedName().contains("helmet") && h(s) + ((ItemArmor)s.getItem()).damageReduceAmount > h(das.mc.thePlayer.getEquipmentInSlot(4)) + ((ItemArmor)das.mc.thePlayer.getEquipmentInSlot(4).getItem()).damageReduceAmount) {
                return 1;
            }
        }
        return -1;
    }
    
    private static boolean g(final ItemStack s) {
        return (das.mc.thePlayer.getEquipmentInSlot(1) == null && s.getUnlocalizedName().contains("boots")) || (das.mc.thePlayer.getEquipmentInSlot(2) == null && s.getUnlocalizedName().contains("leggings")) || (das.mc.thePlayer.getEquipmentInSlot(3) == null && s.getUnlocalizedName().contains("chestplate")) || (das.mc.thePlayer.getEquipmentInSlot(4) == null && s.getUnlocalizedName().contains("helmet"));
    }
    
    private static double h(final ItemStack s) {
        if (!(s.getItem() instanceof ItemArmor)) {
            return 0.0;
        }
        return ((ItemArmor)s.getItem()).damageReduceAmount + (100 - ((ItemArmor)s.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, s) * 4 * 0.0075;
    }
    
    static {
        das.t = 0L;
    }
}
