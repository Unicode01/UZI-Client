package pw.cinque.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventPlayerPost extends Event {

    private float yaw;
    private float pitch;

    public EventPlayerPost(float f, float f1) {
        this.yaw = f;
        this.pitch = f1;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }
}
