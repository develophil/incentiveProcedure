package kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.juvis.common.util.UtilDate;
import kr.co.juvis.common.util.UtilString;
import kr.co.juvis.common.util.Utility;
import kr.co.juvis.counsel.service.CounselService;
import kr.co.juvis.scorecardIncentive.mapper.ScorecardIncentiveMapper;
import kr.co.juvis.scorecardIncentive.service.incentiveProcedure.IncentiveProcedure;
import kr.co.juvis.scorecardIncentive.util.IncentiveUtils;
import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;
import kr.co.juvis.scorecardIncentive.vo.ScorecardIncentiveVO;
import kr.co.juvis.scorecardIncentiveInfo.service.ScorecardIncentiveInfoService;
import kr.co.juvis.util.MathUtil;

@Service
public abstract class IncentiveProcedureForStaff implements IncentiveProcedure
{
    @Autowired
    ScorecardIncentiveMapper scorecardIncentiveMapper;

    @Autowired
    ScorecardIncentiveInfoService scorecardIncentiveInfoService;

    @Autowired
    private CounselService counselService;

    // 필수항목 통과 체크
    protected int checkCnt = 0;

    @Override
    public boolean valid(IncentiveParams params)
    {
        // 개인 성과표 점수 80점 이상 체크.
        checkStaffScoreOver(params);

        // 필수항목 통과 체크
        // - 근태
        checkWorktime(params);

        // - 마케팅 실행율
        checkMarketingRate(params);

        // - 용모
        checkAppearanceCnt(params);

        // 과락항목 체크
        // - 차트평가 59%
        checkChartRate(params);


        // 책임자 직급 상당 5건 미만일 경우 성과급 제외
        // checkCounselCount(params);


        // 미시행 항목 체크
        if(params.isTotalValid())
            checkDoNotList(params);

        return true;
    }

    /**
     * 책임자 직급 상당 5건 미만일 경우 성과급 제외
     * @param params
     * @return
     */
    private boolean checkCounselCount(IncentiveParams params) {
    	boolean result = true;

    	String yymm = params.getYymm();
    	String stfId = params.getStfId();
        String sdate = yymm + "-01";
        String edate = UtilDate.getDateTime(UtilDate.getLastDayOfMonth(UtilDate.getStringToDate(sdate)), "yyyy-MM-dd") ;

		String where = "";
		where+= " AND c.stf_id = '"+stfId+"'";
		where+= " AND cus_date between '"+sdate+"' and '"+edate+"'";

		Map<String,Object> data = counselService.getListStaff(where);

		double totalCnt = Utility.getMapDoubleValue(data, "total::cnt");

        if(IncentiveUtils.isQualifiedOverScore(5, totalCnt )) {
            params.addStfMemo("상담 등록율 점수 5건 미만 : " + (int)totalCnt);
            result = false;params.setStfValid(false);
        }

    	return result;

    }

    protected boolean checkJuvisAllSalesOver(IncentiveParams params) {
        boolean result = true;

        Map<String, Object> costSheet = params.getCostSheet();
        Map<String, Object> bonusRule = params.getBonusRule();

        double allSales = Utility.getMapDoubleValue(costSheet, "all::S");
        long juvisSalesRule = Long.parseLong((String)bonusRule.get("juvis_sales"));

        if(!IncentiveUtils.isQualifiedOverScore(allSales, juvisSalesRule)) {
            params.addStfMemo("쥬비스 전체 손익 6억 미만 : "+ MathUtil.getDoubleToString(allSales));
            params.setStfValid(false);
            result = false;
        }
        return result;
    }

    protected boolean checkDoNotList(IncentiveParams params)
    {
        boolean result = true;
        ScorecardIncentiveVO stfParams = params.getIncentiveInsertVO();
        List<Map<String,Object>> doNotList = scorecardIncentiveMapper.getDoNotScorecardItemList(stfParams);
            if(doNotList != null && doNotList.size() > 0) {
                List<String> sciNameList = new ArrayList<>();
                for(Map<String,Object> nullMap : doNotList) {
                    sciNameList.add((String)nullMap.get("sciName"));
                }
                params.addStfMemo("미시행 항목 존재 : "+ UtilString.join(",", sciNameList));
                result = false;params.setStfValid(false);
            }
        return result;
    }

    protected boolean checkChartRate(IncentiveParams params)
    {
        boolean result = true;

        Map<String, Object> bonusRule = params.getBonusRule();

        int chartRateRule = Integer.parseInt((String)bonusRule.get("chart_rate"));
        int chartRate = params.getChartRate();

        if(!IncentiveUtils.isQualifiedOverScore(chartRate, chartRateRule)) {
            params.addStfMemo("차트평가 59% 미만 : "+ chartRate+"%");
            result = false;params.setStfValid(false);
        }
        return result;
    }

