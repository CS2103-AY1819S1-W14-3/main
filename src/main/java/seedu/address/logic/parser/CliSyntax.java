package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NRIC = new Prefix("ic/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /** Ordered prefixes for the addmeds command. */
    public static final Prefix PREFIX_DRUGNAME = new Prefix("d/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_DOSE_UNIT = new Prefix("u/");
    public static final Prefix PREFIX_DOSES_PER_DAY = new Prefix("n/");
    public static final Prefix PREFIX_DURATION = new Prefix("t/");
}
