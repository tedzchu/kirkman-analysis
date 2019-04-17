/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* File name  :  SchoolgirlSolver.java
* Purpose    :  Solves the Kirkman's Schoolgirl Problem given on Dr. Dorin's 282 page.
* Date       :  2018-24-04
* Description:  Fifteen schoolgirls (named A, B, C, ..., M, N, O) take their daily
                walks in five rows, with three girls per row. How can it be arranged
                so that each schoolgirl walks in the same row with each other
                schoolgirl exactly once each week?
*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*/

import java.util.Arrays;

/**
*   Generates a single solution to Kirkman's Schoolgirl Problem using recursion
*   and backtracking. A one-dimensional solution array and two-dimensional
*   adjacency table array were used. Dr. Toal's notes available on his website
*   for this problem were used to help generate a skeleton for this project.
*   <p>
*   The program namely thrives off of the one-dimensional array being
*   cleanly sorted by column by modulo 3 (first column will always be
*   index % 3 == 0, second is index % 3 == 1, third is index % 3 == 2).
*   Each schoolgirl is treated as a number through the majority of the
*   program as well, with 0 being A and going up to 14 being O in order
*   to check for values easier within the solution array.
*   <p>
*   Based on 20 samples, the program runs on average of 5.7 seconds
*   using a 2013 Macbook Pro, 2.4GHz i7 processor.
*   <p>
*   NOTE: THIS PROGRAM USES MAGIC NUMBERS FOR SAKE OF SOME MAGIC NUMBERS AND HAS NOT
*   BEEN MODIFIED SINCE THE TIME OF SUBMISSION IN DR. DORIN'S CLASS.
*
*   @author     Theodore Chu
*   @version    1.0
*/

public class SchoolgirlSolver_042018 {

    /**
     * The one-dimensional solution array, holding 105 schoolgirls (15 schoolgirls by 7 days).
     *
     * @since           1.0
     */
    public static int[] week = new int[105];
    /**
     * The two-dimensional adjacency table, holding 15 by 15 schoolgirls.
     *
     * @since           1.0
     */
    public static int[] [] adjacencyTable = new int[15][15];
    /**
     * The last index of week[], triggering finish() within placeFrom() if reached.
     *
     * @see             #placeFrom(int)
     * @since           1.0
     */
    public static final int LAST_SLOT = 104;

