package engine.services;

import engine.descriptors.Quiz;
import engine.jpa.repositories.QuizCompletionEntityRepository;
import engine.jpa.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizService {
    private final QuizRepository quizDB;

    @Autowired
    public QuizService(QuizRepository quizzes) {
        this.quizDB = quizzes;
    }

    public Quiz getQuiz(int id) {
        return quizDB.findById(id).orElseThrow();
    }

    public boolean contains(int id) {
        return quizDB.existsById(id);
    }

    public void add(Quiz quiz) {
        quizDB.save(quiz);
    }

    public Iterable<Quiz> getAllQuizzes() {
        return quizDB.findAll();
    }
    public Page<Quiz> getAllQuizzes(int pageNumber, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return quizDB.findAll(paging);
    }
    public void deleteQuizById(int id) {
        quizDB.deleteById(id);
    }

}