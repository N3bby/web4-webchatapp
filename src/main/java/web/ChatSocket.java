package web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import domain.model.Person;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/chat/{topic}", configurator = ChatSocketConfigurator.class)
public class ChatSocket {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(@PathParam("topic") String topic, Session session, EndpointConfig config) {
        //Set Person from EndpointConfig to session attributes
        session.getUserProperties().put("person", config.getUserProperties().get("person"));
        //Set topic the user is in as session attribute
        session.getUserProperties().put("topic", topic);
        //Add session to list of sessions
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        //Set the user of the message to the user of the Session
        chatMessage.setUser(((Person) session.getUserProperties().get("person")).getUsername());
        //Forward message to clients
        forwardMessage((String) session.getUserProperties().get("topic"), chatMessage);
    }

    @OnClose
    public void onClose(Session session) {
        //Remove message from Session list
        sessions.remove(session);
    }

    private void forwardMessage(String topic, ChatMessage message) throws IOException {
        //Get all Sessions within the same topic
        List<Session> sessionsInTopic = sessions.stream()
                .filter(s -> s.getUserProperties().get("topic").equals(topic))
                .collect(Collectors.toList());
        //Format the message into json
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String messageData = gson.toJson(message);
        for (Session s : sessionsInTopic) {
            //Send json to every client
            s.getBasicRemote().sendText(messageData);
        }
    }

    public class ChatMessage {

        @Expose
        private String user;
        @Expose
        private String message;

        public ChatMessage() {
        }

        public ChatMessage(String user, String message) {
            this.user = user;
            this.message = message;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
