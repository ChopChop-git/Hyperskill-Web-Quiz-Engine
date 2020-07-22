package engine.descriptors;

public class QuizResponse {
    private final boolean success;
    private final String feedback;


    public QuizResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
