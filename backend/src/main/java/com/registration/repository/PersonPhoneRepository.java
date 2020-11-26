package com.registration.repository;

import com.registration.model.PersonPhone;
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
public class PersonPhoneRepository extends Repositorio<PersonPhone> {

    @Autowired
    public PersonPhoneRepository(EntityManager em) {
        super(em, PersonPhone.class);
    }

    private static String normalize(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static Specification<PersonPhone> byPersonId(Long idPerson) {
        return (Root<PersonPhone> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
                .equal(root.get("person").get("id"), idPerson);
    }

    public static Specification<PersonPhone> byPhoneId(Long idPhone) {
        return (Root<PersonPhone> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
                .equal(root.get("phone").get("id"), idPhone);
    }
}
