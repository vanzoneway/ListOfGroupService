package listofgroup.dao;

import listofgroup.model.InfoAboutNameEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InfoAboutNameEmployeeRepository extends JpaRepository<InfoAboutNameEmployee, Integer> {

        boolean existsById (int id);

        InfoAboutNameEmployee findById(int id);

        @Query("SELECT i FROM InfoAboutNameEmployee i WHERE i.firstName LIKE :prefix%")
        List<InfoAboutNameEmployee> findByBeginOfName(@Param("prefix")String prefix);


}
