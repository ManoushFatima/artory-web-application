package com.artory.artory.controller;

import com.artory.artory.entity.ContactMessage;
import com.artory.artory.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactMessage> submitContactMessage(@RequestBody ContactMessage message) {
        ContactMessage savedMessage = contactService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }
}
