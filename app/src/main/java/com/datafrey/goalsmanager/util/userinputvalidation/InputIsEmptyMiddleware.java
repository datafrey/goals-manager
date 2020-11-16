package com.datafrey.goalsmanager.util.userinputvalidation;

public class InputIsEmptyMiddleware extends InputValidatorMiddleware {

    @Override
    public InputValidationResult check(String input) {
        if (input.isEmpty()) {
            return InputValidationResult.INPUT_IS_EMPTY;
        }

        return checkNext(input);
    }
}
