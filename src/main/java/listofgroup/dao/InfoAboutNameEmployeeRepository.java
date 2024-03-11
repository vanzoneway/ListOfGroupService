package listofgroup.dao;

import listofgroup.model.InfoAboutNameEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoAboutNameEmployeeRepository extends JpaRepository<InfoAboutNameEmployee, Integer> {

        boolean existsById (int id);
        InfoAboutNameEmployee findById(int id);

}
