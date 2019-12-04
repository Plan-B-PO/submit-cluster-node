package pl.ee.verificator.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VerificationResultRepository extends CrudRepository<VerificationResultEntity, String> {
}
