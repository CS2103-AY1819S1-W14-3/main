package seedu.address.testutil;

//@@author yuntongzhang

import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietType;

/**
 * A utility class for building Diet object for testing.
 */
public class DietBuilder {
    private static final String DEFAULT_DETAIL = "Egg";
    private static final DietType DEFAULT_TYPE = DietType.ALLERGY;

    private String detail;
    private DietType type;

    public DietBuilder() {
        this.detail = DEFAULT_DETAIL;
        this.type = DEFAULT_TYPE;
    }

    /**
     * Setter for detail of the DietBuilder object.
     * @param newDetail The new detail of the dietary requirement.
     * @return the updated DietBuilder.
     */
    public DietBuilder withDetail(String newDetail) {
        this.detail = newDetail;
        return this;
    }

    /**
     * Setter for type of the DietBuilder object.
     * @param newType The new type of the dietary requirement.
     * @return the updated DietBuilder.
     */
    public DietBuilder withType(DietType newType) {
        this.type = newType;
        return this;
    }

    /**
     * Build a {@code Diet} object with the given detail and type.
     * @return The diet object built.
     */
    public Diet build() {
        return new Diet(this.detail, this.type);
    }
}
