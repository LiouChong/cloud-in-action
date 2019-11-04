package repository;

import model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String> {
    @Override
    Optional<Organization> findById(String organizationId);
}
