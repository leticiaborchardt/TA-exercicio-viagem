package com.example.viagemdestino.repositories;

import com.example.viagemdestino.models.DestinationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationModel, Long> {
    List<DestinationModel> findAllByCountryIgnoreCase(String country);
}
