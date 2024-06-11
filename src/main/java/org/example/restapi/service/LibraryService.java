package org.example.restapi.service;

import org.example.restapi.model.Library;
import org.example.restapi.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    public Optional<Library> getLibraryById(Long id) {
        return libraryRepository.findById(id);
    }

    public Optional<Library> getLibraryByName(String name) {
        return libraryRepository.findByName(name);
    }

    public Library getOrCreateLibraryByName(String name) {
        Optional<Library> existingLibrary = libraryRepository.findByName(name);
        return existingLibrary.orElseGet(() -> {
            Library newLibrary = new Library();
            newLibrary.setName(name);
            return libraryRepository.save(newLibrary);
        });
    }

    public Library saveLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }
}
