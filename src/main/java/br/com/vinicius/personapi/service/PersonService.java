package br.com.vinicius.personapi.service;

import br.com.vinicius.personapi.dto.request.PersonDTO;
import br.com.vinicius.personapi.dto.response.MessageResponseDTO;
import br.com.vinicius.personapi.entity.Person;
import br.com.vinicius.personapi.mapper.PersonMapper;
import br.com.vinicius.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person createdPerson = personRepository.save(personToSave);
        return MessageResponseDTO.builder().response("Created personDTO with id = " + createdPerson.getId()).build();
    }
}
