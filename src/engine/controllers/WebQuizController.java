package engine.controllers;

import engine.descriptors.Quiz;
import engine.descriptors.QuizCompletionEntity;
import engine.descriptors.QuizResponse;
import engine.services.QuizCompletionService;
import engine.services.QuizService;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
public class WebQuizController {
    private final QuizService quizService;
    private final UserService userService;
    private final QuizCompletionService quizCompletionService;
    private static final QuizResponse SUCCESS =
            new QuizResponse(true, "Congratulations, you're right!");
    private static final QuizResponse FAILURE =
            new QuizResponse(false, "Wrong answer! Please, try again.");

    @Autowired
    public WebQuizController(QuizService quizService, UserService userService, QuizCompletionService quizCompletionService) {
        this.quizService = quizService;
        this.userService = userService;
        this.quizCompletionService = quizCompletionService;
    }


    @PostMapping(path = "/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, Authentication auth) {
        quiz.setUser(userService.getByEmail(auth.getName()));
        quizService.add(quiz);
        return quiz;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getById(@PathVariable int id) {
        validateQuizExistence(id);
        return quizService.getQuiz(id);
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return quizService.getAllQuizzes(page, pageSize, sortBy);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public QuizResponse solveQuiz(@PathVariable int id, @RequestBody Map<String, Set<Integer>> body, Authentication auth) {
        validateQuizExistence(id);
        Set<Integer> guess = body.get("answer");
        Set<Integer> answers = quizService.getQuiz(id).getAnswer();
        QuizResponse result = FAILURE;
        if (answers == null) {
            answers = new HashSet<>();
        }
        if (answers.equals(guess)) {
            result = SUCCESS;
            QuizCompletionEntity quizCompletionEntity = new QuizCompletionEntity();
            quizCompletionEntity.setQuizId(id);
            quizCompletionEntity.setCompletedAt(new Date());
            quizCompletionEntity.setUserId(getUserId(auth));
            quizCompletionService.save(quizCompletionEntity);
        }
        return result;
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(Authentication auth, @PathVariable int id) {
        validateQuizExistence(id);
        String quizOwnerName = quizService.getQuiz(id).getUser().getEmail();  //TODO Can compare by id but ugh
        String currentUser = auth.getName();
        if (!currentUser.equals(quizOwnerName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this quiz");
        }
        quizService.deleteQuizById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<QuizCompletionEntity> getAllCompletions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Authentication auth
    ) {
        return quizCompletionService.getUserCompletionPage(getUserId(auth), page, pageSize);
    }

    private void validateQuizExistence(int id) {
        if (!quizService.contains(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "quiz not found");
        }
    }
    private int getUserId(Authentication auth) {
        return userService.getByEmail(auth.getName()).getId();
    }
}

