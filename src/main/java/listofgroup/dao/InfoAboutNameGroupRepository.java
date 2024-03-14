package listofgroup.dao;

import listofgroup.model.InfoAboutNameGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoAboutNameGroupRepository extends JpaRepository<InfoAboutNameGroup, Integer> {

        @EntityGraph(attributePaths = {
                "generalInfoGroup.scheduleListMap",
                "generalInfoGroup.scheduleListMap.schedules"
        })
        InfoAboutNameGroup findByName(String name);

}
