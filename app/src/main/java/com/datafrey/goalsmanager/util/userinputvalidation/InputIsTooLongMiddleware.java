package com.datafrey.goalsmanager.util.userinputvalidation;

public class InputIsTooLongMiddleware extends InputValidatorMiddleware {

    private int maximumLength;

    public InputIsTooLongMiddleware(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    @Override
    public InputValidationResult check(String input) {
        if (input.length() > maximumLength) {
            return InputValidationResult.INPUT_IS_TOO_LONG;
        }

        return checkNext(input);
    }
}
