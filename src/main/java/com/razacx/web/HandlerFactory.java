package com.razacx.web;

import com.razacx.domain.service.concrete.DomainServiceHolder;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerFactory {

    private Map<String, ActionHandler> handlers = new HashMap<>();

    public HandlerFactory(DomainServiceHolder serviceHolder) {
        Reflections reflections = new Reflections("web");
        Set<Class<?>> handlerClasses = reflections.getTypesAnnotatedWith(Action.class);
        for (Class<?> c : handlerClasses) {
            try {
                ActionHandler handler = (ActionHandler) c.getConstructor(DomainServiceHolder.class).newInstance(serviceHolder);
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
