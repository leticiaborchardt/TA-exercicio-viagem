package com.example.viagemdestino.controllers;

import com.example.viagemdestino.dtos.DestinationDto;
import com.example.viagemdestino.models.DestinationModel;
import com.example.viagemdestino.repositories.DestinationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    @Autowired
    DestinationRepository destinationRepository;

    @GetMapping
    public ResponseEntity<List<DestinationModel>> getAllDestinations() {
        return ResponseEntity.status(HttpStatus.OK).body(destinationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDestinationById(@PathVariable(value = "id") Long id) {
        Optional<DestinationModel> destinationO = destinationRepository.findById(id);

        if (destinationO.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(destinationO.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination not found.");
    }

    /**
     * Endpoint adicional - Busca destinos pelo nome do país
     */
    @GetMapping("country/{country}")
    public ResponseEntity<Object> getDestinationByCountry(@PathVariable(value = "country") String country) {
        return ResponseEntity.status(HttpStatus.OK).body(destinationRepository.findAllByCountryIgnoreCase(country));
    }

    @PostMapping
    public ResponseEntity<DestinationModel> createDestination(@RequestBody @Valid DestinationDto destinationDTO) {
        DestinationModel destinationModel = new DestinationModel();
        BeanUtils.copyProperties(destinationDTO, destinationModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(destinationRepository.save(destinationModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDestination(@PathVariable(value = "id") Long id, @RequestBody @Valid DestinationDto destinationDTO) {
        Optional<DestinationModel> destinationO = destinationRepository.findById(id);

        if (destinationO.isPresent()) {
            var destinationModel = destinationO.get();
            BeanUtils.copyProperties(destinationDTO, destinationModel);

            return ResponseEntity.status(HttpStatus.OK).body(destinationRepository.save(destinationModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDestinationById(@PathVariable(value = "id") Long id) {
        Optional<DestinationModel> destinationO = destinationRepository.findById(id);

        if (destinationO.isPresent()) {
            destinationRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Destination deleted successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination not found.");
    }
}
