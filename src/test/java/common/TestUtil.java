package common;

import com.github.javafaker.Faker;

public class TestUtil {
	  private static Faker faker = new Faker();

	    public static String randomUsername() {
	        return faker.name().username() + System.currentTimeMillis();
	    }

	    public static String randomEmail() {
	        return faker.internet().emailAddress();
	    }
	    public static String randomPassword() {
	        // Generates a password with letters, numbers, and special characters
	        return faker.internet().password(8, 16, true, true, true);
	        // Parameters: minLength, maxLength, includeUppercase, includeSpecial, includeDigit
	    }
}

