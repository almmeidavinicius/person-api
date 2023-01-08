package br.com.vinicius.personapi.service;

import br.com.vinicius.personapi.dto.request.PersonDTO;
import br.com.vinicius.personapi.dto.response.MessageResponseDTO;
import br.com.vinicius.personapi.entity.Person;
import br.com.vinicius.personapi.exception.PersonNotFoundException;
import br.com.vinicius.personapi.mapper.PersonMapper;
import br.com.vinicius.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<PersonDTO> listAll() {

        List<Person> all = personRepository.findAll();
        return all.stream()
                .map(personMapper::toDTO)
                .toList();
    }

    public PersonDTO getById(Long id) throws PersonNotFoundException {

        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);

    }

    public void deleteById(Long id) throws PersonNotFoundException {

        verifyIfExists(id);
        personRepository.deleteById(id);
    }
    private Person verifyIfExists(Long id) throws PersonNotFoundException {

        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
