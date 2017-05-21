package com.razacx.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razacx.domain.model.Message;
import com.razacx.domain.model.Person;
import com.razacx.domain.model.Topic;
import com.razacx.domain.service.concrete.DomainServiceHolder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/topicchat/{topic}", configurator = ChatSocketUserConfigurator.class)
public class TopicChatSocket {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(@PathParam("topic") String topic, Session session, EndpointConfig config) {
        //Fetch person from EndpointConfig and add it as a session attribute (string)
        session.getUserProperties().put("person", config.getUserProperties().get("person"));
        //Set topic the user is in as session attribute
        session.getUserProperties().put("topic", topic);
        //Add session to set of sessions
        sessions.add(session);

        System.out.println("TopicChat: Connection opened for user '" + config.getUserProperties().get("person") + "' on topic '" + topic + "'");
        
    }

    @OnMessage
    public void onMessage(Session session, String data) throws IOException {

        System.out.println("TopicChat: Message received from user '" + session.getUserProperties().get("person") + "': '" + data + "'");
        
        //Construct message and persist
        Message message = new Message();
        message.setDate(Calendar.getInstance().getTime());
        message.setMessage(data);
        Person person = DomainServiceHolder.getInstance().getPersonService().getPerson((String) session.getUserProperties().get("person"));
        message.setFrom(person);

        DomainServiceHolder.getInstance().getMessageService().addMessage(message);
        
        //Add message to topic and update
        Topic topic = DomainServiceHolder.getInstance().getTopicService().getTopicByName((String) session.getUserProperties().get("topic"));
        topic.addMessage(message);
        
        DomainServiceHolder.getInstance().getTopicService().updateTopic(topic);
                
        //Forward message to clients
        forwardMessage((String) session.getUserProperties().get("topic"), message);
        
    }

    @OnClose
    public void onClose(Session session) {
        
        //Remove session from the set
        sessions.remove(session);

        System.out.println("TopicChat: Connection closed for user '" + session.getUserProperties().get("person") + "' on topic '" + session.getUserProperties().get("topic") + "'");
        
    }

    @OnError
    public void onError(Throwable error) {
        
        if(error.getMessage().contains("An established connection was aborted by the software in your host machine")) return;        
        error.printStackTrace();
        
    }
    
    private void forwardMessage(String topic, Message message) throws IOException {
        
        //Get all Sessions that are listening on topic
        List<Session> sessionsInTopic = sessions.stream()
                .filter(s -> s.getUserProperties().get("topic").equals(topic))
                .collect(Collectors.toList());
        
        //Format the message into json
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String messageData = gson.toJson(message);
        
        //Send json to clients
        for (Session s : sessionsInTopic) {
            s.getBasicRemote().sendText(messageData);
        }
        
    }

}
