package engine.descriptors;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false, unique = true)
    @Pattern( regexp = "\\w+@\\w+\\.\\w+",
            message = "Please specify correct email address")
    private String email;
    @Column
    @NotBlank
    @Length(min = 5)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private UserRoles role = UserRoles.USER;

//    @OneToMany(mappedBy = "user")
//    private final List<Quiz> quizzes = new ArrayList<>();

    public User() {}

  /*  public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }
    public void removeQuiz(Quiz quiz) {
        quizzes.remove(quiz);
    }*/
}

