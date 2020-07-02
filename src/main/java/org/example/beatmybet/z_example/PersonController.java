package org.example.beatmybet.z_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController // automatically annotates with "@ResponseBody"
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;


    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getAllDetails(@PathVariable("id") Long id) {
        return personRepository.findById(id).map(mapToPersonDTO).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/siblings")
    public ResponseEntity<Set<PersonDTO>> getAllSiblings(@PathVariable("id") Long id) {
        return personRepository.findById(id).map(findSiblings).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private Function<Person, Set<PersonDTO>> findSiblings = person -> person.getParent().getChildren().stream()
            .map(p -> PersonDTO.builder().id(p.getId()).fullName(p.getFullName()).build()).collect(Collectors.toSet());

    private Function<Person, PersonDTO> mapToPersonDTO = p -> PersonDTO.builder()
                                                        .id(p.getId())
                                                        .fullName(p.getFullName())
                                                        .parent(p.getParent())
                                                        .children(p.getChildren())
                                                        .build();
}
