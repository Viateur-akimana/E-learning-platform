package org.example.restapi.service;

import org.example.restapi.model.Publisher;
import org.example.restapi.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> getPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public Optional<Publisher> getPublisherByName(String name) {
        return publisherRepository.findByName(name);
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        Optional<Publisher> existingPublisher = publisherRepository.findById(id);
        if (existingPublisher.isPresent()) {
            publisher.setId(id); // Ensure the ID is set for update
            return publisherRepository.save(publisher);
        } else {
            throw new RuntimeException("Publisher with id " + id + " not found");
        }
    }

    public Publisher getOrCreatePublisherByName(String name) {
        Optional<Publisher> existingPublisher = publisherRepository.findByName(name);
        return existingPublisher.orElseGet(() -> {
            Publisher newPublisher = new Publisher();
            newPublisher.setName(name);
            return publisherRepository.save(newPublisher);
        });
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
