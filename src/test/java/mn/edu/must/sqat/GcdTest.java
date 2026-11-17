package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GcdTest {

    @Test
    @DisplayName("T1 gcd(7,0): no loop. Covers x(13,20), y(13,15) F-branch")
    void t1_gcd7and0_noLoop() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("T2 gcd(12,8): two iterations. Covers x(13,16), x(17,16), x(17,20), "
            + "y(13,15), y(13,16), y(13,17), y(18,15), y(18,16), y(18,17), tmp(16,18)")
    void t2_gcd12and8_twoIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("T3 gcd(0,5): one iteration, extra check. Covers x(13,16), x(17,20), "
            + "y(13,15), y(13,16), y(13,17), y(18,15), tmp(16,18)")
    void t3_gcd0and5_oneIteration() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
