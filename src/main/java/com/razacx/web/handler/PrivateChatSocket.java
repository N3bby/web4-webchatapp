package com.razacx.web.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razacx.domain.model.Message;
import com.razacx.domain.model.Person;
import com.razacx.domain.model.Topic;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.ChatSocketUserConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/privatechat", configurator = ChatSocketUserConfigurator.class)
public class PrivateChatSocket {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(@PathParam("topic") String topic, Session session, EndpointConfig config) {

        //Fetch person from EndpointConfig and add it as a session attribute (string)
        session.getUserProperties().put("person", config.getUserProperties().get("person"));
        //Add session to set of sessions
        sessions.add(session);

        System.out.println("PrivateChat: Connection opened for user '" + config.getUserProperties().get("person") + "'");

    }

    @OnMessage
    public void onMessage(Session session, String data) throws IOException {

        //TODO Add for
        System.out.println("PrivateChat: Message received from user '" + session.getUserProperties().get("person") + "': '" + data + "'");

    }

    @OnClose
    public void onClose(Session session) {

        //Remove session from the set
        sessions.remove(session);

        System.out.println("PrivateChat: Connection closed for user '" + session.getUserProperties().get("person") + "'");

    }

    @OnError
    public void onError(Throwable error) {

        if(error.getMessage().contains("An established connection was aborted by the software in your host machine")) return;
        error.printStackTrace();

    }

}
