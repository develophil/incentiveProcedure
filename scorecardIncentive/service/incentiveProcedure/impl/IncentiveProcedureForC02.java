package kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl;

import org.springframework.stereotype.Service;

import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;
import kr.co.juvis.util.MathUtil;

@Service
public class IncentiveProcedureForC02 extends IncentiveProcedureForStaff
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
    protected void calcFinalScore(IncentiveParams params) {
        params.setFinalScore(MathUtil.getRoundDouble(params.getStfScore(),2));
    }
}
