package org.example.restapi.controller;

import org.example.restapi.model.Library;
import org.example.restapi.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<Library>> getAllLibraries() {
        List<Library> libraries = libraryService.getAllLibraries();
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibraryById(@PathVariable("id") Long id) {
        Optional<Library> library = libraryService.getLibraryById(id);
        return library.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createLibrary(@RequestBody Library library) {
        Optional<Library> existingLibrary = libraryService.getLibraryByName(library.getName());
        if (existingLibrary.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Library with name " + library.getName() + " already exists");
        } else {
            Library savedLibrary = libraryService.saveLibrary(library);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLibrary);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable("id") Long id, @RequestBody Library library) {
        library.setId(id);
        Library updatedLibrary = libraryService.saveLibrary(library);
        return new ResponseEntity<>(updatedLibrary, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable("id") Long id) {
        libraryService.deleteLibrary(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
