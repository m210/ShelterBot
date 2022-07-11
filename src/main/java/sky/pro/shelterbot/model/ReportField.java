package sky.pro.shelterbot.model;

public enum ReportField {

    RATION("РАЦИОН ЖИВОТНОГО"),
    HEALTH("САМОЧУВСТВИЕ"),
    BEHAVIOR("ПОВЕДЕНИЕ");

    private final String field;
    ReportField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public static ReportField getValue(String key) {
        for(ReportField field : ReportField.values()) {
            if(field.getField().equals(key)) {
                return field;
            }
        }
        return null;
    }

}
