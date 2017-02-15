package kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.juvis.common.util.Utility;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.IncentiveProcedure;
import kr.co.juvis.scorecardIncentive.util.IncentiveUtils;
import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;
import kr.co.juvis.util.MathUtil;

@Service
public class IncentiveProcedureForBranch implements IncentiveProcedure
{
    @Override
    public boolean valid(IncentiveParams params)
    {
        // 지점 손익 2천만원 이상 체크
        checkBranchSalesOver(params);

        // 지점 성과표 점수 80점 이상 체크.
        checkBranchScoreOver(params);

        return true;
    }

    private boolean checkBranchSalesOver(IncentiveParams params) {
        boolean result = true;

        Map<String, Object> costSheet = params.getCostSheet();
        Map<String, Object> bonusRule = params.getBonusRule();

        String brcId = params.getBrcId();
        double brcSales = Utility.getMapDoubleValue(costSheet, brcId+"::S");
        long brcSalesRule = Long.parseLong((String)bonusRule.get("brc_sales"));

        if(!IncentiveUtils.isQualifiedOverScore(brcSales, brcSalesRule)) {
            params.addBrcMemo("지점 손익 2천만원 미만 : "+ MathUtil.getDoubleToString(brcSales));
            result = false; params.setBrcValid(false);
        }
        return result;
    }

    private boolean checkBranchScoreOver(IncentiveParams params) {
        boolean result = true;

        Map<String, Object> bonusRule = params.getBonusRule();

        double scrScore = params.getBrcScore();
        int brcScoreRule = Integer.parseInt((String)bonusRule.get("brc_score"));
        if(!IncentiveUtils.isQualifiedOverScore(scrScore, brcScoreRule)) {
            params.addBrcMemo("지점 성과표 점수 80점 미만 : "+ scrScore);
            result = false;params.setBrcValid(false);
        }

        return result;

    }


    @Override
    public double calculate(IncentiveParams params)
    {
        return 0;
    }

    @Override
    public int confirm(IncentiveParams params)
    {
        return 0;
    }
}
