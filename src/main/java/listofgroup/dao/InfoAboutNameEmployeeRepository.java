package listofgroup.dao;

import listofgroup.entity.InfoAboutNameEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoAboutNameEmployeeRepository extends JpaRepository<InfoAboutNameEmployeeEntity, Integer> {

        boolean existsById (int id);
        InfoAboutNameEmployeeEntity findById(int id);

}
