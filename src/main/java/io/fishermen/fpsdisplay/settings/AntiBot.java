package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import java.util.Iterator;
import java.util.List;

public class AntiBot extends GuiSettings{
    public AntiBot(){
        super(GuiSettings.a(new char[]{'A','n','t','i','B','o','t'}),"",c4.Player,0,-1);
    }
    @SubscribeEvent
    public void onPre(final RenderLivingEvent.Specials.Pre d) {
        if(GuiSettings.isPlayerInGame()){
            Entity e = d.entity;
            if(e == null){
                return;
            }
            if (e.getName().startsWith("§") && e.getName().contains("§c") || this.isEntityBot(e) && !e.getDisplayName().getFormattedText().contains("NPC") && e != mc.thePlayer) {
                mc.theWorld.removeEntity(e);
            }
        }
    }

    public static boolean isBotCheck(Entity e){
        if (e.getName().startsWith("§") && e.getName().contains("§c") || isEntityBot(e) && !e.getDisplayName().getFormattedText().contains("NPC") && e != mc.thePlayer) {
            return true;
        }
        return false;
    }
    private static boolean isOnTab(Entity entity) {
        Iterator var2 = mc.getNetHandler().getPlayerInfoMap().iterator();

        NetworkPlayerInfo info;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            info = (NetworkPlayerInfo)var2.next();
        } while(!info.getGameProfile().getName().equals(entity.getName()));

        return true;
    }

    public static boolean isEntityBot(Entity entity) {
        double distance = entity.getDistanceSqToEntity(mc.thePlayer);
        if (!(entity instanceof EntityPlayer)) {
            return false;
        } else if (mc.getCurrentServerData() == null) {
            return false;
        } else {
            return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && entity.getDisplayName().getFormattedText().startsWith("&") || !isOnTab(entity) && mc.thePlayer.ticksExisted > 100;
        }
    }

    public static boolean isInTabList(EntityLivingBase entity) {
        for (NetworkPlayerInfo item : mc.getNetHandler().getPlayerInfoMap()) {
            if (item != null && item.getGameProfile() != null && item.getGameProfile().getName().contains(entity.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isNPC(EntityLivingBase entity) {
        if (entity == null) {
            return true;
        }

        if (entity == mc.thePlayer) {
            return true;
        }

        if (!(entity instanceof EntityPlayer)) {
            return false; // ALLOW ALL MOBS
        }

        if (entity.ticksExisted <= 10 * 20)
            return false;

        if (entity.isPlayerSleeping()) {
            return true;
        }

        return false;
    }

    public static boolean isHypixelNPC(Entity entity) {
        String formattedName = entity.getDisplayName().getFormattedText();

        if (!formattedName.startsWith("\247") && formattedName.endsWith("\247r")) {
            return true;
        }

        if (formattedName.contains("[NPC]")) {
            return true;
        }
        return false;
    }

}