    protected boolean checkAppearanceCnt(IncentiveParams params)
    {
        boolean result = true;

        int appearanceCnt = params.getAppearanceCnt();

        if(IncentiveUtils.isQualifiedOverScore(appearanceCnt, checkCnt)) {
            params.addStfMemo("용모 불량 : "+ appearanceCnt+"건");
            result = false;params.setStfValid(false);
        }

        return result;
    }

    protected boolean checkMarketingRate(IncentiveParams params)
    {
        boolean result = true;

        int mktPlanCnt = params.getMktPlanCnt();
        int mktDoCnt = params.getMktDoCnt();

        if(mktPlanCnt!=0 && IncentiveUtils.isQualifiedOverScore(checkCnt, mktDoCnt - mktPlanCnt)) {
            params.addStfMemo("마케팅 실행률 : 100% 미달 ("+ mktDoCnt + "/" + mktPlanCnt + ")");
            result = false;params.setStfValid(false);
        }

        return result;
    }

    protected boolean checkWorktime(IncentiveParams params)
    {
        boolean result = true;

        int worktimeCnt = params.getWorktimeCnt();

        if(IncentiveUtils.isQualifiedOverScore(worktimeCnt, checkCnt)) {
            params.addStfMemo("근태 불량 : "+ worktimeCnt+"건");
            result = false;params.setStfValid(false);
        }

        return result;
    }

    protected boolean checkStaffScoreOver(IncentiveParams params)
    {
        boolean result = true;

        Map<String, Object> bonusRule = params.getBonusRule();

        double stfScore = params.getStfScore();
        int stfScoreRule = Integer.parseInt((String)bonusRule.get("stf_score"));

        if(!IncentiveUtils.isQualifiedOverScore(stfScore, stfScoreRule)) {
            params.addStfMemo("개인 성과표 점수 80점 미만 : "+ stfScore);
            result = false;params.setStfValid(false);
        }
        return result;
    }

    @Override
    public double calculate(IncentiveParams params)
    {
        // 1. 점장, 책임 보유 여부에 따른 성과급 비율 계산
        // 1. 성과급 적용 비율 계산하기
        calcActRate(params);

        // 2. 최종 성과점수 계산하기
        calcFinalScore(params);

        // 3. 요율 계산하기
        calcFactor(params);

        // 4. 성과급 계산 하기
        double incentive = calcIncentive(params);

        return incentive;
    }

    protected int calcActRate(IncentiveParams params)
    {
        int actRate = 100;
        params.setActRate(actRate);
        return actRate;
    }

    protected double calcIncentive(IncentiveParams params) {
        double incentive = 0;
        if(params.isTotalValid() && params.getSccFactor() > 0) {
            incentive = calcIncentiveAndAddMemo(params);
        }

        return incentive;
    }

    protected void calcFinalScore(IncentiveParams params) {
        double brcScore = params.getBrcScore();
        double stfScore = params.getStfScore();
        double stfBrcRatio = 0.7;
        double brcStfRatio = 0.3;
        double finalScore = MathUtil.getRoundDouble(
                   MathUtil.avgRatio(
                       new Double[]{brcScore,stfScore}, new double[]{brcStfRatio,stfBrcRatio}),2);
        params.setFinalScore(finalScore);
    }

    protected void calcFactor(IncentiveParams params) {
        double finalScore = params.getFinalScore();
        int stfScoreRule = Integer.parseInt((String)params.getBonusRule().get("stf_score"));
        double factor = finalScore - stfScoreRule;
        params.setSccFactor(factor);
    }

    protected double calcIncentiveAndAddMemo(IncentiveParams params)
    {
        double factor = params.getSccFactor();
        int stfScoreRule = Integer.parseInt((String)params.getBonusRule().get("stf_score"));
        double incentive = 0;
        if(factor > 0) {
            int actRate = params.getActRate();
            String yymm = params.getYymm();
            String stfId = params.getStfId();
            String stlType = params.getStlType();
            double pricePerPoint = scorecardIncentiveInfoService.getIncentivePerPoint(yymm, stfId, stlType);
            incentive = MathUtil.getRoundDouble(factor * pricePerPoint * actRate / 100 , 0);
            params.addStfMemo("목표 "+stfScoreRule+"점, 개인성과 "+params.getStfScore()+"점, 지점성과 "+params.getBrcScore()+"점");
        }
        params.setSccFactor(MathUtil.getRoundDouble(factor,2));
        params.setSccIncentive((long)incentive);
        return incentive;
    }

    @Override
    public int confirm(IncentiveParams params)
    {
            ScorecardIncentiveVO vo = params.getIncentiveInsertVO();
//            System.out.println(vo.getStfId()+" : "+vo);
          return scorecardIncentiveMapper.insertOrUpdateScorecardIncentive(vo);
    }
}
