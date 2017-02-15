package kr.co.juvis.scorecardIncentive.util;

import java.math.BigDecimal;

public class IncentiveUtils
{
    public static boolean isQualifiedOverScore(Object score, Object targetScore) {

        BigDecimal sample = new BigDecimal(String.valueOf(score));
        BigDecimal target = new BigDecimal(String.valueOf(targetScore));

        return sample.compareTo(target) > 0 ? true : false;
    }
}
