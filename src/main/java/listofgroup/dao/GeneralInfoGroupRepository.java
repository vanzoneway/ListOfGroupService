package listofgroup.dao;

import listofgroup.model.GeneralInfoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralInfoGroupRepository extends JpaRepository<GeneralInfoGroup, Integer> {

}
