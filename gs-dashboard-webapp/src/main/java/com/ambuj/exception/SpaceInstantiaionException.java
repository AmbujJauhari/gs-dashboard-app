package com.ambuj.exception;

/**
 * Created by Aj on 17-06-2016.
 */
public class SpaceInstantiaionException extends RuntimeException {

    public SpaceInstantiaionException() {
        super();
    }

    public SpaceInstantiaionException(Exception ex) {
        super(ex);
    }

    public SpaceInstantiaionException(String spaceName) {
        super("Space Instantaion Exception " + spaceName);
    }
}

