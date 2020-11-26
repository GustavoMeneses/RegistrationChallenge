package com.registration.repository;

import com.registration.model.PersonEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.Normalizer;
import java.util.regex.Pattern;

@Repository
public class PersonEmailRepository extends Repositorio<PersonEmail> {

    @Autowired
    public PersonEmailRepository(EntityManager em) {
        super(em, PersonEmail.class);
    }

    private static String normalize(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static Specification<PersonEmail> byPersonId(Long idPerson) {
        return (Root<PersonEmail> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
                .equal(root.get("person").get("id"), idPerson);
    }

    public static Specification<PersonEmail> byEmailId(Long idEmail) {
        return (Root<PersonEmail> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
                .equal(root.get("email").get("id"), idEmail);
    }
}
