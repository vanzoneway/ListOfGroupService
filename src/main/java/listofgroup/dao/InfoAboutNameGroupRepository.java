package listofgroup.dao;

import listofgroup.entity.InfoAboutNameGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoAboutNameGroupRepository extends JpaRepository<InfoAboutNameGroupEntity, Integer> {
        InfoAboutNameGroupEntity findByName(String name);

}
