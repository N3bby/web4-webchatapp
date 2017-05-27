package com.razacx.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razacx.domain.model.PrivateMessage;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.ChatSocketUserConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/privatechat", configurator = ChatSocketUserConfigurator.class)
public class PrivateChatSocket {
    
//    Not in use

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

        System.out.println("PrivateChat: Message received from user '" + session.getUserProperties().get("person") + "': '" + data + "'");

        //Deserialize incoming json
        PrivateMessageTO privateMessageTO = new Gson().fromJson(data, PrivateMessageTO.class);

        //Create PrivateMessage and persist
        PrivateMessage message = new PrivateMessage();
        message.setFrom(DomainServiceHolder.getInstance().getPersonService().getPerson((String) session.getUserProperties().get("person")));
        message.setTo(DomainServiceHolder.getInstance().getPersonService().getPerson(privateMessageTO.to));
        message.setMessage(privateMessageTO.message);

        DomainServiceHolder.getInstance().getMessageService().addMessage(message);

        //Forward
        forward(message);

    }

    private class PrivateMessageTO {

        private String to;
        private String message;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    private void forward(PrivateMessage message) throws IOException {

        //Get from and to sessions
        List<Session> recipients = sessions.stream()
                                           .filter(s -> s.getUserProperties().get("person").equals(message.getFrom().getUsername()) ||
                                                        s.getUserProperties().get("person").equals(message.getTo().getUsername()))
                                           .collect(Collectors.toList());

        //Convert PrivateMessage to json
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(message);

        //Send json to both recipients
        for (Session s : recipients) {            
            s.getBasicRemote().sendText(json);
        }

    }

    @OnClose
    public void onClose(Session session) {

        //Remove session from the set
        sessions.remove(session);

        System.out.println("PrivateChat: Connection closed for user '" + session.getUserProperties().get("person") + "'");

    }

    @OnError
    public void onError(Throwable error) {

        if (error.getMessage().contains("An established connection was aborted by the software in your host machine"))
            return;
        error.printStackTrace();

    }

}
