package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
