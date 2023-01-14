package io.fishermen.fpsdisplay.settings;

import io.fishermen.fpsdisplay.InvEvent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.timechanger.TimeHelper;

public class BetterSword extends GuiSettings{
    private static TimeHelper timer = new TimeHelper();
    public BetterSword(){
        super("BetterSword","",c4.Player,0,-1);
    }

    @Override
    public void en(){
        InvEvent.BetterSword = true;
    }
    @Override
    public void dd(){
        InvEvent.BetterSword = false;
    }

    public static void Sword(){
        if(!GuiSettings.isPlayerInGame()){
            return;
        }

        if(!timer.isDelayComplete(200)){return;}
        if(mc.currentScreen instanceof GuiInventory){
            int slot = getBestSword(getScoreForSword(getItemBySlot(0)));

            if (slot == -1)
                return;

            swap(slot, 0);
            timer.reset();
        }
    }

    public static int getBestSword(double minimum) {
        for (int i = 0; i < 36; ++i) {
            if (mc.thePlayer.inventory.currentItem == i)
                continue;

            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];

            if (itemStack == null)
                continue;

            if (!(IsSword(itemStack)))
                continue;

            if (minimum >= getScoreForSword(itemStack))
                continue;

            return i;
        }

        return -1;
    }
    public static boolean IsSword(ItemStack item){
        if(item == null){
            return false;
        }
        return item.getItem().getUnlocalizedName().contains("sword")  && !((EntityLivingBase)mc.thePlayer).isDead && !mc.thePlayer.isSpectator();
    }
    public static double getScoreForSword(final ItemStack itemStack){
        if(!(IsSword(itemStack)))
            return 0;

        ItemSword itemSword = (ItemSword) itemStack.getItem();

        double result = 1.0;

        result += itemSword.getDamageVsEntity();

        result += 1.25 * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
        result += 0.5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);

        return result;
    }

    public static void swap(int from, int to) {
        if(from <= 8){
            from = 36 + from;
        }

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, from, to, 2, mc.thePlayer);
    }
    public static ItemStack getItemBySlot(int slot) {
        return mc.thePlayer.inventory.mainInventory[slot] == null ? new ItemStack(Blocks.air) : mc.thePlayer.inventory.mainInventory[slot];
    }
}
