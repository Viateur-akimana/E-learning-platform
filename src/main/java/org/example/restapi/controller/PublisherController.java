package org.example.restapi.controller;

import org.example.restapi.model.Publisher;
import org.example.restapi.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        Optional<Publisher> publisherOptional = publisherService.getPublisherById(id);
        return publisherOptional.map(publisher -> ResponseEntity.ok().body(publisher))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

        @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher) {
        Optional<Publisher> existingPublisher = publisherService.getPublisherByName(publisher.getName());
        if (existingPublisher.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Publisher with name " + publisher.getName() + " already exists");
        } else {
            Publisher savedPublisher = publisherService.savePublisher(publisher);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
        try {
            Publisher updatedPublisher = publisherService.updatePublisher(id, publisher);
            return ResponseEntity.ok(updatedPublisher);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
