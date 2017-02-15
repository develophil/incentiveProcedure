package kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl;

import org.springframework.stereotype.Service;

import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;
import kr.co.juvis.util.MathUtil;

@Service
public class IncentiveProcedureForC01 extends IncentiveProcedureForStaff
{
    @Override
    public boolean valid(IncentiveParams params)
    {

        // 1. 쥬비스 전체 매출 6억 체크
        checkJuvisAllSalesOver(params);
        super.valid(params);

        return true;
    }

    @Override
    protected boolean checkChartRate(IncentiveParams params)
    {
        // 책임급 이상은 제외
        return true;
    }

    @Override
    protected boolean checkMarketingRate(IncentiveParams params)
    {
        // 본사그룹장, 상무, 본부장은 제외함
        return true;
    }

    @Override
    protected void calcFinalScore(IncentiveParams params) {
        params.setFinalScore(MathUtil.getRoundDouble(params.getStfScore(),2));
    }
}
