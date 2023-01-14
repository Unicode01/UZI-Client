package io.fishermen.fpsdisplay.settings;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.timechanger.TimeHelper;

public class FastUse extends GuiSettings {
    private final TimeHelper timer = new TimeHelper();
    public static CommandSettings mode = new CommandSettings("Mode",1,1,3,1);
    public FastUse(){
        super("FastUse","",c4.Player,0,-1);
        this.avav(mode);
    }
    @Override
    public void en(){
        this.timer.reset();
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent a){
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        Item usingItem;
        if(mode.g3tV4l4u3() == 1){
            if (mc.thePlayer.isUsingItem()) {
                usingItem = mc.thePlayer.getItemInUse().getItem();
                if ((usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || usingItem instanceof ItemPotion)/* && this.timer.hasReached(750L)*/) {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getItemInUse()));

                    for(int i = 0; i < 39; ++i) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
                    }

                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    mc.playerController.onStoppedUsingItem(mc.thePlayer);
                    this.timer.reset();
                }

                if (!mc.thePlayer.isUsingItem()) {
                    this.timer.reset();
                }
            }
        } else if (mode.g3tV4l4u3() == 2) {
            if (mc.thePlayer.isUsingItem()) {
                usingItem = mc.thePlayer.getItemInUse().getItem();
                if ((usingItem instanceof ItemFood || usingItem instanceof ItemBucketMilk || usingItem instanceof ItemPotion) && mc.thePlayer.getItemInUseDuration() >= 1) {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }

                if (!mc.thePlayer.isUsingItem()) {
                    this.timer.reset();
                }
            }
        }
    }
}
