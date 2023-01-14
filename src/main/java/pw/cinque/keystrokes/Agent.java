package pw.cinque.keystrokes;

import net.minecraft.launchwrapper.LaunchClassLoader;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void agentmain(String args, Instrumentation instrumentation) throws Exception {
        for (Class<?> classes : instrumentation.getAllLoadedClasses()) {
            if (classes.getName().startsWith("net.minecraft.client.Minecraft")) {
                LaunchClassLoader classLoader = (LaunchClassLoader) classes.getClassLoader();
                classLoader.addURL(Agent.class.getProtectionDomain().getCodeSource().getLocation());
                Class<?> client = classLoader.loadClass(KeystrokesMod.class.getName());
                client.newInstance();
            }
        }
    }
}