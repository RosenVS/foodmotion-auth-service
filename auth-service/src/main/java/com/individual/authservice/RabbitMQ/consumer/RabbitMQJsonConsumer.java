package com.individual.authservice.RabbitMQ.consumer;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
    private final FirebaseAuth firebaseAuth;

    @RabbitListener(queues = "${rabbitmq.accountauthdeletion.queue.json.name}")
    public void receiveAccountAuthDeletionMessage(String userUID) {
        LOGGER.info(String.format("Deleting account auth message -> %s", userUID));

        try {
            UserRecord userRecord = firebaseAuth.getUser(userUID);
            if (userRecord != null) {
                firebaseAuth.deleteUser(userUID);
                LOGGER.info(String.format("Deleted account auth -> %s", userUID));
            } else {
                LOGGER.info(String.format("Deleting account auth failed, user not found -> %s", userUID));
            }
        } catch (FirebaseAuthException e) {
            LOGGER.error("Error deleting user: ", e);
        }
    }


}