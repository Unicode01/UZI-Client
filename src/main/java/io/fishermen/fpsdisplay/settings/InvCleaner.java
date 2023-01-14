package io.fishermen.fpsdisplay.settings;


import io.fishermen.fpsdisplay.InvEvent;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.timechanger.ClickListener;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.timechanger.IItemSword;
import pw.cinque.timechanger.IItemTools;
import pw.cinque.timechanger.TimeHelper;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Iterator;

public class InvCleaner extends GuiSettings {
    public static int pickaxeSlot = 37;
    public static int axeSlot = 38;
    public static TimeHelper delayTimer = new TimeHelper();
    public static int shovelSlot = 39;
    private static ClickListener keepTools = new ClickListener("Tools",false);
    private static ClickListener keepArmor = new ClickListener("Armor",false);
    private static ClickListener keepBow = new ClickListener("Bow",false);
    private static ClickListener keepBucket = new ClickListener("Bucket",false);
    private static ClickListener keepArrow = new ClickListener("Arrow",false);
    private static ClickListener inInv = new ClickListener("OnlyInv",true);
    private static ClickListener noMove = new ClickListener("No Move",true);
    private static CommandSettings delay = new CommandSettings("Delay", 80.0, 0.0, 1000.0, 10.0);
    private static double handitemAttackValue;
    private static int currentSlot = 9;
    public InvCleaner(){
        super("InvCleaner","",c4.Player,0,-1);
        this.avav(keepTools);
        this.avav(keepArmor);
        this.avav(keepBow);
        this.avav(keepBucket);
        this.avav(keepArrow);
        this.avav(inInv);
        this.avav(noMove);
        this.avav(delay);
    }
    public static boolean isShit(int slot) {
        ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
        if (itemStack == null) {
            return false;
        } else if (itemStack.getItem() == Items.stick) {
            return true;
        } else if (itemStack.getItem() == Items.egg) {
            return true;
        } else if (itemStack.getItem() == Items.bone) {
            return true;
        } else if (itemStack.getItem() == Items.bowl) {
            return true;
        } else if (itemStack.getItem() == Items.glass_bottle) {
            return true;
        } else if (itemStack.getItem() == Items.string) {
            return true;
        } else if (itemStack.getItem() == Items.flint && getItemAmount(Items.flint) > 1) {
            return true;
        } else if (itemStack.getItem() == Items.compass && getItemAmount(Items.compass) > 1) {
            return true;
        } else if (itemStack.getItem() == Items.feather) {
            return true;
        } else if (itemStack.getItem() == Items.fishing_rod) {
            return true;
        } else if (itemStack.getItem() == Items.bucket && !keepBucket.i()) {
            return true;
        } else if (itemStack.getItem() == Items.lava_bucket && !keepBucket.i()) {
            return true;
        } else if (itemStack.getItem() == Items.water_bucket && !keepBucket.i()) {
            return true;
        } else if (itemStack.getItem() == Items.milk_bucket && !keepBucket.i()) {
            return true;
        } else if (itemStack.getItem() == Items.arrow && !keepArrow.i()) {
            return true;
        } else if (itemStack.getItem() == Items.snowball) {
            return true;
        } else if (itemStack.getItem() == Items.fish) {
            return true;
        } else if (itemStack.getItem() == Items.experience_bottle) {
            return true;
        } else if (!(itemStack.getItem() instanceof ItemTool) || keepTools.i() && isBestTool(itemStack)) {
            if (!(BetterSword.IsSword(itemStack)) || keepTools.i() && isBestSword(itemStack)) {
                if (!(das.f(itemStack)) || keepArmor.i() && isBestArmor(itemStack)) {
                    if (itemStack.getItem() instanceof ItemBow && (!keepBow.i() || !isBestBow(itemStack))) {
                        return true;
                    } else {
                        return itemStack.getItem().getUnlocalizedName().contains("potion") ? isBadPotion(itemStack) : false;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private static int getItemAmount(Item shit) {
        int result = 0;
        Iterator var2 = mc.thePlayer.inventoryContainer.inventorySlots.iterator();

        while(var2.hasNext()) {
            Slot item = (Slot)var2.next();
            if (item.getHasStack() && item.getStack().getItem() == shit) {
                ++result;
            }
        }

        return result;
    }

    private static boolean isBestTool(ItemStack input) {
        Iterator var1 = new ArrayList(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(0, 35)).iterator();

        ItemStack itemStack;
        do {
            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    if (!var1.hasNext()) {
                                        return true;
                                    }

                                    itemStack = (ItemStack)var1.next();
                                } while(itemStack == null);
                            } while(!(itemStack.getItem() instanceof ItemTool));
                        } while(itemStack == input);
                    } while(itemStack.getItem() instanceof ItemPickaxe && !(input.getItem() instanceof ItemPickaxe));
                } while(itemStack.getItem() instanceof ItemAxe && !(input.getItem() instanceof ItemAxe));
            } while(itemStack.getItem() instanceof ItemSpade && !(input.getItem() instanceof ItemSpade));
        } while(!(getToolEffencly(itemStack) >= getToolEffencly(input)));

