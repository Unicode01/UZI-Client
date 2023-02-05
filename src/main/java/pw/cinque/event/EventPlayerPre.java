package pw.cinque.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventPlayerPre extends Event {

    private boolean isPre;
    private float yaw;
    private float pitch;
    private double x;
    private double y;
    private double z;
    private boolean onground;
    private boolean alwaysSend;
    private boolean sneaking;
    public static float YAW;
    public static float PITCH;
    public static float PREVYAW;
    public static float PREVPITCH;
    public static boolean SNEAKING;





    public boolean isPre() {
        return this.isPre;
    }

    public boolean isPost() {
        return !this.isPre;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public boolean isOnground() {
        return this.onground;
    }

    public void setSneaking(boolean flag) {
        this.sneaking = flag;
    }

    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public void setX(double d0) {
        this.x = d0;
    }

    public void setY(double d0) {
        this.y = d0;
    }

    public void setZ(double d0) {
        this.z = d0;
    }

    public void setGround(boolean flag) {
        this.onground = flag;
    }

    public void setAlwaysSend(boolean flag) {
        this.alwaysSend = flag;
    }

    public void setOnGround(boolean flag) {
        this.onground = flag;
    }
}
