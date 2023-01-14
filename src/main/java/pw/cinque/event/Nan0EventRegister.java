package pw.cinque.event;

import com.google.common.reflect.TypeToken;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MinecraftDummyContainer;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.ASMEventHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Nan0EventRegister {

    public static void register(EventBus bus, Object target) {
        ConcurrentHashMap listeners = (ConcurrentHashMap) ReflectionHelper.getPrivateValue(EventBus.class, bus, new String[] { "listeners"});
        Map listenerOwners = (Map) ReflectionHelper.getPrivateValue(EventBus.class, bus, new String[] { "listenerOwners"});

        if (!listeners.containsKey(target)) {
            MinecraftDummyContainer activeModContainer = Loader.instance().getMinecraftModContainer();

            listenerOwners.put(target, activeModContainer);
            ReflectionHelper.setPrivateValue(EventBus.class, bus, listenerOwners, new String[] { "listenerOwners"});
            Set supers = TypeToken.of(target.getClass()).getTypes().rawTypes();
            Method[] amethod = target.getClass().getMethods();
            int i = amethod.length;
            int j = 0;

            while (j < i) {
                Method method = amethod[j];
                Iterator iterator = supers.iterator();

                while (true) {
                    if (iterator.hasNext()) {
                        Class cls = (Class) iterator.next();

                        try {
                            Method real = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());

                            if (!real.isAnnotationPresent(SubscribeEvent.class)) {
                                continue;
                            }

                            Class[] nameeterTypes = method.getParameterTypes();
                            Class eventType = nameeterTypes[0];

                            register(bus, eventType, target, method, activeModContainer);
                        } catch (NoSuchMethodException nosuchmethodexception) {
                            continue;
                        }
                    }

                    ++j;
                    break;
                }
            }

        }
    }

    private static void register(EventBus bus, Class eventType, Object target, Method method, ModContainer owner) {
        try {
            int busID = ((Integer) ReflectionHelper.getPrivateValue(EventBus.class, bus, new String[] { "busID"})).intValue();
            ConcurrentHashMap listeners = (ConcurrentHashMap) ReflectionHelper.getPrivateValue(EventBus.class, bus, new String[] { "listeners"});
            Constructor ctr = eventType.getConstructor(new Class[0]);

            ctr.setAccessible(true);
            Event event = (Event) ctr.newInstance(new Object[0]);
            ASMEventHandler listener = new ASMEventHandler(target, method, owner);

            event.getListenerList().register(busID, listener.getPriority(), listener);
            ArrayList others = (ArrayList) listeners.get(target);

            if (others == null) {
                others = new ArrayList();
                listeners.put(target, others);
                ReflectionHelper.setPrivateValue(EventBus.class, bus, listeners, new String[] { "listeners"});
            }

            others.add(listener);
        } catch (Exception exception) {
            ;
        }

    }
}
