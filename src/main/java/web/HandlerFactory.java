package web;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerFactory {

    private Map<String, ActionHandler> handlers = new HashMap<String, ActionHandler>();

    public HandlerFactory() {
        Reflections reflections = new Reflections("web");
        Set<Class<?>> handlerClasses = reflections.getTypesAnnotatedWith(Action.class);
        for (Class<?> c : handlerClasses) {
            try {
                ActionHandler handler = (ActionHandler) c.newInstance();
                handlers.put(c.getAnnotation(Action.class).value(), handler);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ActionHandler getHandler(String action) {
        return handlers.get(action);
    }

}
