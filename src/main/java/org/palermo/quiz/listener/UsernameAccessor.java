package org.palermo.quiz.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameAccessor {

    private String username;

    private static final Pattern ROOM_PATTERN = Pattern.compile("^room:\\s(\\d+)$");
    private static final Pattern USER_PATTERN = Pattern.compile("^room:\\s(\\d+)\\suser:\\s(.+)$");

    private static final String ROOM_MASK = "room: %s";
    private static final String USER_MASK = "room: %s user: %s";

    private UsernameAccessor(String username) {
        this.username = username;
    }

    public boolean isRoom() {
        return ROOM_PATTERN.matcher(username).matches();
    }

    public boolean isUser() {
        return USER_PATTERN.matcher(username).matches();
    }

    public String getRoomIdentifier() {
        if (isRoom()) {
            return getGroup(ROOM_PATTERN, this.username, 1);
        }
        else if (isUser()) {
            return getGroup(USER_PATTERN, this.username, 1);
        }
        else {
            throw new RuntimeException("Unknown username type " + username);
        }
    }

    public String getAccountEmail() {
        if (isUser()) {
            return getGroup(USER_PATTERN, this.username, 2);
        }
        else {
            throw new RuntimeException("Unable to get the account email from " + username);
        }
    }

    public static UsernameAccessor wrap(String username) {
        return new UsernameAccessor(username);
    }

    public static String generate(String roomIdentifier) {
        return String.format(ROOM_MASK, roomIdentifier);
    }

    public static String generate(String roomIdentifier, String accountEmail) {
        return String.format(USER_MASK, roomIdentifier, accountEmail);
    }

    private String getGroup(Pattern pattern, String input, int index) {
        Matcher matcher = USER_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new RuntimeException("Could not match pattern for " + input);
        }
        return matcher.group(index);
    }
}
