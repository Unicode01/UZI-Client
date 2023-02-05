package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;

public class AutoGapple extends GuiSettings{
    private int slotBefore;
    private boolean isEating;
    public static CommandSettings health;
    public AutoGapple(){
        super("AutoGapple","",c4.combat1,0,-1);
        health = new CommandSettings("Health", 10, 0, 20, 1);
        this.avav(health);
    }
    @SubscribeEvent
    public void e(TickEvent.PlayerTickEvent e){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(mc.thePlayer.getHealth() < health.g3tV4l4u3() && !isEating){
            slotBefore = mc.thePlayer.inventory.currentItem;
            int Gapple = getGapple();
            if(Gapple == -1) return;
            mc.thePlayer.inventory.currentItem = Gapple;
            //mc.thePlayer.setItemInUse(mc.thePlayer.inventory.getCurrentItem(), (int) EatTicks.g3tV4l4u3());
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(),true);
            isEating = true;
        }
        if(isEating && mc.thePlayer.getHealth() >= health.g3tV4l4u3()){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(),false);
            isEating = false;
            mc.thePlayer.inventory.currentItem = slotBefore;
        }
    }
    private int getGapple(){
        if (mc.thePlayer.isDead || mc.thePlayer.isSpectator()) {
            return -1;
        }
        for(int i=0;i<10;i++){
            if(getItemBySlot(i).getItem() == Items.golden_apple){
                return i;
            }
        }
        return -1;
    }
    private static ItemStack getItemBySlot(int slot) {
        return mc.thePlayer.inventory.mainInventory[slot] == null ? new ItemStack(Blocks.air) : mc.thePlayer.inventory.mainInventory[slot];
    }
}
