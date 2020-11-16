package com.datafrey.goalsmanager.util.userinputvalidation;

public class InputIsNotEqualToStringMiddleware extends InputValidatorMiddleware {

    private String mustBeEqualTo;

    public InputIsNotEqualToStringMiddleware(String mustBeEqualTo) {
        this.mustBeEqualTo = mustBeEqualTo;
    }

    @Override
    public InputValidationResult check(String input) {
        if (input.equals(mustBeEqualTo)) {
            return InputValidationResult.INPUT_IS_NOT_EQUAL_TO_STRING;
        }

        return checkNext(input);
    }
}
