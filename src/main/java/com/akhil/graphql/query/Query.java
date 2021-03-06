package com.akhil.graphql.query;

import com.akhil.graphql.entity.Dog;
import com.akhil.graphql.exception.DogNotFoundException;
import com.akhil.graphql.repository.DogRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.Optional;

public class Query implements GraphQLQueryResolver {
    private DogRepository dogRepository;

    public Query(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Iterable<Dog> findAllDogs(){
        return dogRepository.findAll();
    }

    public Dog findDogById(long id){
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()){
            return optionalDog.get();
        }
        else{
            throw new DogNotFoundException("Dog Not Found", id);
        }
    }
}
