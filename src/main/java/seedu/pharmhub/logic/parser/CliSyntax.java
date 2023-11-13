package seedu.pharmhub.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ALLERGY = new Prefix("no/");
    public static final Prefix PREFIX_ORDER_NUMBER = new Prefix("o/");
    public static final Prefix PREFIX_MEDICINE_NAME = new Prefix("m/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_IGNORE_ALLERGY = new Prefix("ia/");

    public static final Prefix PREFIX_DELETE_SHORT_FORM = new Prefix("d/");

}
