package info.wonlee.assessment.genesys.game.evaluator;
/***************************************************************
 * Copyright (c) 2020 Errigal Inc.
 *
 * This software is the confidential and proprietary information
 * of Errigal, Inc.  You shall not disclose such confidential
 * information and shall use it only in accordance with the
 * license agreement you entered into with Errigal.
 *
 ***************************************************************/

/**
 * User: wonlee
 * Date: 23/12/2020
 */

public class IllegalMoveException extends Exception {
    public IllegalMoveException(String message) {
        super(message);
    }
}
