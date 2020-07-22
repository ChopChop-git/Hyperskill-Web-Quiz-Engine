package engine.descriptors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "quizzes")
public class Quiz {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Title is mandatory")
    @Column
    private String title;

    @NotBlank(message = "Quiz text is mandatory")
    @Column
    private String text;
    @NotNull
    @Size(min = 2, message = "Specify at least 2 options")
    @ElementCollection
    private List<String> options;
    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> answer;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;


    public Quiz() {
    }

    public boolean isAnswer(int ans) {
        return this.answer.contains(ans);
    }

}
