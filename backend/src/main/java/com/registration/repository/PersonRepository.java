package com.registration.repository;

import com.registration.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.Normalizer;
import java.util.regex.Pattern;

@Repository
public class PersonRepository extends Repositorio<Person> {

    @Autowired
    public PersonRepository(EntityManager em) {
        super(em, Person.class);
    }

    private static String normalize(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

//    public static Specification<Person> porIdInterferencia(List<Long> idInterferencia) {
//        return (Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> root
//                .get("interferencia").get("idInterferencia").in(idInterferencia);
//    }
}
