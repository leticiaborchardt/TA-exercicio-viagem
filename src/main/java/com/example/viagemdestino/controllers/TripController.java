package com.example.viagemdestino.controllers;

import com.example.viagemdestino.dtos.TripDto;
import com.example.viagemdestino.models.DestinationModel;
import com.example.viagemdestino.models.TripModel;
import com.example.viagemdestino.repositories.DestinationRepository;
import com.example.viagemdestino.repositories.TripRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trip")
public class TripController {
    
    @Autowired
    TripRepository tripRepository;

    @Autowired
    DestinationRepository destinationRepository;

    @GetMapping
    public ResponseEntity<List<TripModel>> getAllTrips() {
        return ResponseEntity.status(HttpStatus.OK).body(tripRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTripById(@PathVariable(value = "id") Long id) {
        Optional<TripModel> tripO = tripRepository.findById(id);

        if (tripO.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(tripO.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found.");
    }

    @GetMapping("/destination/{destination_id}")
    public ResponseEntity<List<TripModel>> getTripsByDestinationId(@PathVariable(value = "destination_id") Long destinationId) {
        return ResponseEntity.status(HttpStatus.OK).body(tripRepository.findAllByDestinationId(destinationId));
    }

    /**
     * Endpoint adicional - Busca as viagens em um determinado país
     */
    @GetMapping("destination/country/{country}")
    public ResponseEntity<Object> getTripsByDestinationCountry(@PathVariable(value = "country") String country) {
        return ResponseEntity.status(HttpStatus.OK).body(tripRepository.findAllByDestinationCountryIgnoreCase(country));
    }

    @PostMapping
    public ResponseEntity<TripModel> createTrip(@RequestBody @Valid TripDto tripDTO) {
        TripModel tripModel = new TripModel();

        DestinationModel destination = destinationRepository.findById(tripDTO.destinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found."));

        BeanUtils.copyProperties(tripDTO, tripModel);
        tripModel.setDestination(destination);

        return ResponseEntity.status(HttpStatus.CREATED).body(tripRepository.save(tripModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTrip(@PathVariable(value = "id") Long id, @RequestBody @Valid TripDto tripDTO) {
        Optional<TripModel> tripO = tripRepository.findById(id);

        if (tripO.isPresent()) {
            var tripModel = tripO.get();

            DestinationModel destination = destinationRepository.findById(tripDTO.destinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found."));

            BeanUtils.copyProperties(tripDTO, tripModel);
            tripModel.setDestination(destination);

            return ResponseEntity.status(HttpStatus.OK).body(tripRepository.save(tripModel));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTripById(@PathVariable(value = "id") Long id) {
        Optional<TripModel> tripO = tripRepository.findById(id);

        if (tripO.isPresent()) {
            tripRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Trip deleted successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found.");
    }
}
