package com.example.viagemdestino.repositories;

import com.example.viagemdestino.models.TripModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripModel, Long> {
    List<TripModel> findAllByDestinationId(Long id);

    List<TripModel> findAllByDestinationCountryIgnoreCase(String country);
}
