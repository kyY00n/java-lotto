package lotto;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LottoPrinterTest {
    @DisplayName("발행된 로또 출력 테스트")
    @Nested
    class PrintGeneratedLottoTest extends NsTest {
        List<Lotto> mockLotto = new ArrayList<>();

        @Test
        public void printLotto() {
            assertRandomUniqueNumbersInRangeTest(
                    () -> {
                        run();
                        assertThat(output()).contains(
                                "8개를 구매했습니다.",
                                "[8, 21, 23, 41, 42, 43]",
                                "[3, 5, 11, 16, 32, 38]",
                                "[7, 11, 16, 35, 36, 44]",
                                "[1, 8, 11, 31, 41, 42]",
                                "[13, 14, 16, 38, 42, 45]",
                                "[7, 11, 30, 40, 42, 43]",
                                "[2, 13, 22, 32, 38, 45]",
                                "[1, 3, 5, 14, 22, 45]"
                        );
                    },
                    List.of(8, 21, 23, 41, 42, 43),
                    List.of(3, 5, 11, 16, 32, 38),
                    List.of(7, 11, 16, 35, 36, 44),
                    List.of(1, 8, 11, 31, 41, 42),
                    List.of(13, 14, 16, 38, 42, 45),
                    List.of(7, 11, 30, 40, 42, 43),
                    List.of(2, 13, 22, 32, 38, 45),
                    List.of(1, 3, 5, 14, 22, 45)
            );
        }

        @Override
        protected void runMain() {
            for (int i = 0; i < 8; i++) {
                mockLotto.add(LottoGenerator.generate());
            }
            LottoPrinter.printLotto(mockLotto);
        }
    }

    @Nested
    @DisplayName("결과 테스트")
    class PrintStatisticsTest extends NsTest {
        @Test
        public void functionalTest() {
            assertSimpleTest(
                    () -> {
                        run();
                        assertThat(output()).contains(
                                "3개 일치 (5,000원) - 1개",
                                "4개 일치 (50,000원) - 0개",
                                "5개 일치 (1,500,000원) - 0개",
                                "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                                "6개 일치 (2,000,000,000원) - 0개"
                        );
                    }
            );
        }

        @Override
        protected void runMain() {
            Map<LottoPrize, Integer> statistics = new HashMap<>();
            statistics.put(LottoPrize.FIFTH, 1);
            statistics.put(LottoPrize.FOURTH, 0);
            statistics.put(LottoPrize.THIRD, 0);
            statistics.put(LottoPrize.SECOND, 0);
            statistics.put(LottoPrize.FIRST, 0);
            LottoPrinter.printStatistics(statistics);
        }
    }
}