package com.example.messagingjms

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class Receiver {
    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    fun receiveMessage(email: Email) {
        println("Received <$email>")
    }
}