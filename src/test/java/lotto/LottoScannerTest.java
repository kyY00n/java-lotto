package lotto;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoScannerTest {

    @Nested
    @DisplayName("구입금액 입력 테스트")
    class ReadMoneyTest extends NsTest {
        @DisplayName("1000원 미만의 금액을 입력하면 예외를 던져야한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1000", "0", "200"})
        public void throwExceptionWhenPurchaseAmountIsLessThan1_000(String purchaseAmount) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        run(purchaseAmount)).isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(LottoScanner.LESS_THAN_MIN_PURCHASE_AMOUNT);
            });
        }

        @DisplayName("1000원으로 나누어떨어지지 않으면 예외를 던져야한다.")
        @Test
        public void throwExceptionWhen1000DoesNotDivideMoney() {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        run("25200")).isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(LottoScanner.SHOULD_BE_DIVIDED_INTO_1000);
            });
        }

        @DisplayName("숫자가 아닌 경우 예외를 던져야한다.")
        @Test
        public void throwExceptionWhenInputIsNaN() {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        run("1234j")).isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(LottoScanner.REQUIRE_NUMERIC_VALUE);
            });
        }

        @Override
        protected void runMain() {
            LottoScanner.readPurchaseAmount();
        }
    }

    @Nested
    @DisplayName("당첨번호 입력 테스트")
    class ReadWinningNumberTest extends NsTest {
        @DisplayName("번호가 6개가 아닌 경우 예외가 발생한다.")
        @Test
        public void throwExceptionWhenLackOfNumber() {
            assertThatThrownBy(() -> {
                run("1,2,3,4,5", "7");
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("숫자와 구분자 외의 다른 문자가 들어오면 예외가 발생한다.")
        @Test
        public void throwExceptionWhenOtherCharacterExists() {
            assertThatThrownBy(() -> {
                run("12.23,4,2,5,6");
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(LottoScanner.DO_NOT_INCLUDE_NUMERIC_VALUE);
        }

        @Override
        protected void runMain() {
            LottoScanner.readWinningNumber();
        }
    }
}
