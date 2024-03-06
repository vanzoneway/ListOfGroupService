package listofgroup.dao;

import listofgroup.entity.GeneralInfoGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralInfoGroupRepository extends JpaRepository<GeneralInfoGroupEntity, Integer> {

}
