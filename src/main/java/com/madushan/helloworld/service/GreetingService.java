package com.madushan.helloworld.service;

import com.madushan.helloworld.exceptions.InvalidNameException;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    public String greet(String name) {
        if (name == null) {
            throw new InvalidNameException();
        }

        String normalizedName = name.strip();
        if (normalizedName.isEmpty()) {
            throw new InvalidNameException();
        }

        char firstLetter = normalizedName.charAt(0);
        boolean startsWithAllowedLetter = (firstLetter >= 'A' && firstLetter <= 'M')
                || (firstLetter >= 'a' && firstLetter <= 'm');

        if (!startsWithAllowedLetter) {
            throw new InvalidNameException();
        }

        // Only the first letter changes: retain the spelling of the remaining name.
        String displayName = Character.toUpperCase(firstLetter) + normalizedName.substring(1);
        return "Hello " + displayName;
    }
}
