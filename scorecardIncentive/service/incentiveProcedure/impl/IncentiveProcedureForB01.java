package kr.co.juvis.scorecardIncentive.service.incentiveProcedure.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.juvis.scorecardIncentive.vo.IncentiveParams;

@Service
public class IncentiveProcedureForB01 extends IncentiveProcedureForStaff
{

    @Override
    protected boolean checkChartRate(IncentiveParams params)
    {
        // 책임급 이상은 제외
        return true;
    }

    @Override
    protected int calcActRate(IncentiveParams params)
    {
        /*
         * 한 지점에 점장 + 책임 등 책임 이상이 있으면 점장 100%, 책임 60%만 지급함
         * 한 지점에 책임이 2명 이상이면 각각 80%씩만
         */
        int actRate = 100;

        String stfBrcId = params.getBrcId();
        String stfStlId = params.getStlId();
        List<Map<String,Object>> stlCountList = params.getStlCountList();

        String brcId  = null;
        int cntB151 = 0;
        int cntB120 = 0;
        int cntB007 = 0;

        boolean isContinue = true;
        for(Map<String,Object> map : stlCountList) {
            brcId = (String)map.get("brcId");
            if ( stfBrcId.equals(brcId) ) {
                cntB151 = (int)((long)map.get("B151"));
                cntB120 = (int)((long)map.get("B120"));
                cntB007 = (int)((long)map.get("B007"));
                if(cntB151+cntB120+cntB007 >= 2) {
                    if("B151".equals(stfStlId) && cntB151 >= 2) {
                        actRate *= 0.8;
                        isContinue = false;
                    }
                    if("B151".equals(stfStlId) && cntB151 == 1 && cntB120+cntB007>=1) {
                        actRate *= 0.6;
                        isContinue = false;
                    }
                    if(!isContinue) break;
                }
            }
        }
        params.setActRate(actRate);

        return actRate;
    }
}
