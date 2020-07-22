package engine.jpa.repositories;

import engine.descriptors.QuizCompletionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletionEntityRepository extends PagingAndSortingRepository<QuizCompletionEntity, Integer> {

    /*@Query(
            value = "SELECT * from QUIZ_COMPLETION_ENTITY WHERE USER_ID = ?1 ORDER BY COMPLETED_AT DESC",
            countQuery = "SELECT count(*) from QUIZ_COMPLETION_ENTITY WHERE USER_ID = ?1",
            nativeQuery = true
    )*/
    Page<QuizCompletionEntity> findByUserId(int userId, Pageable pageable);
}
