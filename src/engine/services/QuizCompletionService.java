package engine.services;

import engine.descriptors.QuizCompletionEntity;
import engine.jpa.repositories.QuizCompletionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class QuizCompletionService {

    private final QuizCompletionEntityRepository quizCompletionEntityRep;

    @Autowired
    public QuizCompletionService(QuizCompletionEntityRepository quizCompletionEntityRep) {
        this.quizCompletionEntityRep = quizCompletionEntityRep;
    }

    public Page<QuizCompletionEntity> getUserCompletionPage(int userId, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("completedAt").descending());
        return quizCompletionEntityRep.findByUserId(userId, paging);
    }

    public void save(QuizCompletionEntity quizCompletionEntity) {
        quizCompletionEntityRep.save(quizCompletionEntity);
    }
}