        return false;
    }

    private static boolean isBestSword(ItemStack input) {
        Iterator var1 = new ArrayList(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(0, 35)).iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack == null || !(BetterSword.IsSword(itemStack)) || itemStack == input || !(getSwordAttackDamage(itemStack) >= getSwordAttackDamage(input)));

        return false;
    }

    private static boolean isBestBow(ItemStack input) {
        Iterator var1 = new ArrayList(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(0, 35)).iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack == null || !(itemStack.getItem() instanceof ItemBow) || itemStack == input || !(getBowAttackDamage(itemStack) >= getBowAttackDamage(input)));

        return false;
    }

    private static boolean isBestArmor(ItemStack input) {
        Iterator var1 = new ArrayList(Arrays.asList(mc.thePlayer.inventory.mainInventory).subList(0, 35)).iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                ItemStack[] var5 = mc.thePlayer.inventory.armorInventory;
                int var6 = var5.length;

                for(int var3 = 0; var3 < var6; ++var3) {
                    itemStack = var5[var3];
                    if (itemStack != null && itemStack.getItem() instanceof ItemArmor && itemStack != input && ((ItemArmor)itemStack.getItem()).armorType == ((ItemArmor)input.getItem()).armorType && getArmorScore(itemStack) >= getArmorScore(input)) {
                        return false;
                    }
                }

                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack == null || !(itemStack.getItem() instanceof ItemArmor) || itemStack == input || ((ItemArmor)itemStack.getItem()).armorType != ((ItemArmor)input.getItem()).armorType || !(getArmorScore(itemStack) >= getArmorScore(input)));

        return false;
    }

    private static boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion)stack.getItem();
            Iterator var2 = potion.getEffects(stack).iterator();

            while(var2.hasNext()) {
                PotionEffect o = (PotionEffect)var2.next();
                if (o.getPotionID() == Potion.poison.getId() || o.getPotionID() == Potion.moveSlowdown.getId() || o.getPotionID() == Potion.harm.getId()) {
                    return true;
                }
            }
        }

        return false;
    }

    private static double getSwordAttackDamage(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof ItemSword) {
            ItemSword sword = (ItemSword)itemStack.getItem();
            return (double)sword.getDamageVsEntity() + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25 + (double)(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack));
        } else {
            return 0.0;
        }
    }

    private static double getBowAttackDamage(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ItemBow ? (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack) + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 0.1 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) * 0.1 : 0.0;
    }

    private static double getToolEffencly(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof ItemTool) {
            IItemTools sword = (IItemTools)itemStack.getItem();
            return (double)((float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) + sword.getEfficiencyOnProperMaterial());
        } else {
            return 0.0;
        }
    }
    @Override
    public void en() {
        this.currentSlot = 9;
        this.handitemAttackValue = getSwordAttackDamage(mc.thePlayer.getHeldItem());
        InvEvent.InvCleaner = true;
    }
    @Override
    public void dd(){
        InvEvent.InvCleaner = false;
    }
    public static void CleanInv(){
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (!(mc.currentScreen instanceof GuiChest)) {
            if (!noMove.i() || !isMoving()) {
                if (currentSlot >= 45) {
                    currentSlot = 9;
                    if (mc.thePlayer.ticksExisted % 40 == 0) {
                        getBestAxe();
                        getBestPickaxe();
                        getBestShovel();
                    }
                }

                if (!inInv.i() || mc.currentScreen instanceof GuiInventory) {
                    handitemAttackValue = getSwordAttackDamage(mc.thePlayer.getHeldItem());
                    ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(currentSlot).getStack();
                    if (delayTimer.isDelayComplete(delay.g3tV4l4u3())) {
                        if (isShit(currentSlot) && getSwordAttackDamage(itemStack) <= handitemAttackValue && itemStack != mc.thePlayer.getHeldItem()) {
                            mc.playerController.windowClick(0, currentSlot, 1, 4, mc.thePlayer);
                            delayTimer.reset();
                        }

                        ++currentSlot;
                    }
                }

            }
        }
    }

    public static void getBestShovel() {
        for(int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isBestShovel(is) && shovelSlot != i && isBestWeapon(is)) {
                    if (!mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getHasStack()) {
                        swap(i, shovelSlot - 36);
                    } else if (!isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack())) {
                        swap(i, shovelSlot - 36);
                    }
                }
            }
        }

    }
    public static boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        } else {
            float value = getToolEffect(stack);

            for(int i = 9; i < 45; ++i) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
    public static void getBestPickaxe() {
        for(int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isBestPickaxe(is) && pickaxeSlot != i && isBestWeapon(is)) {
                    if (!mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getHasStack()) {
                        swap(i, pickaxeSlot - 36);
                    } else if (!isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack())) {
                        swap(i, pickaxeSlot - 36);
                    }
                }
            }
        }

    }
    public static void swap(int slot, int hotbarNum) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
    }
    public static boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        } else {
            float value = getToolEffect(stack);

            for(int i = 9; i < 45; ++i) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
    public static float getArmorScore(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
            float score = 0.0F;
            score += (float)itemArmor.damageReduceAmount;
            if (EnchantmentHelper.getEnchantments(itemStack).size() <= 0) {
                score = (float)((double)score - 0.1);
            }

            int protection = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack);
            score = (float)((double)score + (double)protection * 0.2);
            return score;
        } else {
            return -1.0F;
        }
    }
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F);
    }
    public static void getBestAxe() {
        for(int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (isBestAxe(is) && axeSlot != i && isBestWeapon(is)) {
                    if (!mc.thePlayer.inventoryContainer.getSlot(axeSlot).getHasStack()) {
                        swap(i, axeSlot - 36);
                    } else if (!isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack())) {
                        swap(i, axeSlot - 36);
                    }
                }
            }
        }
    }
    public static boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        } else {
            float value = getToolEffect(stack);

            for(int i = 9; i < 45; ++i) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (getToolEffect(is) > value && is.getItem() instanceof ItemAxe && isBestWeapon(stack)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
    public static float getToolEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0F;
        } else {
            String name = item.getUnlocalizedName();
            ItemTool tool = (ItemTool)item;
            float value = 1.0F;
            if (item instanceof ItemPickaxe) {
                value = tool.getStrVsBlock(stack, Blocks.stone);
                if (name.toLowerCase().contains("gold")) {
                    value -= 5.0F;
                }
            } else if (item instanceof ItemSpade) {
                value = tool.getStrVsBlock(stack, Blocks.dirt);
                if (name.toLowerCase().contains("gold")) {
                    value -= 5.0F;
                }
            } else {
                if (!(item instanceof ItemAxe)) {
                    return 1.0F;
                }

                value = tool.getStrVsBlock(stack, Blocks.log);
                if (name.toLowerCase().contains("gold")) {
                    value -= 5.0F;
                }
            }

            value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075);
            value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0);
            return value;
        }
    }
    public static boolean isBestWeapon(ItemStack stack) {
        float damage = getDamage(stack);

        for(int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getDamage(is) > damage && is.getItem() instanceof ItemSword) {
                    return true;
                }
            }
        }

        return !(stack.getItem() instanceof ItemSword);
    }
    public static float getDamage(ItemStack stack) {
        float damage = 0.0F;
        Item item = stack.getItem();
        if (item instanceof ItemTool) {
            IItemTools tool = (IItemTools)item;
            damage += tool.getdamageVsEntity();
        }

        if (item instanceof ItemSword) {
            ItemSword sword = (ItemSword)item;
            damage += sword.getDamageVsEntity();
        }

        damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01F;
        return damage;
    }
}
