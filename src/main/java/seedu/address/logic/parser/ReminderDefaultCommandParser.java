package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;

import java.util.stream.Stream;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.defaults.ReminderDefaultCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReminderDefaultCommand object
 */
public class ReminderDefaultCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ReminderDefaultCommand
     * and returns a ReminderDefaultCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderDefaultCommand parse(String args) throws ParseException {
        int r;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        } else {
            r = Integer.parseInt(argMultimap.getValue(PREFIX_REMINDER).get().trim());
        }
        return new ReminderDefaultCommand(String.valueOf(r));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}