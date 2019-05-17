package com.starwars.api.services.impl;

import java.util.List;

import com.starwars.api.models.Planet;
import com.starwars.api.models.integrations.swapi.SwapiApi;
import com.starwars.api.models.integrations.swapi.models.SwapiPlanet;
import com.starwars.api.repository.PlanetRepository;
import com.starwars.api.services.PlanetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    @Override
    public List<Planet> getAll() {
        List<Planet> planets = this.planetRepository.findAll();
        SwapiApi swapi = new SwapiApi();

        planets.forEach(planet -> {
            SwapiPlanet apiSwapi = swapi.GetPlanet(planet.getNome());

            if (apiSwapi != null) {
                planet.setExibicoes(apiSwapi.filmsUrls.size());
            }
        });

        return planets;
    }

    @Override
    public List<Planet> findByName(String name) {
        List<Planet> planets = this.planetRepository.findByNome(name);
        SwapiApi swapi = new SwapiApi();

        planets.forEach(planet -> {
            SwapiPlanet apiSwapi = swapi.GetPlanet(planet.getNome());

            if (apiSwapi != null) {
                planet.setExibicoes(apiSwapi.filmsUrls.size());
            }
        });

        return planets;
    }

    @Override
    public Planet findById(String id) {
        Planet planet = this.planetRepository.findById(id).orElse(null);

        if (planet != null) {
            SwapiApi swapi = new SwapiApi();
            SwapiPlanet apiSwapi = swapi.GetPlanet(planet.getNome());

            if (apiSwapi != null) {
                planet.setExibicoes(apiSwapi.filmsUrls.size());
            }
        }

        return planet;
    }

    @Override
    public Planet add(Planet planet) {
        this.planetRepository.save(planet);

        SwapiApi swapi = new SwapiApi();
        SwapiPlanet apiSwapi = swapi.GetPlanet(planet.getNome());

        if (apiSwapi != null) {
            planet.setExibicoes(apiSwapi.filmsUrls.size());
        }

        return planet;
    }

    @Override
    public Planet update(Planet planet) {
        this.planetRepository.save(planet);

        SwapiApi swapi = new SwapiApi();
        SwapiPlanet apiSwapi = swapi.GetPlanet(planet.getNome());

        if (apiSwapi != null) {
            planet.setExibicoes(apiSwapi.filmsUrls.size());
        }

        return planet;
    }

    @Override
    public void remove(String id) {
        this.planetRepository.deleteById(id);
    }

}