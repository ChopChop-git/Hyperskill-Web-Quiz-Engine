package engine.descriptors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class QuizCompletionEntity {
    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    @Column
    @JsonProperty(value = "id")
    private int quizId;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date completedAt;

    @Column
    @JsonIgnore
    private int userId;

    public QuizCompletionEntity() {}


}
