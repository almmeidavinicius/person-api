package br.com.vinicius.personapi.service;

import br.com.vinicius.personapi.dto.request.PersonDTO;
import br.com.vinicius.personapi.dto.response.MessageResponseDTO;
import br.com.vinicius.personapi.entity.Person;
import br.com.vinicius.personapi.exception.PersonNotFoundException;
import br.com.vinicius.personapi.mapper.PersonMapper;
import br.com.vinicius.personapi.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Person personToSave = personMapper.toModel(personDTO);
        Person createdPerson = personRepository.save(personToSave);
        return createMessageResponseDTO("Created personDTO with id = ", createdPerson.getId());
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

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {

        verifyIfExists(id);
        Person updatedPerson = personRepository.save(personMapper.toModel(personDTO));
        return createMessageResponseDTO("Updated person with id = ", id);
    }

    private MessageResponseDTO createMessageResponseDTO(String message, Long id) {
        return MessageResponseDTO.builder()
                .response(message + id)
                .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {

        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}