    /**
     * Determines if the given schoolgirl can walk in the given slot
     * by performing the following checks: 1) checks to see if the schoolgirls
     * has walked in the given day, 2) checks to see that the first slot of each
     * day is always A 3) checks the adjacency table for elements within each row
     * (if they exist) 4) checks the first column excluding the first element of
     * each day for ascending order.
     *
     * @param slot       position in week[] to check
     * @param schoolgirl integer of schoolgirl (0-14 = A-O)
     * @return          <code>true</code> if the schoolgirl passes all of the
     *                  aformentioned checks;
     *                  <code>false</code> otherwise.
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public boolean canWalk(int slot, int schoolgirl) {
        int day = slot/15;

        for (int i = 15 * day; i < 15 * (day + 1); i++){
            if(week[i] == schoolgirl){
                return false;
            }
        }
        if ( slot % 15 == 0 ){
            if( schoolgirl != 0 ){
                return false;
            }
        } else if (slot % 3 == 0 ){
            if ( week[slot + 1] > -1 && adjacencyTable[schoolgirl][week[slot+1]] == 1 ){
                return false;
            } else if ( week[slot + 2] > -1 && adjacencyTable[schoolgirl][week[slot+2]] == 1 ){
                return false;
            }
        } else if (slot % 3 == 1){
            if( adjacencyTable[schoolgirl][week[slot-1]] == 1 ){
                return false;
            } else if( week[slot-1] > schoolgirl ){
                return false;
            } else if( week[slot + 1] > -1 && adjacencyTable[schoolgirl][week[slot+1]] == 1 ){
                return false;
            }
        } else if (slot % 3 == 2){
            if ( adjacencyTable[schoolgirl][week[slot-1]] == 1 || adjacencyTable[schoolgirl][week[slot-2]] == 1 ){
                return false;
            } else if( week[slot-1] > schoolgirl || week[slot-2] > schoolgirl ){
                return false;
            }
        } else if ( slot % 3 == 0 && slot % 15 != 0 ){
            if ( week[slot - 3] > schoolgirl ){
                return false;
            }
        }
        return true;
    }

    /**
     * Places the given schoolgirl into the given slot of the solution array,
     * then adds markers to the adjacency table if values exist within the same row.
     *
     * @param slot       position in week[] to place from
     * @param schoolgirl integer of schoolgirl
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public void placeSchoolgirls(int slot, int schoolgirl) {
        week[slot] = schoolgirl;
        if ( slot % 3 == 1 ){
            adjacencyTable[schoolgirl][week[slot - 1]] = 1;
            adjacencyTable[week[slot - 1]][schoolgirl] = 1;
            if ( week[slot + 1] > -1 ){
                adjacencyTable[schoolgirl][week[slot + 1]] = 1;
                adjacencyTable[week[slot + 1]][schoolgirl] = 1;
            }
        } else if ( slot % 3 == 2 ){
            adjacencyTable[schoolgirl][week[slot - 1]] = 1;
            adjacencyTable[week[slot - 1]][schoolgirl] = 1;
            adjacencyTable[week[slot - 2]][schoolgirl] = 1;
            adjacencyTable[schoolgirl][week[slot - 2]] = 1;
        } else if ( slot % 3 == 0 ){
            if ( week[slot + 1] > -1 ){
                adjacencyTable[schoolgirl][week[slot + 1]] = 1;
                adjacencyTable[week[slot + 1]][schoolgirl] = 1;
            }
            if ( week[slot + 2] > -1 ){
                adjacencyTable[schoolgirl][week[slot + 2]] = 1;
                adjacencyTable[week[slot + 2]][schoolgirl] = 1;
            }
        }
    }

    /**
     * Removes markers from the adjacency table if values exist within the same row,
     * then removes the given schoolgirl from the given slot of the solution array.
     *
     * @param slot       position in week[] to remove from
     * @param schoolgirl integer of schoolgirl
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public void removeSchoolgirls(int slot, int schoolgirl){
        if ( slot % 3 == 0 ){
            if ( week[slot + 1] > -1 ) {
                adjacencyTable[schoolgirl][week[slot + 1]] = 0;
                adjacencyTable[week[slot + 1]][schoolgirl] = 0;
            }
            if ( week[slot + 2] > -1 ) {
                adjacencyTable[schoolgirl][week[slot + 2]] = 0;
                adjacencyTable[week[slot + 2]][schoolgirl] = 0;
            }
        }
        else if ( slot % 3 == 1 ){
            adjacencyTable[schoolgirl][week[slot - 1]] = 0;
            adjacencyTable[week[slot - 1]][schoolgirl] = 0;
            if (week[slot + 1] > -1) {
                adjacencyTable[schoolgirl][week[slot + 1]] = 0;
                adjacencyTable[week[slot + 1]][schoolgirl] = 0;
            }
        } else if ( slot % 3 == 2 ){
            adjacencyTable[schoolgirl][week[slot - 1]] = 0;
            adjacencyTable[week[slot - 1]][schoolgirl] = 0;
            adjacencyTable[week[slot - 2]][schoolgirl] = 0;
            adjacencyTable[schoolgirl][week[slot - 2]] = 0;
        }
        week[slot] = -1;
    }

    /**
     * The core method of the program. Begins by determining the first and last
     * schoolgirls that should be tested (from A, ending at O). Loops through the
     * range of schoolgirls, checking if placement inside the solution array is valid
     * each time. If a value already exists within the solution array (i.e. backtracking),
     * remove it and its adjacency marks. Next, the schoolgirl is placed within the
     * solution array at the given slot. A check is performed to see if the method has
     * reached the last slot (index 104 in size 105). If so, call finish(). Otherwise,
     * call itself again with a slot incremented by 1.
     * <p>
     * Since backtracking occurs for a single schoolgirl but can jump back multiple
     * indicies at once, a second for loop is used to check for values that occur after
     * the given slot. This does add some time by sometimes eliminating rows of data at once,
     * but seems the most efficient in ensuring no false adjacencies remain.
     *
     * @param slot       position in week[] to place into, called recursively
     * @see             #determineFirst(int)
     * @see             #determineLast(int)
     * @see             #canWalk(int, int)
     * @see             #removeSchoolgirls(int, int)
     * @see             #placeSchoolgirls(int, int)
     * @see             #finish()
     * @since           1.0
     */
    public void placeFrom(int slot) {
        int start = determineFirst(slot);
        int end = determineLast(slot);
        for ( int schoolgirl = start; schoolgirl < end + 1; schoolgirl++ ){
            if(canWalk(slot, schoolgirl)){
                if( week[slot] > -1 ){
                    removeSchoolgirls(slot, week[slot]);
                }
                placeSchoolgirls(slot, schoolgirl);
                if ( slot == LAST_SLOT ) {
                    finish();
                } else {
                    placeFrom(slot + 1);
                }
            }
        }
        for(int i = slot + 1; i < 105; i++){
            if( week[i] > -1 ){
                removeSchoolgirls(i, week[i]);
            }
        }
    }

