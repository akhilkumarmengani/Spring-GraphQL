package com.akhil.graphql.mutator;

import com.akhil.graphql.entity.Dog;
import com.akhil.graphql.exception.BreedNotFoundException;
import com.akhil.graphql.exception.DogNotFoundException;
import com.akhil.graphql.repository.DogRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import java.util.Optional;

public class Mutator implements GraphQLMutationResolver {
    private DogRepository dogRepository;

    public Mutator(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public boolean deleteDogBreed(String breed){
        boolean isFound = false;
        Long deletedId = null;
        Iterable<Dog> allDogs = dogRepository.findAll();

        for(Dog dog : allDogs){
            if(breed.equals(dog.getBreed())){
                dogRepository.deleteById(dog.getId());
                isFound = true;
            }
        }
        if(!isFound){
            throw new BreedNotFoundException("Breed Not Found",breed);
        }
        return isFound;
    }

    public Dog updateDogName(String newName, Long id){
        boolean isUpdated = false;
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()){
            Dog dog = optionalDog.get();
            dog.setName(newName);
            dogRepository.save(dog);
            return dog;
        }
        else{
            throw new DogNotFoundException("Dog Not Found", id);
        }
    }
}
