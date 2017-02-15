package kr.co.juvis.scorecardIncentive.service.incentiveProcedure;

import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;

public interface IncentiveProcedure
{
    /**
     * 성과급 수령 가능 여부 체크기
     * @param incentiveParams   개인 성과
     * @param brcParams         지점 성과
     * @return
     */
    boolean valid(IncentiveParams params);
    double calculate(IncentiveParams params);
    int confirm(IncentiveParams params);
}
