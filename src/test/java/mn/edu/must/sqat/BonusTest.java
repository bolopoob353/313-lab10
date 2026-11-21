package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BonusTest {

    // ===== ALL-DEFS шат =====

    @Test
    @DisplayName("B1 alldefs: present < threshold. score def 49 reaches use 56")
    void b1_belowThreshold_score49() {
        // {1,0}: present=1, threshold=2 -> 1<2, score stays 0; best=1 <5
        assertEquals(0, DataFlowTargets.bonus(new int[] {1, 0}, 2));
    }

    @Test
    @DisplayName("B2 alldefs: present >= threshold and best >= 5. score defs 51 and 54")
    void b2_aboveThreshold_longStreak_score51_54() {
        // {1,0,1,1,1,1,1}: present=6>=3 -> 5; best=5>=5 -> +3 = 8; 0 at index 1 gives def 46 -> use 41
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 0, 1, 1, 1, 1, 1}, 3));
    }
    
    @Test
    @DisplayName("B3 empty array: present(35,50), best(37,53), score(49,56), attendance(31,38) loop exits at once")
    void b3_emptyArray() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {}, 1));
    }

    @Test
    @DisplayName("B4 long streak but below threshold: score(49,54), score(54,56)")
    void b4_longStreak_belowThreshold() {
        // present=5 < 6 -> score stays 0 (def 49), best=5 -> +3 (def 54)
        assertEquals(3, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 6));
    }

    @Test
    @DisplayName("B5 exact threshold, short streak: score(51,56), boundary present==threshold")
    void b5_exactThreshold_shortStreak() {
        // present=2 == threshold=2 -> score=5 (def 51); best=2 < 5 -> straight to return
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 1}, 2));
    }

    @Test
    @DisplayName("B6 null attendance: attendance(31,32) true branch")
    void b6_nullAttendance() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 1));
    }

    @Test
    @DisplayName("B7 negative threshold: threshold(31,32) true branch")
    void b7_negativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] {1}, -1));
    }
    
    @Test
    @DisplayName("B8 kill M2: present != streak when best updates. best must be 3, not present=5")
    void b8_presentDiffersFromStreak() {
        // {1,1,0,1,1,1}, threshold 1: true best=3 (<5), score=5
        // mutant M2: best=present=5 -> score=8
        assertEquals(5, DataFlowTargets.bonus(new int[] {1, 1, 0, 1, 1, 1}, 1));
    }
}