    /**
     * Determines the first schoolgirl that can be tested given a slot position
     * in the solution array. Since schoolgirls A, B, and C cannot exist outside of
     * the first column of each day, schoolgirl D is returned. In any other case,
     * schoolgirl A is returned.
     *
     * @param slot       position in week[] to check
     * @return          <code>integer</code> of the first schoolgirl to be checked
                        in placeFrom().
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public int determineFirst(int slot) {
        if ( slot % 3 != 0 && slot > 14 ){
            return 3;
        }
        return 0;
    }

    /**
     * Determines the last schoolgirl that can be tested given a slot position
     * in the solution array. A should always be the first slot of each day. Similarly,
     * B and C should be the second slot first column and third slot first column respectively.
     * These parameters prevent backtracking across those slots.
     * <p>
     * If it's searching past the first day, M, N, and O must exist in the third column,
     * so another case is thrown to prevent extra searches within the first two colummns.
     *
     * @param slot       position in week[] to check
     * @return          <code>integer</code> of the last schoolgirl to be checked
                        in placeFrom().
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public int determineLast(int slot) {
        if ( slot == 0 || slot % 15 == 0 ){
            return 0;
        } else if ( slot % 15 == 3 && slot > 6 ){
            return 1;
        } else if ( slot % 15 == 6 && slot > 6 ){
            return 2;
        } else if ( slot > 15 && slot % 3 < 2 ) {
            return 11;
        } else {
            return 14;
        }
    }

    /**
     * Converts numbers in range 0-14 to letters A-O respectively.
     *
     * @param i         value of schoolgirl in integer form
     * @return          value of schoolgirl in letter form
     * @see             #finish()
     * @since           1.0
     */

    public static String numToLetter(int i) {
        return i > -1 && i < 15 ? String.valueOf((char)(i + 'A')) : null;
    }

    /**
     * Prints out a complete solution array after being called from placeFrom().
     *
     * @see             #numToLetter(int)
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public void finish() {
        for ( int i = 0; i < LAST_SLOT; i += 3 ){
            if ( i % 15 == 0 && i > 0 ){
                System.out.println("-----");
            }
            System.out.println( numToLetter(week[i]) + " " + numToLetter(week[i + 1]) + " " + numToLetter(week[i + 2]) );
        }
        System.exit(0);
    }

    /**
     * Populates the solution array with value -1 in each slot, creates a new
     * SchoolgirlSolver class and calls placeFrom() at index 0.
     *
     * @param args      not used within this class
     * @see             #placeFrom(int)
     * @since           1.0
     */

    public static void main(String[] args) {
        for( int i = 0; i < week.length; i++ ){
            week[i] = -1;
        }
        SchoolgirlSolver_042018 i = new SchoolgirlSolver_042018();
        i.placeFrom(0);
    }
}
