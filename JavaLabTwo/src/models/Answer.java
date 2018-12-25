package models;

public class Answer {
    private int id;
    private int question_numb;
    private String answer_variant;
    private String correct_y_n;

    public Answer(int id, int question_numb, String answer_variant, String correct_y_n) {
        this.id = id;
        this.question_numb = question_numb;
        this.answer_variant = answer_variant;
        this.correct_y_n = correct_y_n;
    }

    public int getId() {
        return id;
    }

    public int getQuestionNumb() {
        return question_numb;
    }
    
    public String getAnswerVariant() {
        return answer_variant;
    }

    public String checkCorrect() {
        return correct_y_n;
    }
}
