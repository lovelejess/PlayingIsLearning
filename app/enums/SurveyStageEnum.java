package enums;

public enum SurveyStageEnum {

    STAGE_ONE ("one"),
    STAGE_TWO ("two"),
    STAGE_THREE ("three"),
    STAGE_FOUR ("four"),
    STAGE_FIVE ("five"),
    DONE("done");

    public String label;

    SurveyStageEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
